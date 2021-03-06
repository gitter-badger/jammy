package fr.faylixe.jammy.core.command;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import fr.faylixe.jammy.core.ProblemSolver;
import fr.faylixe.jammy.core.submission.ISubmission;
import fr.faylixe.jammy.core.submission.internal.LocalSubmission;

/**
 * Command that run the target solver using the sample dataset.
 * A {@link LocalSubmission} instance is created and scheduled
 * through a {@link Job}
 * 
 * @author fv
 */
public final class RunSampleDatasetCommand extends AbstractProgressiveSolverCommand {

	/** Task name for the file opening. **/
	private static final String RUN_SAMPLE_TASK = "Running sample dataset";

	/** {@inheritDoc} **/
	@Override
	protected void processSolver(final ProblemSolver solver, final IProgressMonitor monitor) throws CoreException {
		final ISubmission submission = new LocalSubmission(solver);
		// TODO : 	Implements ISchedulingRule in order to avoid submission conflict.
		// 			Consider down the job layer with rule to the submit() method (even with submission service).
		final Job job = Job.create("", submissionMonitor -> {
			try {
				submission.start(submissionMonitor);
			}
			catch (final CoreException e) {
				return e.getStatus();
			}
			return Status.OK_STATUS;
		});
		//job.setRule(this);
		job.schedule();
	}

	/** {@inheritDoc} **/
	@Override
	protected String getTaskName() {
		return RUN_SAMPLE_TASK;
	}

}
