package com.labs.systemdesign.exercise07pagination;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Worked example of shaping the API around the keyset query. Nothing to complete
 * here — it shows how the repository method turns into a cursor response.
 */
@RestController
@RequestMapping("/customers")
public class OrderController {

    private final OrderRepository repo;

    public OrderController(OrderRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/{customerId}/orders")
    public CursorPage<OrderRow> list(
            @PathVariable Long customerId,
            @RequestParam(defaultValue = "0") Long afterId,
            @RequestParam(defaultValue = "50") int size) {

        List<OrderRow> rows = repo.findNextPage(customerId, afterId, PageRequest.of(0, size));
        Long nextCursor = rows.isEmpty() ? null : rows.get(rows.size() - 1).getId();
        return new CursorPage<>(rows, nextCursor);
    }
}
