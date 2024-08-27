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

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
