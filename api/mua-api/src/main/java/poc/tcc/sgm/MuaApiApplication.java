package poc.tcc.sgm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MuaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MuaApiApplication.class, args);
	}

}
