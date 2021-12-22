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
import telran.b7a.security.SecurityContext;
import telran.b7a.security.UserProfile;

@Service
@Order(30)
public class DeletePostFilter implements Filter {
	PostRepository repository;
	SecurityContext securityContext;

	@Autowired
	public DeletePostFilter(PostRepository repository, SecurityContext securityContext) {
		this.repository = repository;
		this.securityContext = securityContext;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		Principal principal = request.getUserPrincipal();
		UserProfile user = securityContext.getUser(principal.getName());

		if (checkEndPoints(request.getServletPath(), request.getMethod())) {
			String[] servletString = request.getServletPath().split("/");
			String postId = servletString[servletString.length - 1];
			System.err.println(postId);
			Post post = repository.findById(postId).orElse(null);
			if (post == null) {
				response.sendError(404, "Post not found");
				return;
			}
			if (!((post.getAuthor().equals(user.getLogin())) || user.getRoles().contains("MODERATOR"))) {
				response.sendError(403);
				return;
			} //вопрос удаления принципала из контекста безопасноти после прохождения всех фильтров
		}
		chain.doFilter(request, response);
	}

	private boolean checkEndPoints(String path, String method) {
		return method.equalsIgnoreCase("Delete") && path.matches("/forum/post/\\w+");
	}

}
