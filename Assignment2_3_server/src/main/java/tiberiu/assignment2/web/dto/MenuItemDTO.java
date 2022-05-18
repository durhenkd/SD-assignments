package tiberiu.assignment2.web.dto;


import lombok.Value;
import tiberiu.assignment2.model.FoodCategory;

@Value
public class MenuItemDTO {

    String name;
    String description;
    String imageUrl;
    FoodCategory foodCategory;
    Double price;

}
