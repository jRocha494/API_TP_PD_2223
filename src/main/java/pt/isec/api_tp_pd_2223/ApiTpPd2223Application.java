package pt.isec.api_tp_pd_2223;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ApiTpPd2223Application {

    public static void main(String[] args) {
        SpringApplication.run(ApiTpPd2223Application.class, args);
    }

}
