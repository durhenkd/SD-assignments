package tiberiu.assignment2.model;

import tiberiu.assignment2.exceptions.WrongStatusFlowException;

public enum OrderStatus {
    PENDING("Pending"),
    ACCEPTED("Accepted"),
    IN_DELIVERY("In delivery"),
    DELIVERED("Delivered"),
    DECLINED("Declined");

    final private String name;

    OrderStatus(String name) {
        this.name = name;
    }

    static public OrderStatus accept(OrderStatus status) throws WrongStatusFlowException {
        return switch (status) {
            case PENDING -> ACCEPTED;
            case ACCEPTED -> IN_DELIVERY;
            case IN_DELIVERY -> DELIVERED;
            default -> throw new WrongStatusFlowException();
        };
    }

    static public OrderStatus refuse(OrderStatus status) throws WrongStatusFlowException {
        if (status == PENDING) return DECLINED;
        throw new WrongStatusFlowException();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Status: " + name;
    }
}
