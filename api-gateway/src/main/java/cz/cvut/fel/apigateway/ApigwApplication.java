package cz.cvut.fel.apigateway;

import cz.cvut.fel.apigateway.config.RedisHashComponent;
import cz.cvut.fel.apigateway.dto.ApiKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class ApigwApplication {

	@Value("room-reservation-key")
	private String ROOM_RESERVATION_KEY;

	@Value("invoice-key")
	private String INVOICE_KEY;

	@Value("user-key")
	private String USER_KEY;

	@Value("record-key")
	private String RECORD_KEY;

	@Autowired
	private RedisHashComponent redisHashComponent;

	public static void main(String[] args) {
		SpringApplication.run(ApigwApplication.class, args);
	}

	@PostConstruct
	public void initKeysToRedis() {
		List<ApiKey> apiKeys = new ArrayList<>();
		apiKeys.add(new ApiKey("343C-ED0B-4137-B27E", Stream.of(ROOM_RESERVATION_KEY,
				INVOICE_KEY, USER_KEY).collect(Collectors.toList())));

		apiKeys.add(new ApiKey("FA48-EF0C-427E-8CCF", Stream.of(ROOM_RESERVATION_KEY)
				.collect(Collectors.toList())));

		apiKeys.add(new ApiKey("ANS8-ESWC-867D-HGD8", Stream.of(USER_KEY)
				.collect(Collectors.toList())));

		List<Object> lists = redisHashComponent.hValues(RECORD_KEY);

		if (!lists.isEmpty()) {
			apiKeys.forEach(k -> redisHashComponent.hSet(RECORD_KEY, k.getKey(), k));
		}
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(
						INVOICE_KEY,
						r -> r
								.path("/invoice-service/**")
								.uri("http://localhost:9002"))

				.route(
						ROOM_RESERVATION_KEY,
						r -> r
								.path("/room-reservation-service/**")
								.uri("http://localhost:9001"))

				.route(
						USER_KEY,
						r -> r
								.path("/user-service/**")
								.uri("http://localhost:9003")
				)

				.build();
	}

}
