package telran.b7a.security.filter;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import telran.b7a.forum.dao.PostRepository;
import telran.b7a.forum.model.Post;

@Service
@Order(40)
public class UpdatePostFilter implements Filter {
	PostRepository repository;
	
	@Autowired
	public UpdatePostFilter(PostRepository repository) {
		this.repository = repository;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		Principal principal = request.getUserPrincipal();
		
		if (checkEndPoints(request.getServletPath(), request.getMethod())) {
			String[] servletString = request.getServletPath().split("/");
			String postId = servletString[servletString.length - 1];
			Post post = repository.findById(postId).orElse(null);
			if (post == null) {
				response.sendError(404);
				return;
			}
			if (!post.getAuthor().equals(principal.getName())) {
				response.sendError(403);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private boolean checkEndPoints(String path, String method) {
		return method.equalsIgnoreCase("Put") && path.matches("/forum/post/\\w+/?");
	}
	
	
}
