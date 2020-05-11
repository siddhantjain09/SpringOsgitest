package springtoosgi.portlet;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;

public class OSGiServiceToBeanFactory implements AutoCloseable {

	private static final Logger log = LoggerFactory.getLogger(BeanToOSGiServiceFactory.class);

	private final BundleContext bundleContext;
	private final Map<ServiceKey, ServiceTrackerList<?, ?>> trackerLists = new ConcurrentHashMap<>();

	public OSGiServiceToBeanFactory() {
		bundleContext = org.osgi.framework.FrameworkUtil.getBundle(getClass()).getBundleContext();
	}

	public <T> T getService(String clazz) {
		return getService(clazz, "");
	}

	@SuppressWarnings("unchecked")
	public <T> T getService(String className, String filterString) {
		
		Class clazz;
		try {
			clazz = Class.forName(className);
		
		ServiceTrackerList<?, ?> serviceTrackerList = trackerLists.computeIfAbsent(
			new ServiceKey(className, filterString), k -> openList(clazz, filterString));

		return (T)serviceTrackerList.iterator().next();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return null;
	}

	@EventListener(classes = ContextStoppedEvent.class)
	@Override
	public void close() throws Exception {
		trackerLists.forEach((k, v) -> v.close());
		trackerLists.clear();
	}

	private <X> ServiceTrackerList<X, X> openList(Class<X> clazz, String filterString) {
		try {
			return ServiceTrackerListFactory.open(bundleContext, clazz, filterString);
		}
		finally {
			log.debug("Tracking {} with {}", clazz.getName(), filterString);
		}
	}

	private static class ServiceKey {
		private final String className;
		private final String filterString;
		public ServiceKey(String className, String filterString) {
			super();
			this.className = className;
			this.filterString = filterString;
		}
		@Override
		public int hashCode() {
			return Objects.hash(className, filterString);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof ServiceKey)) {
				return false;
			}
			ServiceKey other = (ServiceKey) obj;
			return Objects.equals(className, other.className) && Objects.equals(filterString, other.filterString);
		}
	}

}
