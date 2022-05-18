package tiberiu.assignment2.web.dto;

import lombok.Value;

@Value
public class AdminRegistrationDTO {

    String email;
    String password;

    String name;
    String location;
    String deliveryLocations;
    String photoUrl;

}
