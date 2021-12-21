package telran.b7a.security.filter;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import telran.b7a.accounting.dao.AccountingMongoDBRepository;
import telran.b7a.accounting.model.User;

@Service
@Order(10)
public class AuthenticationFilter implements Filter {

	AccountingMongoDBRepository repository;

	@Autowired
	public AuthenticationFilter(AccountingMongoDBRepository repository) {
		this.repository = repository;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		System.out.println(request.getMethod());
		System.out.println(request.getServletPath());
		
		if (checkEndPoints(request.getServletPath(), request.getMethod())) {
			String token = (request.getHeader("Authorization"));
			if (token == null) {
				response.sendError(401, "Header authorization not found!"); // Unauthorized error
				return;
			}
			String[] credentionals = getCredentionals(token).orElse(null);
			if (credentionals == null || credentionals.length < 2) {
				response.sendError(401, "Token not valid");
				return;
			}
			User userAccount = repository.findById(credentionals[0]).orElse(null);
			if (userAccount == null) {
				response.sendError(401, "User not found");
				return;
			}
			if (!BCrypt.checkpw(credentionals[1], userAccount.getPassword())) {
				response.sendError(401, "User or password not valid");
				return;
			} 
			request = new WrappedRequest(request, credentionals[0]);
		}
		chain.doFilter(request, response);

	}

	private boolean checkEndPoints(String path, String method) {
		return !(("POST".equalsIgnoreCase(method) 
				&& path.matches("[/]account[/]register[/]?"))
				|| (path.matches("[/]forum[/]posts([/]\\w+)+[/]?")));
	}

	private Optional<String[]> getCredentionals(String token) {
		String[] res = null;
		try {
			token = token.split(" ")[1];
			byte[] bytesDecode = Base64.getDecoder().decode(token);
			token = new String(bytesDecode);
			res = token.split(":");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(res);
	}
	private class WrappedRequest extends HttpServletRequestWrapper {
		String login;

		public WrappedRequest(HttpServletRequest request, String login) {
			super(request);
			this.login = login;
		}
		@Override
		public Principal getUserPrincipal() {
			return () -> login;
 		}
		
	}
}
