package org.example.threadsafe.account;


import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class BalanceRepository {
    private final HashMap<Long, Account> db = new HashMap<>();


    public Account findById(Long id) {
        return db.computeIfAbsent(id, k -> Account.builder()
                .balance(0)
                .updateMilli(System.currentTimeMillis())
                .updateNano(System.nanoTime())
                .build());
    }

    public void save (Long id,Account account) {
        db.put(id, account);
    }
}
