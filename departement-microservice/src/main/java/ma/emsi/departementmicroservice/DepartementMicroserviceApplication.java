package ma.emsi.departementmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DepartementMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DepartementMicroserviceApplication.class, args);
    }
}
