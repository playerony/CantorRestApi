package pl.playerony.cantor;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import pl.playerony.cantor.database.models.User;
import pl.playerony.cantor.database.services.UserService;
import pl.playerony.cantor.exceptions.CantorRestApiException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserService {
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	private void saveUser(User user) throws CantorRestApiException {
		assertNotNull(user);
		
		user.setFirstName("saveTest");
		user.setUsername("saveTest");
		user.setPassword(encoder.encode("saveTest"));
		user.setUserId(null);
		userService.insertUser(user);
		
		
		User foundUser = userService.fetchUserByUsername(user.getUsername());
		assertNotNull(foundUser);
		assertTrue(foundUser.getUserId() == 2);
		assertTrue(foundUser.getFirstName().equals("saveTest"));
		assertTrue(foundUser.getUsername().equals("saveTest"));
	}
	
	private User getUserByUsername(String username) throws CantorRestApiException {
		User user = userService.fetchUserByUsername(username);
		
		assertNotNull(user);
		assertTrue(encoder.matches(username, user.getPassword()));
		assertTrue(user.getUserId() == 1);
		assertTrue(user.getRoleId() == 1);
		
		return user;
	}
	
	private void removeUser(Long userId) throws CantorRestApiException {
		userService.removeUser(userId);
	}	
	
	@Test
	public void mainTestMethod() {
		try {
			User user = getUserByUsername("test");
			saveUser(user);
			removeUser(2L);
		} catch (CantorRestApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
