package com.labs.systemdesign.exercise04bulkimport;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ImportServiceTest {

    @Autowired
    CustomerRepository repo;

    @Autowired
    EntityManager entityManager;

    @Test
    void importsEveryRow_inBatches() {
        ImportService service = new ImportService(repo, entityManager);

        List<Customer> batch = new ArrayList<>();
        for (int i = 0; i < 2500; i++) {
            batch.add(new Customer("Customer " + i, "c" + i + "@example.com"));
        }

        service.importAllBatched(batch);

        assertThat(repo.count())
                .as("all rows must be persisted")
                .isEqualTo(2500);
    }
}
