package com.labs.systemdesign.exercise03caching;

import org.springframework.stereotype.Service;

/**
 * EXERCISE 03 — Cache a read-heavy catalog, and keep it correct on writes.
 *
 * getProduct() is hit thousands of times a minute; the row changes rarely.
 * Cache reads. But when a product is updated, a stale cache entry would keep
 * serving the old price — so the write path must refresh the cache.
 *
 * Complete this class:
 *   TODO 1: cache getProduct() by id in a cache named "products".
 *   TODO 2: make updateProduct() leave the cache CORRECT for that id
 *           (evict it, or put the fresh value) so later reads don't go stale.
 *
 * The starter has no caching annotations, so the test that expects the DB to be
 * hit only once for repeated reads will fail.
 */
@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    // TODO 1: add caching here.
    public Product getProduct(Long id) {
        return repo.findById(id).orElseThrow();
    }

    // TODO 2: keep the "products" cache correct for this id after the write.
    public Product updateProduct(Long id, String newName, java.math.BigDecimal newPrice) {
        Product p = repo.findById(id).orElseThrow();
        p.setName(newName);
        p.setPrice(newPrice);
        return repo.save(p);
    }
}
