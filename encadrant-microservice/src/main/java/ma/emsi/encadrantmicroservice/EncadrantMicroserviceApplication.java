package ma.emsi.encadrantmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootApplication
public class EncadrantMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EncadrantMicroserviceApplication.class, args);
    }

}
