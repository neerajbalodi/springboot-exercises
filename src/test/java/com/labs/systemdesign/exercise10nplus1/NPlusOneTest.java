package com.labs.systemdesign.exercise10nplus1;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.jpa.properties.hibernate.generate_statistics=true")
class NPlusOneTest {

    @Autowired
    PurchaseOrderRepository repo;

    @Autowired
    EntityManager entityManager;

    @Autowired
    EntityManagerFactory emf;

    @Test
    void loadingOrdersWithDetails_usesFewQueries() {
        // 4 orders, each with a distinct buyer and 2 line items.
        for (int i = 0; i < 4; i++) {
            PurchaseOrder order = new PurchaseOrder(new Buyer("buyer-" + i));
            order.addItem(new LineItem("sku-" + i + "-a"));
            order.addItem(new LineItem("sku-" + i + "-b"));
            repo.save(order);
        }

        // Flush inserts and DETACH everything, so the reads below actually hit the DB.
        // Without this, the seeded entities stay managed and lazy access is free —
        // which would hide the N+1.
        entityManager.flush();
        entityManager.clear();

        Statistics stats = emf.unwrap(SessionFactory.class).getStatistics();
        stats.clear();

        List<PurchaseOrder> orders = repo.findAllWithDetails();
        // Touch the lazy associations the screen would render.
        for (PurchaseOrder o : orders) {
            o.getBuyer().getName();
            o.getItems().size();
        }

        long statements = stats.getPrepareStatementCount();
        assertThat(statements)
                .as("buyer + items should be fetched together, not one query per order (N+1)")
                .isLessThanOrEqualTo(3);
    }
}
