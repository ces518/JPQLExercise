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
            /*
            List<Member> members = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();

            for (Member member : members) {
                System.out.println("member.getUsername() = " + member.getUsername());
            }

             */

            /**
             * 조인
             */
            /*
            Team team = new Team();
            team.setName("TeamA");

            em.persist(team);

            Member member = new Member();
            member.setAge(10);
            member.setUsername("HELLO");

            member.changeTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            // 내부 조인
            List<Member> members = em.createQuery("select m from Member m join m.team t", Member.class)
                    .getResultList();

            // 외부 조인
            List<Member> leftMembers = em.createQuery("select m from Member m left join m.team t", Member.class)
                    .getResultList();

            // 세타 조인
            List<Member> setaJoin = em.createQuery("select m from Member m, m.team t where m.username = t.name", Member.class)
                        .getResultList();

            // 조인 대상 필터링
            List<Member> filteredMember = em.createQuery("select m from Member m left join m.team t on t.name = 'TeamA'", Member.class)
                        .getResultList();

            // 연관관계가 없는 엔티티 외부 조인
            List<Member> leftJoin = em.createQuery("select m from Member m left join Team t on m.username = t.name", Member.class)
                    .getResultList();


             */

            /**
             * JPQL 타입표현
             */
            /*
            // string, boolean, 숫자는 자바와 동일하고, enum 타입은 풀패키지 경로를 입력해 주어야한다.
            List<Object[]> result = em.createQuery("select m.username, 'HELLO', true from Member m where m.type = jpql.MemberType.USER")
                .getResultList();

            // 상속관계에서 다음과 같이 엔티티 타입 사용도 가능하다.
            em.createQuery("select i from Item i where type(i) = Book");


             */

            /**
             * 조건식
             */
            /*
            // case 문
            em.createQuery("select " +
                                        "case when m.age <= 10 then '학생요금' " +
                                            " when m.age >= 60 then '경로요금' " +
                                            " else '일반요금' end " +
                                    "from Member m");

            // coalesce
            // 이름이 없다면, 이름 없는 회원이라고 출력
            em.createQuery("select coalesce(m.username, '이름 없는 회원') from Member m");

            // nullif
            // 이름이 관리자 라면 null로 출력
            em.createQuery("select nullif(m.username, '관리자') from Member m");

             */

            /**
             * JPQL 함수
             */
            // 기본 함수
            em.createQuery("select concat('a', 'b') from Member m");
            em.createQuery("select substring(m.username, 2, 3) from Member m");
            em.createQuery("select trim(m.username) from Member m");
            em.createQuery("select lower(m.username) from Member m ");
            em.createQuery("select upper(m.username) from Member m ");
            em.createQuery("select length(m.username) from Member m");
            // 해당 문자열의 위치를 반환
            em.createQuery("select locate('de', 'abcdef') from Member m ");
            em.createQuery("select abs(1.0) from Member m");
            // 연관관계를 맺고 있는 컬렉션의 크기를 알려준다. (JPA 함수)
            em.createQuery("select size(t.members) from Team t");
            // 값 타입 컬렉션에서의 인덱스를 반환한다. 사용하지 않는것을 추천 (JPA함수)
            em.createQuery("select index(t.members) from Team t");

            // 사용자 정의 함수
            // 이름1, 이름2, 이름3 형태로 한줄로 합쳐버린다.
            em.createQuery("select function('group_concat', m.username) from Member m");
            // 하이버네이트 일경우
            em.createQuery("select group_concat(m.username) from Member m");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.clear();
        }
        emf.close();
    }
}
