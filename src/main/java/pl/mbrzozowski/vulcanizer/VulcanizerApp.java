package pl.mbrzozowski.vulcanizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class VulcanizerApp {

    public static void main(String[] args) {
        SpringApplication.run(VulcanizerApp.class, args);
    }
}
