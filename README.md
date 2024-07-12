# Java Thread-Safe 동시성 제어

### 동시성 제어

1. ReentrantLock
    1. tryLock
    2. lock
2. ConcurrentHashMap

### 테스트
1. ExecutorService
2. CompletableFuture

### 사용기술
Spring Boot 3
Java 17
gradle
Lombok

- 잔액 조회: `GET /account/{id}/balance`
- 입금: `POST /account/{id}/deposit`
- 출금: `POST /account/{id}/withdraw`

### 제약조건
- 한명의 유저에게 잔고 입금 요청이 동시에 2개 이상 올 경우 실패
- 한명의 유저에게 잔고 입금과 출금 요청은 동시에 올 수 있고, 요청 온 차례대로 실행.
- 한명의 유저에게 잔고 출금 요청이 동시에 2개 이상 올 경우 차례대로 실행