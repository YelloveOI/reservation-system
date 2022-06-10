package cz.cvut.fel.APIGW;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;
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
	private StatefulRedisConnection<String, String> connection;


	public static void main(String[] args) {
		SpringApplication.run(ApigwApplication.class, args);
	}

	@PostConstruct
	public void initKeysToRedis() {
		Map<String, String> map = new HashMap<>();

		map.put("343C-ED0B-4137-B27E", AppConstants.ROOM_RESERVATION_KEY);
		map.put("FA48-EF0C-427E-8CCF", AppConstants.INVOICE_KEY);
		map.put("CC48-EDSC-432E-8JSF", AppConstants.USER_KEY);

		List<ApiKey> apiKeys = new ArrayList<>();

		List<String> list = connection.sync().hvals(AppConstants.RECORD_KEY);

		if(list.isEmpty()) {
			connection.sync().hset(AppConstants.RECORD_KEY, map);
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

				.build();
	}

}
