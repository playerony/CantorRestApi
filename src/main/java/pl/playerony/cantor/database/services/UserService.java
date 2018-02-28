package pl.playerony.cantor.database.services;

import pl.playerony.cantor.database.models.User;
import pl.playerony.cantor.exceptions.CantorRestApiException;

public interface UserService {
	void insertUser(User newUser) throws CantorRestApiException;
	
	void updateUser(Long userId, User user) throws CantorRestApiException;
	
	void removeUser(Long userId) throws CantorRestApiException;
	
	User fetchUserByUsername(String username) throws CantorRestApiException;
	
	User fetchUserByUserId(Long userId) throws CantorRestApiException;
}
