package cz.cvut.fel.APIGW.filter;

import cz.cvut.fel.APIGW.config.RedisHashComponent;
import cz.cvut.fel.APIGW.dto.ApiKey;
import cz.cvut.fel.APIGW.util.AppConstants;
import cz.cvut.fel.APIGW.util.MapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisHashComponent redisHashComponent;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        List<String> apiKeysHeader = exchange.getRequest().getHeaders().get("gatewaykey");

        log.info("apikey '{}'", apiKeysHeader);

        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        String routeId = route != null ? route.getId() : null;

        if(routeId == null || CollectionUtils.isEmpty(apiKeysHeader) || !isAuthorize(routeId, apiKeysHeader.get(0))) {
            log.warn("You can't consume this service, please validate your api keys");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You can't consume this service, please validate your api keys");
        }

        return chain.filter(exchange);
    }

    private boolean isAuthorize(String routeId, String apiKey) {
        Object apiKeyObject = redisHashComponent.hGet(AppConstants.RECORD_KEY, apiKey);

        if(apiKeyObject != null) {
            ApiKey key = MapperUtils.objectMapper(apiKeyObject, ApiKey.class);
            return key.getServices().contains(routeId);
        }

        return false;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
