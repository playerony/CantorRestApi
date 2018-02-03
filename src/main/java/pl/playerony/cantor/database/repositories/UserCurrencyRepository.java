package pl.playerony.cantor.database.repositories;

import java.util.List;

import pl.playerony.cantor.database.models.UserCurrency;
import pl.playerony.cantor.exceptions.CantorRestApiException;

public interface UserCurrencyRepository {
	void insertUserCurrency(UserCurrency newUserCurrency) throws CantorRestApiException;

    void updateUserCurrency(Long userCurrencyId, UserCurrency userCurrency) throws CantorRestApiException;

    UserCurrency fetchUserCurrencyByUserCurrencyId(Long userCurrencyId) throws CantorRestApiException;

    List<UserCurrency> fetchUserCurrenciesByUserId() throws CantorRestApiException;

    void removeUserCurrency(Long userCurrencyId) throws CantorRestApiException;
}
