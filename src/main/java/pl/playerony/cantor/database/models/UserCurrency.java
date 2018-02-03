package pl.playerony.cantor.database.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "user_currency")
public class UserCurrency implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "user_currency_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userCurrencyId;

	@NotNull
	@Column(name = "user_id")
	private Long userId;

	@NotBlank
	@Size(max = 3, min = 3)
	@Column(name = "currency_code")
	private String currencyCode;

	@NotBlank
	@Column(name = "currency_amount")
	private Integer currencyAmount;

	public Long getUserCurrencyId() {
		return userCurrencyId;
	}

	public void setUserCurrencyId(Long userCurrencyId) {
		this.userCurrencyId = userCurrencyId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Integer getCurrencyAmount() {
		return currencyAmount;
	}

	public void setCurrencyAmount(Integer currencyAmount) {
		this.currencyAmount = currencyAmount;
	}

}
