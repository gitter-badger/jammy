package review.classdesign.jammy.addons;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import review.classdesign.jammy.core.ProblemSolver;
import review.classdesign.jammy.core.webservice.contest.Problem;

/**
 * Abstract {@link ILanguageManager} implementation tat aims to run solver
 * through {@link Process} instance.
 * 
 * @author fv
 */
public abstract class AbstractProcessManager implements ILanguageManager {

	/** {@inheritDoc} **/
	@Override
	public IProject getProject(final Problem problem, final IProgressMonitor monitor) throws CoreException {
		final String name = problem.getParent().getProjectName();
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProject project = workspace.getRoot().getProject(name + ".python");
		if (!project.exists()) {
			monitor.subTask(String.format(CREATE_PROJECT_TASK, ""));
			project.create(monitor);
			project.open(monitor);
		}
		return project;
	}

	/** {@inheritDoc} **/
	@Override
	public IFile getSolver(final Problem problem, final IProgressMonitor monitor) throws CoreException {
		final IProject project = getProject(problem, monitor);
		final StringBuilder builder = new StringBuilder();
		builder.append(problem.getNormalizedName());
		builder.append(getExtension());
		final IFile file = project.getFile(builder.toString());
		if (!file.exists()) {
			final String template = getTemplate(problem.getNormalizedName());
			final InputStream stream = new ByteArrayInputStream(template.getBytes());
			file.create(stream, true, monitor);
		}
		return file;
	}

	/** {@inheritDoc} **/
	@Override
	public ISolverRunner getRunner(final ProblemSolver solver, final IProgressMonitor monitor) {
		return new ProcessSolverRunner(getCommand(), solver);
	}

	/**
	 * Returns the default template for
	 * the given solver <tt>name</tt>.
	 *
	 * @param name Name of the solver to retrieve template for.
	 * @return Generated template.
	 */
	protected abstract String getTemplate(String name);

	/**
	 * Returns the file extension for file that contains
	 * solver code.
	 * 
	 * @return Solver file extension.
	 */
	protected abstract String getExtension();

	/**
	 * Returns the command binary name that will be ran for
	 * executing the solver code.
	 * 
	 * @return Name of the command line binary.
	 */
	protected abstract String getCommand();

}