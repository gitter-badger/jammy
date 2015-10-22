package review.classdesign.jammy.addons.java;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import review.classdesign.jammy.ILanguageManager;
import review.classdesign.jammy.ISolverExecution;
import review.classdesign.jammy.addons.java.internal.JavaProjectBuilder;
import review.classdesign.jammy.addons.java.internal.JavaSolverBuilder;
import review.classdesign.jammy.addons.java.internal.JavaSolverRunner;
import review.classdesign.jammy.model.ProblemSolver;
import review.classdesign.jammy.model.webservice.ContestInfo;
import review.classdesign.jammy.model.webservice.Problem;

/**
 * 
 * @author fv
 */
public final class JavaManager implements ILanguageManager {

	/** Suffix used for solver class file. **/
	private static final String SOLVER_SUFFIX = "Solver";

	/** {@inheritDoc} **/
	@Override
	public IProject getProject(final Problem problem, final IProgressMonitor monitor) throws CoreException {
		final ContestInfo info = problem.getParent();
		final String name = info.getProjectName();
		return JavaProjectBuilder.build(name, monitor);
	}

	/** {@inheritDoc} **/
	@Override
	public IFile getSolver(final Problem problem, final IProgressMonitor monitor) throws CoreException {
		final StringBuilder builder = new StringBuilder();
		builder.append(problem.getNormalizedName());
		builder.append(SOLVER_SUFFIX);
		final String name = builder.toString();
		final IProject project = getProject(problem, monitor);
		return new JavaSolverBuilder(project, monitor).build(name);
	}

	/** {@inheritDoc} **/
	@Override
	public ISolverExecution getExecution(final ProblemSolver solver, final IProgressMonitor monitor) throws CoreException {
		return new JavaSolverRunner(solver, monitor);
	}

}
