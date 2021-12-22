package telran.b7a.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import telran.b7a.accounting.model.User;

@Service
public class SessionServiceImpl implements SessionService {
	Map<String, User> users = new ConcurrentHashMap<>();

	@Override
	public User addUser(String sessionId, User user) {

		return users.put(sessionId, user);
	}

	@Override
	public User getUser(String sessionId) {

		return users.get(sessionId);
	}

	@Override
	public User removeUser(String sessionId) {

		return users.remove(sessionId);
	}

}
