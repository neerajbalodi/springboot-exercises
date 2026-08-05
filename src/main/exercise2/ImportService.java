package com.labs.systemdesign.exercise04bulkimport;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * EXERCISE 02 — Import a huge list without blowing up the DB or heap.
 *
 * importAllNaive() does one insert + one flush per row and keeps every entity in
 * the persistence context. On a large file it crawls and eventually OOMs. It is
 * left here only as the "before" picture.
 *
 * Complete importAllBatched() so that:
 *   TODO 1: entities are inserted in chunks of BATCH_SIZE.
 *   TODO 2: after each chunk you flush() then clear() the EntityManager, so the
 *           persistence context does not grow unbounded.
 *   (The matching hibernate.jdbc.batch_size property is already set in
 *    application.properties — the code and the config have to agree.)
 *
 * The starter throws, so the test that imports thousands of rows fails until done.
 */
@Service
public class ImportService {

    public static final int BATCH_SIZE = 500;

    private final CustomerRepository repo;
    private final EntityManager entityManager;

    public ImportService(CustomerRepository repo, EntityManager entityManager) {
        this.repo = repo;
        this.entityManager = entityManager;
    }

    /** The slow "before" version — do not use in production. */
    @Transactional
    public void importAllNaive(List<Customer> customers) {
        for (Customer c : customers) {
            repo.save(c);
        }
    }

    @Transactional
    public void importAllBatched(List<Customer> customers) {
        // TODO: chunked inserts with periodic flush()+clear(). Delete the line below.
        throw new UnsupportedOperationException("Complete ImportService.importAllBatched(...)");
    }
}
