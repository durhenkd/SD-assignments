package tiberiu.assignment2.web.dto;

import lombok.Value;

import java.util.List;

@Value
public class OrderDTO {

    Long userId;
    List<Integer> itemIdList;
    List<Integer> quantityList;
}
