package shop.wannab.frontservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class FrontserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrontserviceApplication.class, args);
	}

}
