package pl.playerony.cantor.database.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.playerony.cantor.database.models.UserCurrency;
import pl.playerony.cantor.database.repositories.UserCurrencyRepository;
import pl.playerony.cantor.database.services.UserCurrencyService;
import pl.playerony.cantor.exceptions.CantorRestApiException;

@Service
public class UserCurrencyServiceImpl implements UserCurrencyService {
	@Autowired
	private UserCurrencyRepository userCurrencyRepository;
	
	@Override
	public void insertUserCurrency(UserCurrency newUserCurrency) throws CantorRestApiException {
		List<UserCurrency> userCurrencies = fetchUserCurrenciesByUserId(newUserCurrency.getUserId());
		boolean isElementExist = false;
		
		if(userCurrencies != null) {
			for(UserCurrency u : userCurrencies) {
				if(u.getCurrencyCode().equals(newUserCurrency.getCurrencyCode())) {
					isElementExist = true;
					break;
				}
			}
		}
		
		if(userCurrencies != null && !isElementExist)
			userCurrencyRepository.insertUserCurrency(newUserCurrency);
		else
			throw new CantorRestApiException("This userCurrencyId[" + newUserCurrency.getUserCurrencyId() + "] already exist for this user.");
	}

	@Override
	public void buyCurrency(Long userCurrencyId, Integer amount) throws CantorRestApiException {
		UserCurrency userCurrency = userCurrencyRepository.fetchUserCurrencyByUserCurrencyId(userCurrencyId);
		
		if(userCurrency == null)
			throw new CantorRestApiException("This userCurrencyId[" + userCurrencyId + "] doesnt exist for this user.");
		
		int newAmountOfCurrency = userCurrency.getCurrencyAmount() + amount;
		
		userCurrencyRepository.updateUserCurrency(userCurrencyId, newAmountOfCurrency);
	}

	@Override
	public void sellCurrency(Long userCurrencyId, Integer amount) throws CantorRestApiException {
		UserCurrency userCurrency = userCurrencyRepository.fetchUserCurrencyByUserCurrencyId(userCurrencyId);
		if(userCurrency == null)
			throw new CantorRestApiException("This userCurrencyId[" + userCurrencyId + "] doesnt exist for this user.");
		
		int newAmountOfCurrency = userCurrency.getCurrencyAmount() - amount;
		if(newAmountOfCurrency < 0)
			throw new CantorRestApiException("You wanna sell too much amount of currency");
		
		userCurrencyRepository.updateUserCurrency(userCurrencyId, newAmountOfCurrency);
	}

	@Override
	public List<UserCurrency> fetchUserCurrenciesByUserId(Long userId) throws CantorRestApiException {
		return userCurrencyRepository.fetchUserCurrenciesByUserId(userId);
	}

	@Override
	public UserCurrency fetchUserCurrencyByUserCurrencyId(Long userCurrencyId) throws CantorRestApiException {
		UserCurrency userCurrency = userCurrencyRepository.fetchUserCurrencyByUserCurrencyId(userCurrencyId);
		
		if(userCurrency != null)
			return userCurrency;
		else
			throw new CantorRestApiException("This userCurrencyId[" + userCurrencyId + "] doesnt exist.");
	}

}
