package com.labs.systemdesign.exercise06transfer;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TODO (optional, for the pessimistic-locking variant): add a finder annotated
 * with @Lock(LockModeType.PESSIMISTIC_WRITE) that the debit path uses, e.g.
 *
 *   @Lock(LockModeType.PESSIMISTIC_WRITE)
 *   @Query("select a from Account a where a.id = :id")
 *   Optional<Account> findByIdForUpdate(@Param("id") Long id);
 */
public interface AccountRepository extends JpaRepository<Account, Long> {}
