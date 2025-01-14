package t3h.vn.testonline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TestOnlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestOnlineApplication.class, args);
    }

}
