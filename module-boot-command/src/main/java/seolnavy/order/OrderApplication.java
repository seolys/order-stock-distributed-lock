package seolnavy.order;

import seolnavy.order.view.main.MainView;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class OrderApplication {

	public static void main(final String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

	@Profile("!test")
	@Bean
	public ApplicationRunner applicationRunner(final MainView mainView) {
		return args -> mainView.show();
	}

}
