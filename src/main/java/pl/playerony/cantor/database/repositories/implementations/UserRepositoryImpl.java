package pl.playerony.cantor.database.repositories.implementations;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.playerony.cantor.database.models.User;
import pl.playerony.cantor.database.repositories.UserRepository;
import pl.playerony.cantor.exceptions.CantorRestApiException;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
	@PersistenceContext	
	private EntityManager entityManager;
	
	@Override
	public void insertUser(User newUser) throws CantorRestApiException {
		try {
			entityManager.persist(newUser);
			entityManager.flush();
		} catch(Exception e) {
			throw new CantorRestApiException("There's a problem by insert user", e);
		}
	}

	@Override
	public void updateUser(Long userId, User user) throws CantorRestApiException {
		try {
			User foundUser = fetchUserByUserId(userId);
			
			foundUser.setFirstName(user.getFirstName());
			foundUser.setLastName(user.getLastName());
			foundUser.setUsername(user.getUsername());
			foundUser.setPassword(user.getPassword());
			foundUser.setEmail(user.getEmail());
			foundUser.setBalance(user.getBalance());
			entityManager.flush();
		} catch(Exception e) {
			throw new CantorRestApiException("There's a problem by update user", e);
		}
	}

	@Override
	public User fetchUserByUserId(Long userId) throws CantorRestApiException {
		try {
			User user = entityManager.find(User.class, userId);
			entityManager.flush();
			
			return user;
		} catch(Exception e) {
			throw new CantorRestApiException("There's a problem by fetching user", e);
		}
	}

	@Override
	public User fetchUserByUsername(String username) throws CantorRestApiException {
		try {
			String sql = "FROM User as user WHERE user.username LIKE ?";
			User user = (User) entityManager.createQuery(sql)
									 .setParameter(1, username)
									 .getSingleResult();
			
			entityManager.flush();
			
			return user;
		} catch(Exception e) {
			throw new CantorRestApiException("There's a problem by fetching user by username", e);
		}
	}

	@Override
	public User fetchUserByEmail(String email) throws CantorRestApiException {
		try {
			String sql = "FROM User as user WHERE user.email LIKE ?";
			User user = (User) entityManager.createQuery(sql)
									 .setParameter(1, email)
									 .getSingleResult();
			
			entityManager.flush();
			
			return user;
		} catch(Exception e) {
			throw new CantorRestApiException("There's a problem by fetching user by email", e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> fetchAll() throws CantorRestApiException {
		try {
			String sql = "FROM User as user ORDER BY user.userId";
			List<User> users = (List<User>) entityManager.createQuery(sql)
									 			   		 .getResultList();
			
			entityManager.flush();
			
			return users;
		} catch(Exception e) {
			throw new CantorRestApiException("There's a problem by fetching users", e);
		}
	}

	@Override
	public void removeUser(Long userId) throws CantorRestApiException {
		try {
			User user = fetchUserByUserId(userId);
			entityManager.remove(user);
			entityManager.flush();
		} catch(Exception e) {
			throw new CantorRestApiException("There's a problem by delete user", e);
		}
	}

	@Override
	public Boolean checkUsername(String username) throws CantorRestApiException {
		try {
			String sql = "FROM User as user WHERE user.username LIKE ?";
			int result = entityManager.createQuery(sql)
									  .setParameter(1, username)
									  .getFirstResult();
			
			entityManager.flush();
			
			return result > 0;
		} catch(Exception e) {
			throw new CantorRestApiException("There's a problem by fetching user by username", e);
		}
	}

}
