package pl.playerony.cantor.database.repositories;

import java.util.List;

import pl.playerony.cantor.database.models.User;
import pl.playerony.cantor.exceptions.CantorRestApiException;

public interface UserRepository {
	void insertUser(User newUser) throws CantorRestApiException;

    void updateUser(Long userId, User user) throws CantorRestApiException;

    User fetchUserByUserId(Long userId) throws CantorRestApiException;

    User fetchUserByUsername(String username) throws CantorRestApiException;
    
    User fetchUserByEmail(String email) throws CantorRestApiException;

    List<User> fetchAll() throws CantorRestApiException;

    void removeUser(Long userId) throws CantorRestApiException;
    
    Boolean checkUsername(String username) throws CantorRestApiException;
}
