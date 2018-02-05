package pl.playerony.cantor;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.playerony.cantor.database.models.UserCurrency;
import pl.playerony.cantor.database.services.UserCurrencyService;
import pl.playerony.cantor.exceptions.CantorRestApiException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserCurrencyService {
	@Autowired
	private UserCurrencyService userCurrencyService;
	
	private void insertUserCurrency(UserCurrency userCurrency) throws CantorRestApiException {
		assertNotNull(userCurrency != null);
		
		userCurrencyService.insertUserCurrency(userCurrency);
	}
	
	private UserCurrency fetchUserCurrency(Long userCurrencyId) throws CantorRestApiException {
		UserCurrency userCurrency = userCurrencyService.fetchUserCurrencyByUserCurrencyId(userCurrencyId);
		
		assertNotNull(userCurrency);
		assertTrue(userCurrency.getCurrencyCode().length() == 3);
		assertTrue(userCurrency.getCurrencyAmount() != null);
		
		return userCurrency;
	}
	
	private void buyCurrency(Long userCurrencyId, Integer amount) throws CantorRestApiException {
		userCurrencyService.buyCurrency(userCurrencyId, amount);
		
		UserCurrency userCurrency = userCurrencyService.fetchUserCurrencyByUserCurrencyId(userCurrencyId);
		assertNotNull(userCurrency);
		assertTrue(userCurrency.getCurrencyCode().length() == 3);
		assertTrue(userCurrency.getCurrencyAmount() != null && userCurrency.getCurrencyAmount() > 0);
	}
	
	private void sellCurrency(Long userCurrencyId, Integer amount) throws CantorRestApiException {
		userCurrencyService.sellCurrency(userCurrencyId, amount);
		
		UserCurrency userCurrency = userCurrencyService.fetchUserCurrencyByUserCurrencyId(userCurrencyId);
		assertNotNull(userCurrency);
		assertTrue(userCurrency.getCurrencyCode().length() == 3);
		assertTrue(userCurrency.getCurrencyAmount() != null && userCurrency.getCurrencyAmount() == 0);
	}
	
	private void fetchUserCurrencies(Long userId) throws CantorRestApiException {
		List<UserCurrency> userCurrencies = userCurrencyService.fetchUserCurrenciesByUserId(userId);
		
		assertNotNull(userCurrencies);
		assertTrue(userCurrencies.size() > 0);
	}
	
	@Test
	public void mainTestMethod() {
		try {
			Long userCurrencyId = 1L;
			UserCurrency userCurrency = fetchUserCurrency(userCurrencyId);
			
			buyCurrency(userCurrencyId, 5);
			//sellCurrency(userCurrencyId, 5);
			
			fetchUserCurrencies(userCurrency.getUserId());
		} catch (CantorRestApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
