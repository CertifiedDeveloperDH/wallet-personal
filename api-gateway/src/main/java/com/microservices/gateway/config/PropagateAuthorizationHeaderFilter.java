package com.microservices.gateway.config;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;

public class PropagateAuthorizationHeaderFilter extends AbstractGatewayFilterFactory<PropagateAuthorizationHeaderFilter.Config> {

    public PropagateAuthorizationHeaderFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Verifica si hay un encabezado Authorization en la solicitud original
            HttpHeaders headers = exchange.getRequest().getHeaders();
            if (headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                // Si existe, lo propaga hacia el siguiente servicio (API-CUENTA)
                exchange = exchange.mutate()
                        .request(r -> r.headers(httpHeaders -> httpHeaders.add(HttpHeaders.AUTHORIZATION, headers.getFirst(HttpHeaders.AUTHORIZATION))))
                        .build();
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Aquí puedes agregar configuraciones adicionales si es necesario
    }
}
