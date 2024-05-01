package dk.sdu.mmmi.cbse.main;

import static java.util.stream.Collectors.toList;

import dk.sdu.mmmi.cbse.common.data.EventBroker;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IEntityStylingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IObserver;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import java.util.List;
import java.util.ServiceLoader;
import javax.annotation.PostConstruct;

import dk.sdu.mmmi.cbse.common.services.IUIRenderingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"dk.sdu.mmmi.cbse.main"})
public class ServiceConfig {
	private final EventBroker eventBroker = EventBroker.getInstance();

	@Bean
	public List<IGamePluginService> gamePluginServices() {
		return ServiceLoader.load(IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
	}

	@Bean
	public List<IEntityProcessingService> entityProcessingServiceList() {
		return ServiceLoader.load(IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get)
				.collect(toList());
	}

	@Bean
	public List<IPostEntityProcessingService> postEntityProcessingServices() {
		return ServiceLoader.load(IPostEntityProcessingService.class).stream().map(ServiceLoader.Provider::get)
				.collect(toList());
	}

	@Bean
	public List<IUIRenderingService> uiRenderingServices() {
		return ServiceLoader.load(IUIRenderingService.class).stream().map(ServiceLoader.Provider::get)
				.collect(toList());
	}

	@Bean
	public List<IEntityStylingService> entityStylingServices() {
		return ServiceLoader.load(IEntityStylingService.class).stream().map(ServiceLoader.Provider::get)
				.collect(toList());
	}

	@PostConstruct
	public void loadObservers() {
		ServiceLoader.load(IObserver.class).stream().map(ServiceLoader.Provider::get)
				.forEach(observer -> eventBroker.addObserver(observer.getTopics(), observer));
	}
}
