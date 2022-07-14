package univ.master.mql.memberservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.representations.adapters.config.PolicyEnforcerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import univ.master.mql.memberservice.config.SecurityConfig;

@SpringBootApplication
@EnableTransactionManagement
@EnableEurekaClient
@OpenAPIDefinition(info = @Info(title = "Member Service API", version = "1.0", description = "All about Client and his membership Information"))
public class MemberServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberServiceApplication.class, args);
    }
    @Bean
    public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }
//
//    @Bean
//    @Primary
//    public KeycloakSpringBootProperties properties() {
//        final KeycloakSpringBootProperties props = new KeycloakSpringBootProperties();
//        final PolicyEnforcerConfig policyEnforcerConfig = new PolicyEnforcerConfig();
//        policyEnforcerConfig.setEnforcementMode(PolicyEnforcerConfig.EnforcementMode.ENFORCING);
//        policyEnforcerConfig.setUserManagedAccess(new PolicyEnforcerConfig.UserManagedAccessConfig());
//        props.setPolicyEnforcerConfig(policyEnforcerConfig);
//        return props;
//    }


}
