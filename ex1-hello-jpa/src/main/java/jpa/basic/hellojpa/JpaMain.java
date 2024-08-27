package jpa.basic.hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 비영속 : 영속 X, 새로운 상태
            // 영속 : 영속성 컨텍스트에 관리되는 상태
            // 준영속 : 영속성 컨텍스트에 저장되었다가 분리된 상태
            // 삭제 : 삭제된 상태

            // 영속성 컨텍스트의 이점
            // 1. 1차 캐시
            // 2. 동일성 보장
            // 3. 트랜잭션을 지원하는 쓰기 지연
            // 4. 변경 감지
            // 5. 지연 로딩

            // Dirty Checking : 변경   감지
            // flush : 영속성 컨텍스트의 변경내용을 데이터베이스에 반영
            // 트랜잭션 작업단위, 커밋 직전에만 동기화

            // 영속 -> 준영속
            // detach(), clear(), close()

            // 테이블은 외래 키로 조인, 객체는 참조를 사용해 연관 객체 찾음

            // 객체 지향적으로 양쪽에 넣어주는게 맞다
            // 연관관계 편의 메서드 활용
            // 양방향 매핑시에 무한 루프 주의

            // 단방향 매핑만으로도 이미 연관관계 매핑은 완료
            // 양방향 매핑은 반대 방향으로 조회(객체 그래프 탐색) 기능이 추가된 것 뿐
            // JPQL 역방향으로 탐색할 일이 많음
            // 단뱡향 매핑을 잘하고 양방향은 필요할 때 추가

            // 프록시 특징
            // 1. 프록시 객체는 처음 사용할 때 한 번만 초기화
            // 2. 프록시 객체를 초기화 할 때, 프록시 객체가 실제 엔티티로 바뀌는 것은 아니다.
            // 초기화 되면 프록시 객체를 통해서 실제 엔티티에 접근 가능
            // 3. 프록시 객체는 원본 엔티티를 상속받는다. 타입 체크시 주의(instance of 사용)
            // 4. 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 getReference 호출 시 실제 엔티티 반환
            // 5. 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화하면 문제 발생(Hibernate 예외 발생)

            // 프록시 확인
            // 프록시 강제 초기화 : org.hibernate.Hibernate.initialize(entity); (JPA 표준에는 없다)

            // 지연로딩 -> 설정시 연관 엔티티 프록시 객체로 가져온다. 사용하는 시점에 초기화(DB 조회)
            // 즉시로딩 -> 연관 엔티티도 한 번에 조회
            // 가급적 지연 로딩만 사용
            // 1. 즉시로딩 사용시 예상못한 SQL 발생
            // 2. 즉시로딩 JPQL 사용시 N+1 문제 발생 (첫 쿼리 + 추가 쿼리 N)
            // List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
            // SQL: select * from Member
            // SQL: select * from Team where TEAM_ID == xxx
            // 3. ~ToOne 디폴트 즉시로딩, ~ToMany 디폴트 지연로딩

            // 영속성 전이 CASCADE : 특정 엔티티 영속 상태로 만들 때 연관된 엔티티도 함께 영속 상태로 만들 때
            // 영속성 전이는 연관관계 매핑과 관련 X, 영속화 편리함만 제공, 단일 소유자

            // 고아 객체
            // 부모 엔티티와 연관관계가 끊어진 자식 엔티티 자동 삭제, orphanRemoval = true 
            // 참조하는 곳이 하나일 때 사용, 특정 엔티티가 개인 소유할 때 사용
            // 부모 엔티티를 제거할 때도 동작

            // JPA 데이터 타입: 엔티티, 값 타입
            // 불변으로 부작용 막기 추천
            
            // 값 타입 컬렉션
            // @ElementCollection, @CollectionTable 사용
            // 데이터베이스는 컬렉션을 같은 테이블에 저장할 수 없다.
            // 컬렉션을 저장하기 위한 별도의 테이블이 필요함
            // 지연로딩, CASCADE + 고아객체 제거
            // 엔티티와 다르게 식별자 개념이 없다.
            // 값을 변경하면 추적이 어렵다.
            // 컬렉션에 변경이 있으면 주인 엔티티와 관련된 모든 데이터를 삭제하고, 다시 저장한다.
            // 값 타입 컬렉션을 매핑하는 테이블은 모든 컬럼을 묶어서 기본키를 구성해야 한다. null X, 중복 저장 X
            // 값 타입 컬렉션 대신 일대다 관계를 고려
            
            // 엔티티 타입: 식별자 O, 생명주기 관리, 공유
            // 값 타입: 식별자 X, 생명주기를 엔티티에 의존, 공유하지 않는 것이 안전, 불변 객체로 만드는 것이 안전
            // 식별자가 필요하고, 지속해서 값을 추적, 변경하면 엔티티

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
