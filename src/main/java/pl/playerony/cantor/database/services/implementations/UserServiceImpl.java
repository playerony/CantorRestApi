package pl.playerony.cantor.database.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.playerony.cantor.database.models.User;
import pl.playerony.cantor.database.repositories.UserRepository;
import pl.playerony.cantor.database.services.UserService;
import pl.playerony.cantor.exceptions.CantorRestApiException;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void insertUser(User newUser) throws CantorRestApiException {
		if(!userRepository.checkUsername(newUser.getUsername()))
			userRepository.insertUser(newUser);
		else
			throw new CantorRestApiException("This username[" + newUser.getUsername() + "] is busy.");
	}

	@Override
	public void updateUser(Long userId, User user) throws CantorRestApiException {
		if(userRepository.fetchUserByUserId(userId) != null)
			userRepository.updateUser(userId, user);
		else
			throw new CantorRestApiException("This user[" + userId + "] doesnt exist in database.");
	}

	@Override
	public void removeUser(Long userId) throws CantorRestApiException {
		if(userRepository.fetchUserByUserId(userId) != null)
			userRepository.removeUser(userId);
		else
			throw new CantorRestApiException("This user[" + userId + "] doesnt exist in database.");
	}

	@Override
	public User fetchUserByUsername(String username) throws CantorRestApiException {
		User user = userRepository.fetchUserByUsername(username);
		
		if(user != null)
			return user;
		else
			throw new CantorRestApiException("This username[" + username + "] doesnt exist in database.");
	}

}
