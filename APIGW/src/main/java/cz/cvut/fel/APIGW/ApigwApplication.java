package cz.cvut.fel.APIGW;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import cz.cvut.fel.APIGW.config.RedisHashComponent;
import cz.cvut.fel.APIGW.dto.ApiKey;
import cz.cvut.fel.APIGW.util.AppConstants;
import cz.cvut.fel.APIGW.util.MapperUtils;
import io.lettuce.core.api.StatefulRedisConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class ApigwApplication {


	@Autowired
	private RedisHashComponent redisHashComponent;

	public static void main(String[] args) {
		SpringApplication.run(ApigwApplication.class, args);
	}

	@PostConstruct
	public void initKeysToRedis() {
		List<ApiKey> apiKeys = new ArrayList<>();
		apiKeys.add(new ApiKey("343C-ED0B-4137-B27E", Stream.of(AppConstants.ROOM_RESERVATION_KEY,
				AppConstants.INVOICE_KEY, AppConstants.USER_KEY).collect(Collectors.toList())));

		apiKeys.add(new ApiKey("FA48-EF0C-427E-8CCF", Stream.of(AppConstants.ROOM_RESERVATION_KEY)
				.collect(Collectors.toList())));

		apiKeys.add(new ApiKey("ANS8-ESWC-867D-HGD8", Stream.of(AppConstants.USER_KEY)
				.collect(Collectors.toList())));

		List<Object> lists = redisHashComponent.hValues(AppConstants.RECORD_KEY);

		if (!lists.isEmpty()) {
			apiKeys.forEach(k -> redisHashComponent.hSet(AppConstants.RECORD_KEY, k.getKey(), k));
		}
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(
						AppConstants.INVOICE_KEY,
						r -> r
								.path("/invoice-service/**")
								.uri("http://localhost:9002"))

				.route(
						AppConstants.ROOM_RESERVATION_KEY,
						r -> r
								.path("/room-reservation-service/**")
								.uri("http://localhost:9001"))

				.route(
						AppConstants.USER_KEY,
						r -> r
								.path("/user-service/**")
								.uri("http://localhost:9003")
				)

				.build();
	}

}
