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
	public void updateUserCurrency(Long userCurrencyId, Integer currencyAMount) throws CantorRestApiException {
		try {
			UserCurrency foundUserCurrency = fetchUserCurrencyByUserCurrencyId(userCurrencyId);
			
			foundUserCurrency.setCurrencyAmount(currencyAMount);
			entityManager.flush();
		} catch(Exception e) {
			throw new CantorRestApiException("There's a problem by update userCurrency", e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public UserCurrency fetchUserCurrencyByUserCurrencyId(Long userCurrencyId) throws CantorRestApiException {
		try {
			String sql = "FROM UserCurrency as userCurrency WHERE userCurrency.userCurrencyId = ?";
			List<UserCurrency> userCurrencies = (List<UserCurrency>) entityManager.createQuery(sql)
																	 .setParameter(1, userCurrencyId)
																	 .getResultList();
			
			entityManager.flush();
			
			if(userCurrencies != null && userCurrencies.size() > 0)
				return userCurrencies.stream().findFirst().get();
			else
				return null;
		} catch(Exception e) {
			throw new CantorRestApiException("There's a problem by fetching user by username", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserCurrency> fetchUserCurrenciesByUserId(Long userId) throws CantorRestApiException {
		try {
			String sql = "FROM UserCurrency as userCurrency WHERE userCurrency.userId = ?";
			List<UserCurrency> userCurrencies = (List<UserCurrency>) entityManager.createQuery(sql)
																	 .setParameter(1, userId)
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
