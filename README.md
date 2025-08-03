# Coupon-Con
## 1. 프로젝트 개요
### 1-1. 프로젝트 소개
- 대규모 트래픽 상황에서 쿠폰 중복 발급 및 재고 초과 발급 문제를 방지하기 위해 동시성 제어가 적용된 선착순 쿠폰 이벤트 시스템을 개발하였습니다.
- MyBatis로 학습을 시작하되, 추후 JPA로 전환할 수 있도록 유연성을 고려한 Hexagonal Architecture(헥사고날 아키텍처) 기반으로 설계하였습니다.텍쳐 설계
### 1-2. 문제 정의
- 사용자가 동시에 쿠폰을 요청할 경우, 재고보다 많은 수량이 발급되는 "중복 발급 및 초과 발급 이슈가"가 발생 할 수 있습니다.
- 단일 서버 환경뿐 아니라 멀티 서버 환경에서도 데이터 정합성 보장이 어려운 문제가 존재합니다.
### 1-3. 문제 해결 전략
- **단계별 락 적용**을 통해 쿠폰 수량에 대한 정합성을 확보합니다.
  - 1단계: 단일 인스턴스 환경에서 **JVM `synchronized`** 사용 
  - 2단계: DB차원에서 **낙관적 락(Optimistic Lock)**, **비관적 락(Pessimistic Lock)** 사용
  - 3단계:  **Redis 분산락(Redisson)** Lettuce SpinLock, Redisson Lock을 도입하여 멀티 인스턴스 환경에서도 동시성 문제 해결
### 1-4. 기술 스택
- **Language: Java**
- **Server: 로컬 환경 (localhost:8080, 8081에서 테스트 진행)**
- **DB: Mysql**
- **Tech: Spring Boot, Mybatis , Redis , Docker , Flyway , Grafana/Prometheus**

