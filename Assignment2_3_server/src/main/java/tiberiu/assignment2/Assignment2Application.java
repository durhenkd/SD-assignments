package tiberiu.assignment2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@SpringBootApplication
public class Assignment2Application {

    public static void main(String[] args) {
        SpringApplication.run(Assignment2Application.class, args);
    }

    @PostMapping
    public ResponseEntity<?> ok(){
        return new ResponseEntity<>("Mesag", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
