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
            /**
             * JPQL 기본문법
             */
            /*
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

             */

            /**
             * 프로젝션
             *
             */
            /*
            // 엔티티 프로젝션을 사용하면 해당 엔티티들은 모두 영속성 컨텍스트에서 관리된다.
            List<Member> members = em.createQuery("select m from Member m", Member.class)
                    .getResultList();

            // JOIN 할 경우 JPQL 에도 join을 같이 써주어야 한다.
            // JOIN을 하지 않아도, 조인이 되어 결과같이 나가는 경우가 있지만 JPQL에도 직접 명시적으로 해주는것이 좋음
            Team findTeam = em.createQuery("select t from Member m join m.team t", Team.class)
                    .getSingleResult();

            // 임베디드 타입 프로젝션
            Address findAddress = em.createQuery("select o.address from Order o", Address.class)
                    .getSingleResult();

            // 스칼라 타입 프로젝션
            List resultList = em.createQuery("select distinct m.username, m.age from Member m")
                    .getResultList();

            // 1.Query 타입을 사용하면 내부적인 값들은 Object 배열로 리턴된다.
            // 2.제네릭을 사용하여 이 과정을 생략할 수 있다.
            Object o = resultList.get(0);
            Object[] result = (Object[]) o;

            // 3.new Operation 사용
            // DTO를 활용하여 DTO 타입으로 받아온다.
            List<MemberDTO> memberDtos = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();

             */

            /**
             * 페이징
             */
            List<Member> members = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();

            for (Member member : members) {
                System.out.println("member.getUsername() = " + member.getUsername());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.clear();
        }
        emf.close();
    }
}
