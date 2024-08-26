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

//            Member memberA = new Member();
//            memberA.setUsername("A");
//
//            Member memberB = new Member();
//            memberB.setUsername("B");
//
//            Member memberC = new Member();
//            memberC.setUsername("C");
//
//            System.out.println("==========================");
//
//            em.persist(memberA); //1, 51
//            em.persist(memberB); //MEM
//            em.persist(memberC); //MEM
//
//            System.out.println("memberA.getId() = " + memberA.getId());
//            System.out.println("memberB.getId() = " + memberB.getId());
//            System.out.println("memberC.getId() = " + memberC.getId());
//
//            System.out.println("==========================");

            // 테이블은 외래 키로 조인, 객체는 참조를 사용해 연관 객체 찾음

            // 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
//            member.changeTeam(team);
            em.persist(member);

            // 객체 지향적으로 양쪽에 넣어주는게 맞다
            // 연관관계 편의 메서드 활용
            // 양방향 매핑시에 무한 루프 주의
//            team.getMembers().add(member);
            team.addMember(member);

            em.flush();
            em.clear();

//            Member findMember = em.find(Member.class, member.getId());
//            Team findTeam = findMember.getTeam();
//            System.out.println("findTeam.getName() = " + findTeam.getName());
            Team findTeam = em.find(Team.class, team.getId());
            List<Member> members = findTeam.getMembers();
            for (Member m : members) {
                System.out.println("m.getUsername() = " + m.getUsername());
            }
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
