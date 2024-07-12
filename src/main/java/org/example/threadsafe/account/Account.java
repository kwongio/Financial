package org.example.threadsafe.account;

import lombok.*;

@Getter
@ToString
public class Account {
    private long balance;
    private final long updateMilli;
    private final long updateNano;

    @Builder
    public Account(long balance, long updateMilli, long updateNano) {
        this.balance = balance;
        this.updateMilli = updateMilli;
        this.updateNano = updateNano;
    }

    public void deposit(long amount) {
        this.balance += amount;
    }

    public void withdraw(long amount) {
        this.balance -= amount;
    }
}
