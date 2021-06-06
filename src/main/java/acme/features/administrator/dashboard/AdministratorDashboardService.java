
package acme.features.administrator.dashboard;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.task.Task;
import acme.forms.Dashboard;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorDashboardService implements AbstractShowService<Administrator, Dashboard> {

	@Autowired
	protected AdministratorDashboardRepository repository;


	@Override
	public boolean authorise(final Request<Dashboard> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<Dashboard> request, final Dashboard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, //
			"publicTasks", "privateTasks", "ongoingTasks", "finishedTasks", "averageExecTime", //
			"deviationExecTime", "maxExecTime", "minExecTime", "averageWorkload", "deviationWorkload",//
			"maxWorkload", "minWorkload","ratioFinalisao", "mediaEUR", "mediaUSD", "deviationEUR", "deviationUSD");
	}

	@Override
	public Dashboard findOne(final Request<Dashboard> request) {
		assert request != null;
		Dashboard result;
		Integer publicTasks;
		Integer privateTasks;
		Integer ongoingTasks;
		Integer finishedTasks;
		final Double averageExecTime;
		final Double deviationExecTime;
		final Double maxExecTime;
		final Double minExecTime;
		Double averageWorkload;
		Double deviationWorkload;
		Double maxWorkload;
		Double minWorkload;
		Double ratioFinalisao;
		Double mediaEUR;
		Double mediaUSD;		
		Double deviationEUR;		
		Double deviationUSD; 
		List<Task> tasks;
		Integer totalShouts;

		publicTasks = this.repository.publicTasks();
		privateTasks = this.repository.privateTasks();
		ongoingTasks = this.repository.ongoingTasks();
		finishedTasks = this.repository.finishedTasks();
		averageWorkload = this.repository.averageWorkload();
		deviationWorkload = this.repository.deviationWorkload();
		maxWorkload = this.repository.maxWorkload();
		minWorkload = this.repository.minWorkload();
		tasks = this.repository.findAllTasks();
		final Comparator<Task> cmp = Comparator.comparing(Task::getExecutionTime);
		maxExecTime = tasks.stream().max(cmp).get().getExecutionTime();
		minExecTime = tasks.stream().min(cmp).get().getExecutionTime();
		averageExecTime = tasks.stream().mapToDouble(Task::getExecutionTime).average().getAsDouble();
		deviationExecTime = Math.sqrt(tasks.stream().mapToDouble(Task::getExecutionTime).map(i -> (i - averageExecTime)).map(i -> i * i).average().getAsDouble());
		totalShouts = this.repository.findAllShouts().size();
		ratioFinalisao = (double) (this.repository.getFinalisao().size() / totalShouts);
		mediaEUR = this.repository.mediaEUR();
		mediaUSD = this.repository.mediaUSD();
		deviationEUR = this.repository.deviationEUR();
		deviationUSD = this.repository.deviationUSD();

		result = new Dashboard();
		result.setPublicTasks(publicTasks);
		result.setPrivateTasks(privateTasks);
		result.setOngoingTasks(ongoingTasks);
		result.setFinishedTasks(finishedTasks);
		result.setAverageExecTime(averageExecTime);
		result.setDeviationExecTime(deviationExecTime);
		result.setMaxExecTime(maxExecTime);
		result.setMinExecTime(minExecTime);
		result.setAverageWorkload(averageWorkload);
		result.setDeviationWorkload(deviationWorkload);
		result.setMaxWorkload(maxWorkload);
		result.setMinWorkload(minWorkload);
		result.setRatioFinalisao(ratioFinalisao);
		result.setMediaEUR(mediaEUR);
		result.setMediaUSD(mediaUSD);
		result.setDeviationEUR(deviationEUR);
		result.setDeviationUSD(deviationUSD);
		return result;
	}

}
