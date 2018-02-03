package pl.playerony.cantor.database.repositories.implementations;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.playerony.cantor.database.models.UserCurrency;
import pl.playerony.cantor.database.repositories.UserCurrencyRepository;
import pl.playerony.cantor.exceptions.CantorRestApiException;

@Repository
@Transactional
public class UserCurrencyRepositoryImpl implements UserCurrencyRepository {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void insertUserCurrency(UserCurrency newUserCurrency) throws CantorRestApiException {
		try {
			entityManager.persist(newUserCurrency);
			entityManager.flush();
		} catch(Exception e) {
			throw new CantorRestApiException("There's a problem by insert userCurrency", e);
		}
	}

	@Override
	public void updateUserCurrency(Long userCurrencyId, UserCurrency userCurrency) throws CantorRestApiException {
		try {
			UserCurrency foundUserCurrency = fetchUserCurrencyByUserCurrencyId(userCurrencyId);
			
			foundUserCurrency.setCurrencyAmount(userCurrency.getCurrencyAmount());
			entityManager.flush();
		} catch(Exception e) {
			throw new CantorRestApiException("There's a problem by update userCurrency", e);
		}
	}

	@Override
	public UserCurrency fetchUserCurrencyByUserCurrencyId(Long userCurrencyId) throws CantorRestApiException {
		try {
			String sql = "FROM UserCurrency as userCurrency WHERE user.userCurrencyId = ?";
			UserCurrency userCurrency = (UserCurrency) entityManager.createQuery(sql)
																	 .setParameter(1, userCurrencyId)
																	 .getSingleResult();
			
			entityManager.flush();
			
			return userCurrency;
		} catch(Exception e) {
			throw new CantorRestApiException("There's a problem by fetching user by username", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserCurrency> fetchUserCurrenciesByUserId() throws CantorRestApiException {
		try {
			String sql = "FROM UserCurrency";
			List<UserCurrency> userCurrencies = (List<UserCurrency>) entityManager.createQuery(sql)
																	 .getResultList();
			
			entityManager.flush();
			
			return userCurrencies;
		} catch(Exception e) {
			throw new CantorRestApiException("There's a problem by fetching userCurrencies", e);
		}
	}

	@Override
	public void removeUserCurrency(Long userCurrencyId) throws CantorRestApiException {
		try {
			UserCurrency userCurrency = fetchUserCurrencyByUserCurrencyId(userCurrencyId);
			entityManager.remove(userCurrency);
			entityManager.flush();
		} catch(Exception e) {
			throw new CantorRestApiException("There's a problem by insert userCurrency", e);
		}
	}

}