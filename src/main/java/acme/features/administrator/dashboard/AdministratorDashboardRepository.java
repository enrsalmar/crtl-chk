
package acme.features.administrator.dashboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.shouts.XXX;
import acme.entities.task.Task;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {
	
	@Query("SELECT t FROM Task t")
	List<Task> findAllTasks();
	
	@Query("select count(t.publico) from Task t where t.publico = TRUE")
	Integer publicTasks();

	@Query("select count(t.publico) from Task t where t.publico = FALSE")
	Integer privateTasks();
	
	@Query("select count(t.finished) from Task t where t.finished = FALSE")
	Integer ongoingTasks();
	
	@Query("select count(t.finished) from Task t where t.finished = TRUE")
	Integer finishedTasks();

	@Query("select avg(t.workload) from Task t")
	Double averageWorkload();

	@Query("select stddev(t.workload) from Task t")
	Double deviationWorkload();

	@Query("select max(t.workload) from Task t")
	Double maxWorkload();

	@Query("select min(t.workload) from Task t")
	Double minWorkload();
	
	@Query("select count(x.finalisao) from XXX x where x.finalisao = TRUE")
	List<XXX> getFinalisao();
	
	@Query("select avg(x.dinero) from XXX x where x.dinero.CURRENCY = 'EUR'")
	Double mediaEUR();
	
	@Query("select avg(x.dinero) from XXX x where x.dinero.CURRENCY = 'USD'")
	Double mediaUSD();
	
	@Query("select stddev(x.dinero) from XXX x where x.dinero.CURRENCY = 'EUR'")
	Double deviationEUR();
	
	@Query("select stddev(x.dinero) from XXX x where x.dinero.CURRENCY = 'USD'")
	Double deviationUSD();
	
	@Query("SELECT s FROM Shout s")
	List<Task> findAllShouts();

}