package org.example.threadsafe.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final BalanceRepository balanceRepository;
    private final ConcurrentHashMap<Long, ReentrantLock> locks = new ConcurrentHashMap<>();

    public Account withdraw(long id, long amount) {
        ReentrantLock lock = getLock(id);
        lock.lock();
        try {
            Account account = getAccount(id);
            if (account.getBalance() < amount) {
                throw new IllegalArgumentException("Not enough balance");
            }
            account.withdraw(amount);
            return account;
        } finally {
            lock.unlock();
        }
    }

    private ReentrantLock getLock(long id) {
        return locks.computeIfAbsent(id, k -> new ReentrantLock());
    }

    public Account deposit(long id, long amount) {
        ReentrantLock lock = getLock(id);
        if (lock.tryLock()) {
            try {
                Thread.sleep(100);
                Account account = getAccount(id);
                account.deposit(amount);
                balanceRepository.save(id, account);
                return account;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("에러 발생");
            throw new RuntimeException("Account is locked");
        }
    }
    public Account balance(long id) {
        return getAccount(id);
    }

    private Account getAccount(long id) {
        return balanceRepository.findById(id);
    }
}
