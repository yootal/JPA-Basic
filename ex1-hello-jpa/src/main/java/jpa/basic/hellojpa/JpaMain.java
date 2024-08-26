package jpa.basic.hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // code
        try {
//            Member member1 = new Member();
//            member1.setId(1L);
//            member1.setUsername("HelloA");
//            em.persist(member1);
//
//            Member member2 = new Member();
//            member2.setId(2L);
//            member2.setUsername("HelloB");
//            em.persist(member2);
//
//            Member findMember = em.find(Member.class, 1L);
//            findMember.setUsername("HelloA2");
//            System.out.println("findMember.getId() = " + findMember.getId());
//            System.out.println("findMember.getName() = " + findMember.getUsername());
//            List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
//            for (Member member : result) System.out.println("member.getName() = " + member.getUsername());
//
//            // 비영속
//            Member member = new Member();
//            member.setId(101L);
//            member.setUsername("HelloJPA");
//
//            // 영속
//            System.out.println("=== BEFORE ===");
//            em.persist(member);
//            System.out.println("=== AFTER ===");
//
//            findMember = em.find(Member.class, 101L);
//            System.out.println("findMember.getId() = " + findMember.getId());
//            System.out.println("findMember.getName() = " + findMember.getUsername());
//
//            Member findMember1 = em.find(Member.class, 1L);
//            Member findMember2 = em.find(Member.class, 1L);
//            System.out.println(findMember1 == findMember2);

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

            Member memberA = new Member();
            memberA.setUsername("A");

            Member memberB = new Member();
            memberB.setUsername("B");

            Member memberC = new Member();
            memberC.setUsername("C");

            System.out.println("==========================");

            em.persist(memberA); //1, 51
            em.persist(memberB); //MEM
            em.persist(memberC); //MEM

            System.out.println("memberA.getId() = " + memberA.getId());
            System.out.println("memberB.getId() = " + memberB.getId());
            System.out.println("memberC.getId() = " + memberC.getId());

            System.out.println("==========================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
