package univ.master.mql.administrationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient

public class AdministrationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdministrationServiceApplication.class, args);
    }





}
