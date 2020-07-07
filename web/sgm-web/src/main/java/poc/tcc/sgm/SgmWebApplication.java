package poc.tcc.sgm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SgmWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SgmWebApplication.class, args);
	}

}
