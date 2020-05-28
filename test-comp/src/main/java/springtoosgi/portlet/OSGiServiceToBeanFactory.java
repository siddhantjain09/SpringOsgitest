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

	private static final Logger log = LoggerFactory.getLogger(OSGiServiceToBeanFactory.class);

	private final BundleContext bundleContext;
	private final Map<ServiceKey, ServiceTrackerList<?, ?>> trackerLists = new ConcurrentHashMap<>();

	public OSGiServiceToBeanFactory() {
		bundleContext = org.osgi.framework.FrameworkUtil.getBundle(getClass()).getBundleContext();
	}

	public <T> T getService(Class<T> clazz) {
		return getService(clazz, "");
	}

	@SuppressWarnings("unchecked")
	public <T> T getService(Class<T> clazz, String filterString) {
		ServiceTrackerList<?, ?> serviceTrackerList = trackerLists.computeIfAbsent(
			new ServiceKey(clazz, filterString), k -> openList(clazz, filterString));
System.out.println(serviceTrackerList.iterator().hasNext());
		return (T)serviceTrackerList.iterator().next();
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

	private static class ServiceKey<X> {
		private final Class<X> clazz;
		private final String filterString;
		public ServiceKey(Class<X> clazz, String filterString) {
			super();
			this.clazz = clazz;
			this.filterString = filterString;
		}
		@Override
		public int hashCode() {
			return Objects.hash(clazz.getName(), filterString);
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
			return Objects.equals(clazz.getName(), other.clazz.getName()) && Objects.equals(filterString, other.filterString);
		}
	}

}
