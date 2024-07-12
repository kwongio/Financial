package org.example.threadsafe.account;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class BalanceController {
    private final BalanceService balanceService;
    @GetMapping("/{id}/balance")
    public Account balance(@PathVariable long id) {
        return balanceService.balance(id);
    }

    @PostMapping("/{id}/deposit")
    public Account deposit(@PathVariable long id, long amount) {
        try {
            return balanceService.deposit(id, amount);
        } catch (RuntimeException e) {
            return Account.builder()
                    .balance(-1)
                    .updateMilli(System.currentTimeMillis())
                    .updateNano(System.nanoTime())
                    .build();
        }
    }

    @PostMapping("/{id}/withdraw")
    public Account withdraw(@PathVariable long id, @RequestBody long amount) {
        return balanceService.withdraw(id, amount);
    }
}
