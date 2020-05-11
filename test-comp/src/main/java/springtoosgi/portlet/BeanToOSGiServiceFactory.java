package springtoosgi.portlet;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;

public class BeanToOSGiServiceFactory implements AutoCloseable {

	private static final Logger log = LoggerFactory.getLogger(BeanToOSGiServiceFactory.class);

	private final BundleContext bundleContext;
	private final List<ServiceRegistration<?>> registrations = new CopyOnWriteArrayList<>();

	public BeanToOSGiServiceFactory() {
		bundleContext = org.osgi.framework.FrameworkUtil.getBundle(getClass()).getBundleContext();
	}

	
	public ServiceRegistration<?> makeService(Object bean, List<String> serviceTypes) {
		return makeService(bean, serviceTypes, null);
	}

	public ServiceRegistration<?> makeService(Object bean, List<String> serviceTypes, Map<String, Object> properties) {
		requireNonNull(bean, "bean (argument-0) must not be null");
		requireNonNull(serviceTypes);
		if (serviceTypes.isEmpty()) {
			throw new IllegalArgumentException("At least one service type must be specified to publish bean " + bean + " as a service");
		}
		ServiceRegistration<?> reg = null;
		try {
			return reg = bundleContext.registerService(serviceTypes.toArray(new String[0]), bean, getServiceProperties(properties));
		}
		finally {
			log.debug("Registed {} as {}", bean, reg.getReference());
		}
	}

	@EventListener(classes = ContextStoppedEvent.class)
	@Override
	public void close() throws Exception {
		registrations.removeIf(
			reg -> {
				try {
					reg.unregister();
				}
				catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				return true;
			}
		);
	}

	private Dictionary<String, Object> getServiceProperties(Map<String, Object> properties) {
		Hashtable<String, Object> copy = new Hashtable<>();
		if (properties != null) {
			properties.forEach((k,v) -> copy.put(String.valueOf(k), v));
		}
		return copy;
	}

}