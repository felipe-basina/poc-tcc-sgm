package poc.tcc.sgm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class SgmApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SgmApiGatewayApplication.class, args);
	}

}
