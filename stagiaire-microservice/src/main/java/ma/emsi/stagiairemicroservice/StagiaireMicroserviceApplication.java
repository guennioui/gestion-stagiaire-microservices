package ma.emsi.stagiairemicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class StagiaireMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StagiaireMicroserviceApplication.class, args);
    }

}
