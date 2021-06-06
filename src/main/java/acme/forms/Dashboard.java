
package acme.forms;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Dashboard implements Serializable {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Integer						publicTasks;

	Integer						privateTasks;

	Integer						ongoingTasks;

	Integer						finishedTasks;

	Double						averageExecTime;

	Double						deviationExecTime;

	Double						maxExecTime;

	Double						minExecTime;

	Double						averageWorkload;

	Double						deviationWorkload;

	Double						maxWorkload;

	Double						minWorkload;
	
	Double						ratioFinalisao;
	
//	Double						ratioXXX;
	
	Double						mediaEUR;
	
	Double						mediaUSD;
	
	Double						deviationEUR;
	
	Double						deviationUSD; 

}
