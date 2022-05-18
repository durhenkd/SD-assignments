package tiberiu.assignment2.web.dto;

import lombok.Value;
import tiberiu.assignment2.model.User;

@Value
public class UserRegistrationDTO {

    String username;
    String firstName;
    String lastName;
    String email;
    String password;



}
