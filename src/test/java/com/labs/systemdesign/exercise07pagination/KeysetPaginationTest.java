package com.labs.systemdesign.exercise07pagination;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class KeysetPaginationTest {

    @Autowired
    OrderRepository repo;

    @Test
    void nextPage_returnsRowsStrictlyAfterTheCursor() {
        long customer = 42L;
        for (int i = 0; i < 5; i++) {
            repo.save(new OrderRow(customer, "order-" + i));
        }
        List<OrderRow> all = repo.findNextPage(customer, 0L, PageRequest.of(0, 10));
        assertThat(all).hasSize(5);

        Long secondId = all.get(1).getId();

        List<OrderRow> afterSecond = repo.findNextPage(customer, secondId, PageRequest.of(0, 10));

        assertThat(afterSecond)
                .as("cursor row must be excluded; only strictly-later ids come back")
                .extracting(OrderRow::getId)
                .allMatch(id -> id > secondId)
                .hasSize(3);
    }

    @Test
    void limit_isRespected() {
        long customer = 7L;
        for (int i = 0; i < 5; i++) {
            repo.save(new OrderRow(customer, "o" + i));
        }
        List<OrderRow> firstTwo = repo.findNextPage(customer, 0L, PageRequest.of(0, 2));
        assertThat(firstTwo).hasSize(2);
    }
}
