package jorados.pupfit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // 특정 필드 감사
public class PupfitApplication {

    public static void main(String[] args) {
        SpringApplication.run(PupfitApplication.class, args);
    }

}
