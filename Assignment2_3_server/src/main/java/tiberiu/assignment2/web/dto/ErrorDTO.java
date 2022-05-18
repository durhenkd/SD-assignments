package tiberiu.assignment2.web.dto;

import lombok.Value;

@Value
public class ErrorDTO {

    Integer status;
    String error;
    String message;

}
