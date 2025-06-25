package shop.wannab.frontservice.config;

import feign.Client;
import feign.httpclient.ApacheHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.httpclient.ApacheHttpClient;

@Configuration
public class FeignConfig {
    @Bean
    public Client feignClient() {
        return new ApacheHttpClient(HttpClients.createDefault());
    }
}
