package org.example.threadsafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.locks.ReentrantLock;

@SpringBootApplication
public class ThreadSafeApplication {

    public static void main(String[] args) {

        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.tryLock();

        SpringApplication.run(ThreadSafeApplication.class, args);
    }

}
