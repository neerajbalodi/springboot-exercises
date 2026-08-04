package com.labs.systemdesign.exercise03caching;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * The repository is mocked with a tiny in-memory "database" (a map) so we can
 * observe both caching hits (read count) and correctness after a write.
 */
@SpringBootTest
class ProductCachingTest {

    @Autowired
    ProductService service;

    @Autowired
    CacheManager cacheManager;

    @MockBean
    ProductRepository repo;

    private final Map<Long, Product> db = new HashMap<>();

    private static Product copy(Product p) {
        return new Product(p.getId(), p.getName(), p.getPrice());
    }

    @BeforeEach
    void setUp() {
        db.clear();
        var cache = cacheManager.getCache("products");
        if (cache != null) cache.clear();

        // findById returns a fresh copy of current db state (or empty).
        when(repo.findById(any())).thenAnswer(inv -> {
            Long id = inv.getArgument(0);
            Product p = db.get(id);
            return p == null ? Optional.empty() : Optional.of(copy(p));
        });
        // save writes a copy into the db and returns it.
        when(repo.save(any(Product.class))).thenAnswer(inv -> {
            Product p = inv.getArgument(0);
            db.put(p.getId(), copy(p));
            return p;
        });
    }

    @Test
    void repeatedReads_hitDatabaseOnce() {
        db.put(1L, new Product(1L, "Mouse", new BigDecimal("9.99")));

        service.getProduct(1L);
        service.getProduct(1L);
        service.getProduct(1L);

        verify(repo, times(1)).findById(1L); // cached after the first read
    }

    @Test
    void afterUpdate_readReturnsFreshValue_notStaleCache() {
        db.put(2L, new Product(2L, "Keyboard", new BigDecimal("20.00")));

        service.getProduct(2L);                                                  // caches price 20.00
        service.updateProduct(2L, "Keyboard Pro", new BigDecimal("25.00"));      // must refresh cache

        Product after = service.getProduct(2L);

        assertThat(after.getPrice())
                .as("read after update must reflect the new price, not the cached old one")
                .isEqualByComparingTo("25.00");
    }
}
