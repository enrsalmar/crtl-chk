
package acme.entities.shouts;

import java.util.Date;

import javax.money.MonetaryAmount;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Currency;
import org.springframework.format.annotation.DateTimeFormat;

import acme.framework.entities.DomainEntity;

public class XXX extends DomainEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@OneToOne
	protected Shout				shout;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat
	protected Date				fecha;

	@NotNull
	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				moment;

	@Currency(value = {"EUR", "USD"})
	@Min(0)
	protected MonetaryAmount				dinero;
	
	
	protected Boolean						finalisao;

}
