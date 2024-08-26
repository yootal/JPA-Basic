package jpa.basic.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpa.basic.jpashop.domain.Order;
import jpa.basic.jpashop.domain.OrderItem;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

//            Order order = em.find(Order.class, 1L);
//            Long memberId = order.getMemberId();
//            Member member = em.find(Member.class, memberId);

            Order order = new Order();
            order.addOrderItem(new OrderItem());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
