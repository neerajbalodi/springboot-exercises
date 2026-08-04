package com.labs.systemdesign.exercise07pagination;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * EXERCISE 07 — Keyset (cursor) pagination.
 *
 * "All orders for a customer" can be 100k rows. OFFSET-based paging gets slower
 * the deeper you go, because the DB still walks and discards all the skipped rows.
 * Keyset paging instead asks for "the next N rows AFTER the last id I saw".
 *
 * Fix the query below:
 *   TODO: it currently uses id >= :afterId, which re-includes the cursor row on
 *         every page (duplicates / off-by-one). Make it return rows STRICTLY
 *         after the cursor, ordered by id ascending, limited by the Pageable.
 *
 * Pass afterId = 0 for the first page.
 */
public interface OrderRepository extends JpaRepository<OrderRow, Long> {

    // TODO: change ">=" to ">" so the cursor row itself is not returned again.
    @Query("select o from OrderRow o where o.customerId = :customerId and o.id >= :afterId order by o.id asc")
    List<OrderRow> findNextPage(@Param("customerId") Long customerId,
                                @Param("afterId") Long afterId,
                                Pageable limit);
}
