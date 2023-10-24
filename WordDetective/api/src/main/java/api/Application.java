package api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import core.GameLogic;

/**
 *
 * Spring Boot application starter class.
 */
@SpringBootApplication
public class Application {

    /**
     * Main method for starting the application.
     *
     * @param args Argument used to start the program.
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Primary
    @Bean
    @Scope("session")
    public GameLogic gameLogic() {
        return new GameLogic();
    }

}
