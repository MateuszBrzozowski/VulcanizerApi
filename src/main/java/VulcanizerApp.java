import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.mbrzozowski.entity.User;
import pl.mbrzozowski.enums.Gender;

@SpringBootApplication
public class VulcanizerApp {

    public static void main(String[] args) {
//        SpringApplication.run(VulcanizerApp.class, args);
        User build = User.builder()
                .gender(Gender.FEMALE)
                .build();
    }
}
