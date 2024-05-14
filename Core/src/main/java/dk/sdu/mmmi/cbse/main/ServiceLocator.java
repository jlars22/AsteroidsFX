package dk.sdu.mmmi.cbse.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public class ServiceLocator<T> {
	private static final Map<Class<?>, ServiceLocator<?>> serviceLocatorMap = new HashMap<>();
	private final List<T> services;

	private ServiceLocator(Class<T> type) {
		services = ServiceLoader.load(type).stream().map(ServiceLoader.Provider::get).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	public static <S> ServiceLocator<S> getInstance(Class<S> serviceType) {
		return (ServiceLocator<S>) serviceLocatorMap.computeIfAbsent(serviceType, ServiceLocator::new);
	}

	public List<T> getServices() {
		return services;
	}
}
