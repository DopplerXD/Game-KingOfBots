package site.dopplerxd.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class BackendApplicationTests {

    @Test
    void contextLoads() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("pa"));
        System.out.println(passwordEncoder.encode("newpc"));
        System.out.println(passwordEncoder.encode("pd"));
        System.out.println(passwordEncoder.encode("123"));
    }

}
