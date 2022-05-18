package tiberiu.assignment2.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tiberiu.assignment2.bussiness.AccountService;
import tiberiu.assignment2.exceptions.AlreadyExistsException;
import tiberiu.assignment2.exceptions.InvalidRegisterInputException;
import tiberiu.assignment2.web.dto.AdminRegistrationDTO;
import tiberiu.assignment2.web.dto.ErrorDTO;
import tiberiu.assignment2.web.dto.UserRegistrationDTO;

@RestController
@RequestMapping("/api/register")
@Slf4j
public class RegisterController {


    private final AccountService as;

    @Autowired
    public RegisterController(AccountService as) {
        this.as = as;
    }

    @GetMapping
    public ResponseEntity<String> test(){
        return ResponseEntity.ok().body("This is a test path");
    }

    @PostMapping("/user")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDTO user){
        log.info("Registering User:" + user);
        try {
            return ResponseEntity.ok().body(as.save(user));
        } catch (AlreadyExistsException | InvalidRegisterInputException e) {
            return ResponseEntity.internalServerError().body(
                    new ErrorDTO(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            HttpStatus.INTERNAL_SERVER_ERROR.name(),
                            e.getMessage()
                    )
            );
        }
    }

    @PostMapping("/admin")
    public ResponseEntity<?> register(@RequestBody AdminRegistrationDTO user) {
        log.info("Registering Admin: " + user);

        try {
            return ResponseEntity.ok().body(as.save(user));
        } catch (AlreadyExistsException | InvalidRegisterInputException e) {
            return ResponseEntity.internalServerError().body(
                    new ErrorDTO(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            HttpStatus.INTERNAL_SERVER_ERROR.name(),
                            e.getMessage()
                    )
            );
        }
    }

}
