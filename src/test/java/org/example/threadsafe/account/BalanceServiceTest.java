package org.example.threadsafe.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BalanceServiceTest {

    @DisplayName("한명의 유저에게게 잔고 입금과 출금 요청이 동시에 2개 이상 올 경우 차례대로 실행한다.")
    @Test
    void withdraw() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
        BalanceRepository balanceRepository = new BalanceRepository();
        balanceRepository.save(1L, Account.builder().balance(1000).build());
        BalanceService balanceService = new BalanceService(balanceRepository);
        CountDownLatch countDownLatch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                try {
                    balanceService.withdraw(1L, 1);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        Account account = balanceService.balance(1L);
        assertThat(account.getBalance()).isEqualTo(900L);
    }


    @DisplayName("한명의 유저에게 동시에 2개 이상 올 경우 실패한다.")
    @Test
    void deposit() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
        BalanceRepository balanceRepository = new BalanceRepository();
        balanceRepository.save(1L, Account.builder().balance(1000).build());
        BalanceService balanceService = new BalanceService(balanceRepository);
        Future<Account> test1 = executorService.submit(() -> balanceService.deposit(1L, 1));
        Future<Account> test2 = executorService.submit(() -> balanceService.deposit(1L, 1));
        Account account = test1.get();
        assertThat(account.getBalance()).isEqualTo(1001L);
        assertThatThrownBy(() -> {
            try {
                test2.get();
            } catch (ExecutionException e) {
                throw e.getCause();
            }
        }).isInstanceOf(RuntimeException.class);
    }
}
