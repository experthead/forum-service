package telran.b7a.security;

import telran.b7a.accounting.model.User;

public interface SessionService {
	User addUser(String sessionId, User user);

	User getUser(String sessionId);

	User removeUser(String sessionId);
}
