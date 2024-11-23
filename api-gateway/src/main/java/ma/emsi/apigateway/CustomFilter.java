    package ma.emsi.apigateway;

    import org.springframework.cloud.gateway.filter.GatewayFilter;
    import org.springframework.cloud.gateway.filter.GatewayFilterChain;
    import org.springframework.http.HttpStatus;
    import org.springframework.web.server.ServerWebExchange;
    import reactor.core.publisher.Mono;

    public class CustomFilter implements GatewayFilter {

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            String headerValue = exchange.getRequest().getHeaders().getFirst("X-Custom-Header");

            if (headerValue == null || !headerValue.equals("ValidValue")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return chain.filter(exchange);
            
        }
    }

