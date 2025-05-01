package ru.skypro.homework.dto;

public class Order {

    private Long id;
    private Long petId;
    private Integer quantity;
    private String shipDate;
    private OrderStatus status;
    private Boolean complete;

    public Order() {
    }

    public Order(Long id, Long petId, Integer quantity, String shipDate, OrderStatus status, Boolean complete) {
        this.id = id;
        this.petId = petId;
        this.quantity = quantity;
        this.shipDate = shipDate;
        this.status = status;
        this.complete = complete;
    }
}
