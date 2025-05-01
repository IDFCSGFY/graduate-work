package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Order;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/store")
public class StoreController {

    @GetMapping("/inventory")
    public ResponseEntity<Map<String, Long>> findPetQuantityByStatus() {
        return ResponseEntity.ok(new HashMap<>(Map.of("status", 0L)));
    }

    @PostMapping("/order")
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {
        return ResponseEntity.ok(order);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Order> findOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(new Order());
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<String> deleteOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok("Order deleted");
    }
}
