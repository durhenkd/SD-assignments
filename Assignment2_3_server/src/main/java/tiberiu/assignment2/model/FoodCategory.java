package tiberiu.assignment2.model;

public enum FoodCategory {

    DESERT("Desert"),
    PASTA("Pasta"),
    BURGER("Burger"),
    RED_MEAT("Red Meat"),
    CHICKEN("Chicken"),
    SEA_FRUITS("Sea Fruits"),
    SALAD("Salad"),
    BREAKFAST("Breakfast"),
    COFFEE("Coffee"),
    MILKSHAKE("Milkshake"),
    SMOOTHIE("Smoothie"),
    ALCOHOL("Alcohol"),
    SOUP("Soup"),
    ASIAN("Asian");

    final private String name;

    FoodCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Category: " + name + ';';
    }
}
