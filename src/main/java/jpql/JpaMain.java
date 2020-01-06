package jpql;

import javax.persistence.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 06/01/2020
 * Time: 1:54 오후
 **/
public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            TypedQuery<Member> memberQuery = em.createQuery("select m from Member m", Member.class);

            // username은 String, age 는 int 이기 때문에 타입 정보를 명확하게 줄 수 없다.
            Query query = em.createQuery("select m.username, m.age from Member m");


            // 결과값을 반환시 한개 이상의 결과를 받고싶다면  getResultList() 를 사용한다.
            List<Member> members = memberQuery.getResultList();

            // 결과값을 반드시 하나로 받고 싶을때 getSingleResult() 를 사용한다.
            Member singleMember = memberQuery.getSingleResult();


            // 파라미터 바인딩
            TypedQuery<Member> memberTypedQuery = em.createQuery("select m from Member m where m.username = :username", Member.class);
            // setParameter 를 활용하여 바인딩 해준다.
            memberTypedQuery.setParameter("username", "member1");
            Member singleResultMember = memberTypedQuery.getSingleResult();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.clear();
        }
        emf.close();
    }
}
