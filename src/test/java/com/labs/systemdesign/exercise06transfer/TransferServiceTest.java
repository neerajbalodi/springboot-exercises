package com.labs.systemdesign.exercise06transfer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class TransferServiceTest {

    @Autowired
    AccountRepository repo;

    @Test
    void validTransfer_movesMoney() {
        repo.save(new Account(1L, new BigDecimal("100.00")));
        repo.save(new Account(2L, new BigDecimal("0.00")));
        TransferService service = new TransferService(repo);

        service.transfer(1L, 2L, new BigDecimal("30.00"));

        assertThat(repo.findById(1L).orElseThrow().getBalance()).isEqualByComparingTo("70.00");
        assertThat(repo.findById(2L).orElseThrow().getBalance()).isEqualByComparingTo("30.00");
    }

    @Test
    void overdraft_isRejected_andBalancesUnchanged() {
        repo.save(new Account(1L, new BigDecimal("50.00")));
        repo.save(new Account(2L, new BigDecimal("0.00")));
        TransferService service = new TransferService(repo);

        assertThatThrownBy(() -> service.transfer(1L, 2L, new BigDecimal("80.00")))
                .isInstanceOf(InsufficientFundsException.class);

        assertThat(repo.findById(1L).orElseThrow().getBalance()).isEqualByComparingTo("50.00");
        assertThat(repo.findById(2L).orElseThrow().getBalance()).isEqualByComparingTo("0.00");
    }
}
