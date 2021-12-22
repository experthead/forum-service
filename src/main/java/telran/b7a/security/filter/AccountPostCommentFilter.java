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

import telran.b7a.security.SecurityContext;

@Service
@Order(20)
public class AccountPostCommentFilter implements Filter {
	SecurityContext context;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        
        if (checkEndPoints(request.getServletPath(), request.getMethod())) {
        	Principal principal = request.getUserPrincipal();
			String[] servletString = request.getServletPath().split("/");
			String name = servletString[servletString.length - 1];
			if (!principal.getName().equalsIgnoreCase(name)) {
				response.sendError(403);
				return;
			}
		}
        chain.doFilter(request, response);
	}

	private boolean checkEndPoints(String path, String method) {
		
		return path.matches("/account/user/\\w+") 
                || (path.matches("/forum/post/\\w+") && "Post".equalsIgnoreCase(method))
                ||  path.matches("/forum/post/\\w+/comment/\\w+");
	}

}