## 2. 구현 상세
### 2-1. 백엔드 아키텍쳐
![레디스락](https://github.com/user-attachments/assets/6d2fe856-68c2-4aec-822e-5200501f80b9)

### 2-2 아키텍쳐 패키지 구조
![핵사곤](https://github.com/user-attachments/assets/4b4730ad-500c-4a4a-9a79-3741cb0d4a95)

- **Hexagonal Architecture 기반 설계**
    - 추후 JPA 도입 가능성을 고려하여, 영속성 계층이 유연하게 교체될 수 있도록 **도메인 중심의 계층 분리 설계(Port/Adapter 구조)** 를 적용하였습니다.
    - 실제 회사마다 다른 영속성 기술 (JPA , MyBatis 등)을 사용하는 현실을 반영해 기술 교체나 유지보수 시의 리스크를 최소화하는 방향으로 설계했습니다.
    - 내부 비지니스 로직은 도메인 레이어에 위치시키고 , 외부 의존성은 어댑터로 분리하여 유지보수성과 확장성을 확보하였습니다.
``````
📦 coupon_con
┣ 📜 CouponConApplication.java
┣ 📂application : 비지니스 로직 처리 및 UseCase 인터페이스 구현
┃ ┣ 📂callback : 콜백 스타일 리팩토링
┃ ┃ ┣ 📜 RedisLockService.java
┃ ┃ ┣ 📜 RedisLockTime.java
┃ ┃ ┗ 📜 RedisLockServiceImpl.java
┃ ┣ 📂mapper : 도메인 <-> DTO 변환
┃ ┃ ┣ 📜 MemberDtoMapper.java
┃ ┃ ┗ 📜 CouponDtoMapper.java
┃ ┣ 📂port
┃ ┃ ┣ 📂in : 애플리케이션 외부 → 내부 요청 처리용 인터페이스 (UseCase)
┃ ┃ ┃ ┣ 📂dto
┃ ┃ ┃ ┃ ┣ 📜 CreateCouponCommand.java
┃ ┃ ┃ ┃ ┣ 📜 CreateCouponRequest.java
┃ ┃ ┃ ┃ ┣ 📜 CreateCouponResponse.java
┃ ┃ ┃ ┃ ┣ 📜 CouponResponse.java
┃ ┃ ┃ ┃ ┣ 📜 DeleteCouponCommand.java
┃ ┃ ┃ ┃ ┣ 📜 MemberResponse.java
┃ ┃ ┃ ┃ ┣ 📜 UpdateCouponCommand.java
┃ ┃ ┃ ┃ ┗ 📜 UpdateCouponRequest.java
┃ ┃ ┃ ┣ 📜 CreateCouponUseCase.java
┃ ┃ ┃ ┣ 📜 DeleteCouponUseCase.java
┃ ┃ ┃ ┣ 📜 FindCouponUseCase.java
┃ ┃ ┃ ┣ 📜 FindMemberUseCase.java
┃ ┃ ┃ ┣ 📜 GetAllCouponUseCase.java
┃ ┃ ┃ ┗ 📜 IssueCouponToMemberUseCase.java
┃ ┃ ┣ 📂out : 애플리케이션 내부 → 외부 시스템(DB) 호출용 인터페이스 (Port)
┃ ┃ ┃ ┣ 📜 CreateCouponPort.java
┃ ┃ ┃ ┣ 📜 DeleteCouponByIdPort.java
┃ ┃ ┃ ┣ 📜 FindCouponPort.java
┃ ┃ ┃ ┣ 📜 FindMemberPort.java
┃ ┃ ┃ ┣ 📜 GetAllCouponPort.java
┃ ┃ ┃ ┣ 📜 IssueCouponToMemberPort.java
┃ ┃ ┃ ┣ 📜 RedisLettuceLockPort.java
┃ ┃ ┃ ┣ 📜 UpdateCouponPort.java
┃ ┃ ┃ ┗ 📜 UpdateQuantityCouponPort.java
┃ ┣ 📂service : 비지니스 로직 처리
┃ ┃ ┣ 📜 CouponIssueService.java
┃ ┃ ┣ 📜 CouponService.java
┃ ┃ ┣ 📜 LettuceLockFacade.java
┃ ┃ ┣ 📜 MemberService.java
┃ ┃ ┗ 📜 RedissonLockFacade.java
┣ 📂common : 공통 클래스
┃ ┗ 📂concurrency
┃ ┃ ┗ 📜 MaxThreadTest.java
┣ 📂domain : 비지니스 도메인
┃ ┣ 📜 Coupon.java
┃ ┣ 📜 Member.java
┃ ┗ 📜 MemberCouponIssue.java
┣ 📂infrastructure : DB(외부시스템) 통신 구현체
┃ ┣ 📜 CouponIssuePersistenceAdapter.java
┃ ┣ 📜 CouponPersistenceAdapter.java
┃ ┣ 📜 MemberPersistenceAdapter.java
┃ ┣ 📜 RedisLettuceLockAdapter.java
┃ ┣ 📂adapter
┃ ┃ ┣ 📂in : 외부 요청 → 애플리케이션 (Controller 구현체)
┃ ┃ ┃ ┗ 📂web
┃ ┃ ┃ ┃ ┣ 📜 CouponController.java
┃ ┃ ┃ ┃ ┣ 📜 MemberController.java
┃ ┃ ┃ ┃ ┗ 📜 MemberCouponIssueController.java
┃ ┃ ┗ 📂out : 애플리케이션 → 외부 시스템 (DB 등) 구현체
┃ ┃ ┃ ┣ 📂converter 도메인 <-> 엔티티 변환
┃ ┃ ┃ ┃ ┣ 📜 CouponEntityMapper.java
┃ ┃ ┃ ┃ ┣ 📜 CouponIssueEntityMapper.java
┃ ┃ ┃ ┃ ┗ 📜 MemberEntityMapper.java
┃ ┃ ┃ ┗ 📂persistence
┃ ┃ ┃ ┃ ┣ 📂entity 영속성 엔티티
┃ ┃ ┃ ┃ ┃ ┣ 📜 CouponMybatisEntity.java
┃ ┃ ┃ ┃ ┃ ┣ 📜 MemberCouponIssueMybatisEntity.java
┃ ┃ ┃ ┃ ┃ ┗ 📜 MemberMybatisEntity.java
┃ ┃ ┃ ┃ ┗ 📂mapper : Mybatis 매퍼
┃ ┃ ┃ ┃ ┃ ┣ 📜 CouponMapper.java
┃ ┃ ┃ ┃ ┃ ┣ 📜 MemberCouponIssueMapper.java
┃ ┃ ┃ ┃ ┃ ┗ 📜 MemberMapper.java
``````


### 2-3 데이터베이스 ERD
<img width="933" height="371" alt="스크린샷 2025-08-01 19 11 49" src="https://github.com/user-attachments/assets/893ef46e-24cc-44e3-a065-f0b8d36e4ee5" />

## 3. 성능 및 테스트
<img width="291" height="145" alt="스크린샷 2025-08-01 19 54 15" src="https://github.com/user-attachments/assets/7fac58cb-960f-4851-8192-8ebd6afe4484" />

- JMeter 사용하여 1초안에 2000명이 쿠폰을 발급 받는다.
- 쿠폰수량은 100개이고 , 회원은 2000명이다.

### 비관적 락 (Pessimistic Lock) : Mysql 락대기가 많이 일어난다. Throughput 처리량은 적당한 속도였다.
<img width="842" height="230" alt="스크린샷 2025-07-30 21 21 49" src="https://github.com/user-attachments/assets/55451e7f-18fd-4e36-87bf-240f67cfffea" />
<img width="547" height="295" alt="스크린샷 2025-07-30 21 31 45" src="https://github.com/user-attachments/assets/224d30da-0324-44df-929d-663aaadb2e30" />
<img width="1098" height="596" alt="스크린샷 2025-07-30 21 10 41" src="https://github.com/user-attachments/assets/88c4992f-fcc4-4fd3-afe3-108796b8db79" />

### Lettuce Lock : CPU 사용률이 다른 락보다 높다. Throughput 처리량도 속도도 낮고 응답속도가 느리다.
<img width="839" height="233" alt="스크린샷 2025-07-30 21 35 09" src="https://github.com/user-attachments/assets/8fb5b196-68e3-4d8e-8e97-dc86d15eab24" />
<img width="1101" height="527" alt="스크린샷 2025-07-30 21 32 59" src="https://github.com/user-attachments/assets/10b5e465-8e85-47a7-a7f3-b49e0341b107" />
<img width="1090" height="596" alt="스크린샷 2025-07-30 21 40 26" src="https://github.com/user-attachments/assets/640fdc8a-6157-4e31-8c8a-2a01e84a9fa4" />

### Redisson Lock : Redisson Lock은 비관적 락과 거의 비슷한 처리량을 보여, Redis 도입 여부에 대해 신중한 판단이 필요하다 생각이 들었다.
<img width="844" height="236" alt="스크린샷 2025-07-30 21 53 01 (1)" src="https://github.com/user-attachments/assets/e33ed550-2e7e-44cc-9514-d4096d4bc695" />
<img width="1104" height="543" alt="스크린샷 2025-07-30 21 50 01" src="https://github.com/user-attachments/assets/5fa94385-4f3f-44ea-b953-1fa7637e11ab" />
<img width="1095" height="586" alt="스크린샷 2025-07-30 21 56 28" src="https://github.com/user-attachments/assets/b11cb2e0-3cdd-4f2e-94bd-5792c8c346cc" />

## 4. 향후 및 개선점
### 4-1 회고 문제점
동시성 문제를 해결하기 위해 낙관적 락, 비관적 락 그리고 Redis 기반의 락까지 다양한 방식들을 적용해보았습니다. 특히 쿠폰 수량 차감의 정합성을 확보하고 , DB 병목 문제를 줄이고자  Redis 분산 락을 도입했습니다.  하지만 테스트와 자료 조사를 통해, Redis 역시도 완벽한 해법은 아니라는 사실을 깨달았습니다.
- 락을 획득 한 클라이언트가 GC(Stop-the-world) 네트워크 지연, 프로세스 중단 등으로 작업을 완료하지 못한채 TTL이 만료될 수 있습니다.
TTL을 늘리는면 되지 않는다는 생각을 해보았지만  이 방법은 일시적인 완화책일 뿐 근본적인 해결책은 아니였습니다.
    - 락을 잃은 클라이언트가 장애로 인해 락을 해제하지 못하더라도, 다른 클라이언트가 락을 획득하지 못해 전체 시스템이 **교착 상태(deadlock)** 에 빠질 위험이 커집니다.
    - 락을 획득한 프로세스가 멈춰도 다른 프로세스가 락을 획득하지 못하고 대기하는 시간이 길어져서 서비스 지연이 심해집니다.
- 일반적으로 GC는 매우 빠르게 수행되지만 드물게 잠금이 만료될 정도로 지속될 수 있고  **Martin Kleppmann 문서에도** 분산 환경에서도 네트워크 지연이나, 타이밍이슈로 락의 안정성이 무조건 보장되지 않는다라는 지적이 있습니다.
  
## 🔍 느낀 점
단순히 DB 부하를 줄이자는 이유만으로 Redis를 도입하는 것은 모든 문제를 해결할 수 없으며, 오히려 새로운 리스크를 불러올 수 있다는 것을 깨달았습니다.  
기술 도입이 완벽한 해결책이 되지는 못한다라는것을 느꼈고.  어떤 락 전략이 적절한지는 시스템의 규모, 장애 허용 수준, 운영 환경에 따라 달라지며, 기술 선택은 장점뿐 아니라 단점까지 충분히 인지한 상태에서 판단해야 한다고 느꼈습니다.  기술을 “도입하는 것”이 아니라, “언제,왜, 어떻게 도입해야 하는지 판단하는 능력”이 더 중요하다라고 느낀 프로젝트 였습니다.
### 4-2 ✨ 추후 확장 및 학습 방향
**대용량 트래픽을 안정적으로 처리하기 위해서는 kafka, RabbitMQ 같은 우선순위 메시지 큐 기술들을 학습하고 도입해볼 계획입니다. 또한 Redis의 안정성을 높이기 위해 Redis 클러스터 구성 및 RedLock 알고리즘에 대해서도 향후 학습 할 예정입니다.**
