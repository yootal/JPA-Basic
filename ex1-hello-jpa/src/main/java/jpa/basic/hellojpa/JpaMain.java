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

        // 비영속: 영속 X, 새로운 상태
        // 영속: 영속성 컨텍스트에 관리되는 상태
        // 준영속: 영속성 컨텍스트에 저장되었다가 분리된 상태
        // 삭제: 삭제된 상태

        // 영속성 컨텍스트의 이점
        // 1. 1차 캐시
        // 2. 동일성 보장
        // 3. 트랜잭션을 지원하는 쓰기 지연
        // 4. 변경 감지
        // 5. 지연 로딩

        // Dirty Checking: 변경 감지
        // flush: 영속성 컨텍스트의 변경내용을 데이터베이스에 반영
        // 트랜잭션이라는 작업 단위가 중요, 커밋 직전에만 동기화하면 됨

        // 영속 -> 준영속
        // detach(), clear(), close()

        // 테이블은 외래 키로 조인, 객체는 참조를 사용해 연관 객체 찾음
        // 객체 연관관계 단방향 + 단방향(2개), 테이블 연관관계(1개)
        // 객체를 양방향으로 참조하려면 단방향 연관관계를 2개 만들어야 한다.
        // 객체의 두 관계중 하나를 연관관계의 주인으로 지정
        // 주인만이 외래 키 등록, 수정 / 주인이 아닌 쪽은 읽기만 가능
        // 주인은 mappedBy 속성 X
        // 외래 키가 있는 곳을 주인으로 설정 (N)
        // 연관관계의 주인에 값 설정

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
        // @OneToOne, @OneToMany 만 가능

        // JPA 데이터 타입: 엔티티, 값 타입
        // 불변으로 부작용 막기 추천
        // 동일성 비교: 인스턴스의 참조 값 비교
        // 동등성 비교: 인스턴스의 값을 비교

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

        // JPA 쿼리 방법
        // 1. JPQL: 객체 지향 SQL, 특정 데이터베이스 SQL 의존 X
        // 2. JPA Criteria
        // 3. QueryDSL: 자바코드로 JPQL 작성 가능, JPQL 빌더 역할, 컴파일 오류 체크, 동적쿼리 작성
        // 4. 네이티브 SQL
        // 5. JDBC API 직접 사용, MyBatis, SpringJdbcTemplate 함께 사용

        // 모든 DB 데이터를 객체로 변환해서 검색하는 것은 불가능

        // JPQL: SQL 추상화한 객체 지향 쿼리 언어, 특정 DB SQL 의존 X
        // 1. 엔티티와 속성은 대소문자 구분, 키워드는 대소문자 구분 X(SELECT, FROM, where)
        // 2. 엔티티 이름 사용
        // 3. 별칭 필수
        // TypeQuery: 반환 타입 명확 O, Query: 반환 타입 명확 X
        // query.getResultList(): 결과 하나 이상, query.getSingleResult(): 결과 정확히 하나

        // 프로젝션
        // SELECT 절에 조회할 대상 지정
        // 명시적 조인을 하자

        // 페이징
        // setFirstResult, setMaxResults

        // 서브 쿼리(EXISTS, ALL, ANY, SOME..)
        // JPA -> WHERE, HAVING 절에서만 서브 쿼리 가능, SELECT 절에서도 가능(Hibernate 지원)
        // FROM 절 서브 커리는 불가능 -> 조인 활용

        // 경로 표현식 (명시적 조인 사용하자)
        // 1. 상태 필드: 단순 값 저장
        // 2. 연관 필드 (단일 값, 컬렉션 값): 연관관계 필드

        // fetch join: 연관된 엔티티나 컬렉션을 SQL 한 번에 함께 조회
        // N+1 문제 방지
        // JPQL DISTINCT: SQL DISTINCT 추가, 애플리케이션에서 엔티티 중복 제거
        // 일반 조인은 연관된 엔티티를 함께 조회하지 않는다.
        // 1. 페치 조인 대상에는 별칭을 줄 수 없다. (Hibernate 가능, 가급적 사용 X)
        // 2. 둘 이상의 컬렉션은 페치 조인 불가능
        // 3. 컬렉션을 페치 조인하면 페이징 API 사용 불가 (일대일, 다대일 같은 단일 값 연관 필드는 가능, 컬렉션이면 @BatchSize)
        // 특징과 한계:
        // 1. 연관된 엔티티들을 SQL 한 번으로 조회 -> 성능 최적화
        // 2. 엔티티에 직접 적용하는 글로벌 로딩 전략보다 우선함
        // 3. 최적화가 필요한 곳은 페치 조인 적용
        // 정리:
        // 1. 모든 것을 페치 조인으로 해결할 수는 없음
        // 2. 페치 조인은 객체 그래프를 유지할 때 사용하면 효과적
        // 3. 여러 테이블을 조인해서 엔티티가 가진 모양이 아닌 전혀 다른 결과를 내야 하면,
        // 페치 조인 보다는 일반 조인을 사용하고 필요한 데이터들만 조회해서 DTO 반환하는 것이 효과적

        // Named 쿼리: 미리 정의해서 이름을 부여해두고 사용하는 JPQL
        // 애플리케이션 로딩 시점에 오류 발견

        // 벌크 연산: 쿼리 한 번으로 여러 테이블 로우 변경(엔티티)
        // 영속성 컨텍스트 무시, 데이터베이스에 직접 쿼리 주의

        try {
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
