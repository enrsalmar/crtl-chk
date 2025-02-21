package acme.features.authenticated.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.spam.Spam;
import acme.entities.spam.Threshold;
import acme.features.administrator.spam.AdminSpamCreateService;
import acme.features.administrator.spam.AdminSpamRepository;
import acme.features.administrator.threshold.ThresholdRepository;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractUpdateService;

@Service
public class AuthenticatedManagerUpdateService implements AbstractUpdateService<Authenticated, Manager> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedManagerRepository repository;
	
	@Autowired
	private AdminSpamRepository spamRepository;
	
	@Autowired
	protected AdminSpamCreateService spamService;
	
	@Autowired
	protected ThresholdRepository	thresholdRepository;

	// AbstractUpdateService<Authenticated, Manager> interface -----------------


	@Override
	public boolean authorise(final Request<Manager> request) {
		assert request != null;

		return true;
	}

	@Override
	public void validate(final Request<Manager> request, final Manager entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		final List<Spam> spam= (List<Spam>) this.spamRepository.findSpam();
		final Threshold threshold=this.thresholdRepository.findSpamEntity();
		final boolean censuraCompany = Threshold.censura(entity.getCompany(), spam, threshold.getThreshold());
		final boolean censuraSector = Threshold.censura(entity.getSector(), spam, threshold.getThreshold());
		
		if(censuraCompany) {
			errors.add("company", "this company is spam ");
		}

		if(censuraSector) {
			errors.add("sector", "this sector is spam ");
		}
	
	}

	@Override
	public void bind(final Request<Manager> request, final Manager entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Manager> request, final Manager entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "company", "sector");
	}

	@Override
	public Manager findOne(final Request<Manager> request) {
		assert request != null;

		Manager result;
		Principal principal;
		int userAccountId;

		principal = request.getPrincipal();
		userAccountId = principal.getAccountId();

		result = this.repository.findOneManagerByUserAccountId(userAccountId);

		return result;
	}

	@Override
	public void update(final Request<Manager> request, final Manager entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

	@Override
	public void onSuccess(final Request<Manager> request, final Response<Manager> response) {
		assert request != null;
		assert response != null;

		if (request.isMethod(HttpMethod.POST)) {
			PrincipalHelper.handleUpdate();
		}
	}

}
