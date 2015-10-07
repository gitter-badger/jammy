package review.classdesign.jammy.service.factory;

import java.util.Optional;

import org.eclipse.ui.services.AbstractServiceFactory;
import org.eclipse.ui.services.IServiceLocator;

import review.classdesign.jammy.service.ISubmissionService;
import review.classdesign.jammy.service.internal.SubmissionService;

/**
 * Factory {@link ISubmissionService} class.
 * 
 * @author fv
 */
public final class SubmissionServiceFactory extends AbstractServiceFactory {

	/** Optional reference of our singleton service. **/
	private Optional<ISubmissionService> service;

	/**
	 * Default constructor.
	 * Initializes service reference.
	 */
	public SubmissionServiceFactory() {
		this.service = Optional.empty();
	}

	/** {@inheritDoc} **/
	@Override
	public Object create(
			@SuppressWarnings("rawtypes")
			final Class serviceInterface,
			final IServiceLocator parentLocator,
			final IServiceLocator locator) {
		if (ISubmissionService.class.equals(serviceInterface)) {
			if (!service.isPresent()) {
				service = Optional.of(new SubmissionService());
			}
			return service.get();
		}
		return null;
	}

}