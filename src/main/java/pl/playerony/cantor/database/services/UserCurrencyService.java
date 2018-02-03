package pl.playerony.cantor.database.services;

import java.util.List;

import pl.playerony.cantor.database.models.UserCurrency;
import pl.playerony.cantor.exceptions.CantorRestApiException;

public interface UserCurrencyService {
	void insertUserCurrency(UserCurrency newUserCurrency) throws CantorRestApiException;
	
	void buyCurrency(Long userCurrencyId, Integer amount) throws CantorRestApiException;
	
	void sellCurrency(Long userCurrencyId, Integer amount) throws CantorRestApiException;
	
	List<UserCurrency> fetchUserCurrenciesByUserId(Long userId) throws CantorRestApiException;
	
	UserCurrency fetchUserCurrencyByUserCurrencyId(Long userCurrencyId) throws CantorRestApiException;
}
