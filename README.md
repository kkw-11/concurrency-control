# 🛠️ Concurrency Control Practice (동시성 제어 역량 강화 프로젝트)

## 📌 프로젝트 개요

* **프로젝트명:** Concurrency Stock Order
* **프로젝트 기간:** 2025.07 \~ 2025.08 (약 2주)
* **목적:** 재고 관리와 주문 처리 과정에서 발생할 수 있는 **동시성 문제(Lost Update, Race Condition)** 를 직접 재현하고,
  다양한 동시성 제어 방법을 적용하여 해결 방안을 탐구

---

## ⚙️ 기술 스택

* **Language:** Java 17
* **Framework:** Spring Boot 3.4.8, Spring Data JPA
* **Database:** H2 (in-memory for test)
* **Test:** JUnit 5
* **Build Tool:** Gradle
* **Infra (추가 예정):** Redis (분산 락 실험)

---

## 📝 실험 내용

1. **문제 재현**

    * `ExecutorService`, `CountDownLatch` 를 사용하여 100명 동시 주문 상황을 시뮬레이션
    * 동시성 제어가 없을 경우:

        * 주문이 100개를 초과해서 들어가거나
        * 재고가 0이 되지 않고 잘못 남는 문제 확인 (**Lost Update 발생**)

2. **해결 시도**

    * **트랜잭션 격리 수준 (REPEATABLE\_READ)** → Lost Update 완전히 방지하지 못함
    * **DB 레벨 비관적 락 (Pessimistic Lock)** 적용 → 정확히 100건만 처리되고 초과 주문은 예외로 차단됨

3. **결과**

    * Lost Update 문제 해결
    * 데이터 정합성 확보
    * 단, **TPS(초당 트랜잭션 처리량) 저하**라는 trade-off 존재


---

## 📚 프로젝트를 통해 얻은 것

* 단순 이론이 아닌 **실제 동시성 문제 상황을 재현**하며, 동시성 제어 필요성을 직접 경험하며 이해
* **JPA & DB Lock**을 통한 **데이터 정합성 보장** 방식 습득


---

## 🚀 앞으로의 확장 계획

* 낙관적 락(Optimistic Lock) 실험 → 성능 비교
* Redis 분산락 적용 → 분산 환경에서의 동시성 제어 실험
* TPS 측정 및 성능 보고서 작성
