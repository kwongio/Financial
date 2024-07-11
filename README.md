Java
이 프로젝트는 Java Spring Boot를 사용하여 동기화와 thread-safe 패턴을 실습하고 연습하기 위한 예제 프로젝트입니다. 본 프로젝트에서는 간단한 은행 계좌 관리 시스템을 구현하여, 입금, 출금 및 잔액 조회 기능을 thread-safe하게 처리하는 방법을 보여줍니다.

기능
계좌 생성: 새로운 사용자 계좌를 생성합니다.
잔액 조회: 특정 계좌의 현재 잔액을 조회합니다.
입금: 특정 계좌에 금액을 입금합니다.
출금: 특정 계좌에서 금액을 출금합니다.
모든 요청은 thread-safe하게 처리되어, 동시에 여러 요청이 발생해도 데이터의 일관성과 무결성을 유지합니다.

전제 조건
Java 17
gradle
Spring Boot
H2
Lombok
JPA

잔액 조회: GET /account/{id}/balance
입금: POST /account/{id}/deposit with JSON body {"amount": 100}
출금: POST /account/{id}/withdraw with JSON body {"amount": 50}
