package univ.master.mql.sportservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
@OpenAPIDefinition(info = @Info(title = "Sport Service API", version = "1.0", description = "All about Sports and trainers information"))
public class    SportServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SportServiceApplication.class, args);
    }

}
