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
            /*
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
             */
            /**
             * JPQL 경로 표현식
             */
            /*
            // 상태 필드 경로 표현식
            em.createQuery("select m.username from Member m");

            // 단일 값 연관 경로 표현식
            // 묵시적인 내부 조인이 발생한다.
            // 추가적인 탐색이 가능하다.
            // 묵시적인 내부조인이 발생하도록 JPQL을 짜서는 안된다.
            // 성능 튜닝 단계에서 영향을 미친다.
            // 왠만하면 JPQL 과 SQL을 맞추는것을 추천한다.
            em.createQuery("select m.team.id, m.team.name from Member m");

            // 컬렉션 값 연관경로
            // 컬렉션의 경우 어떠한 값을 꺼내야할지 난감하다.
            // JPA의 경우에는 제약을 두었음
            // 컬렉션 값 연관경로의 경우 묵시적 내부 조인이 발생하지만 추가 탐색은 불가능하다.
            // * From 절에서 명시적 조인을 통해 알리아스(별칭) 을 얻어온다면, 별칭을 통해 탐색이 가능하다.
            em.createQuery("select t.members from Team t");
             */
            /**
             * 페치조인 - 기본
             */
            Team teamA = new Team();
            teamA.setName("teamA");

            Team teamB = new Team();
            teamB.setName("teamB");

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(teamA);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(teamB);

            Member member3 = new Member();
            member3.setUsername("member3");
            member3.setTeam(teamB);

            em.persist(teamA);
            em.persist(teamB);
            em.persist(member1);
            em.persist(member2);
            em.persist(member3);

            em.flush();
            em.clear();

            // 이 시점에는 연관관계가 지연로딩 이기때문에 Member와, m.team은 프록시객체를 가지고 있다.
            List<Member> members = em.createQuery("select m from Member m", Member.class)
                    .getResultList();

            for (Member member : members) {
                // getTeam.getName() 을 호출하는 순간, 지연로딩이 되어, 쿼리가 추가적으로 나가게 된다.
                System.out.println("member = " + member.getUsername() + "," + member.getTeam().getName());
                // 회원 100명 조회시 -> 100번 나가게 됨.. (최악의 경우) N + 1 문제가 발생함.
                // 이를 해결하는것이 fetch join
            }

            // 페치 조인을 해서 team을 한방 쿼리로 가져온다.
            // 이때 팀의 정보를 같이 가져 왔기때문에 m.team은 프록시 객체가 아니다.
            List<Member> fetchMembers = em.createQuery("select m from Member m join fetch m.team", Member.class)
                    .getResultList();

            for (Member fetchMember : fetchMembers) {
                // N + 1 문제가 발생하지 않는다.
                System.out.println("fetchMember = " + fetchMember.getUsername() + "," + fetchMember.getTeam().getName());
            }

            // 컬렉션 페치 조인
            // 일대다 관계, 컬렉션 페치 조인
            // * 주의할점 *
            // 컬렉션조인 1:N 조인을 할경우 SQL에서도 마찬가지로 데이터가 뻥튀기 된다.
            // teamA 소속 회원이 멤버1, 멤버2가 있다면
            // teamA 멤버1
            // teamA 멤버2
            // 위와 같은형태로 로우가 2개로 나오며, teamA가 중복이 된다.
            // teamA 엔티티가 중복으로 나온다..
            List<Team> teams = em.createQuery("select t from Team t", Team.class)
                    .getResultList();

            for (Team team : teams) {
                System.out.println("team.getName() = " + team.getName() + "," + team.getMembers().size());
            }

            // 페치 조인과 DISTINCT
            // JPQL의 DISTINCT 2가지 기능 제공
            // 1. SQL에 DISTINCT를 추가한다.
            // 2. 애플리케이션에서 엔티티 중복을 제거한다.
            List<Team> distinctTeams = em.createQuery("select distinct  t from Team t", Team.class)
                    .getResultList();
            // SQL DISTINCT 만으로는 중복 제거가 되지않아, JPA에서 같은 식별자를 가진 Team 엔티티를 제거해서 원했던 결과가 나온다.
            System.out.println("distinctTeams.size() = " + distinctTeams.size());

            // 페치 조인과 일반조인의 차이
            // 일반 조인 실행시 연관된 엔티티를 함께 조회하지 않는다.

            // 실제 데이터는 t 에 대한 데이터만 조회하게 된다.
            // members에는 데이터가 로드되지 않았기 때문에 루프를 돌리면 SQL이 발생하게 된다.
            em.createQuery("select t from Team t join t.members m").getResultList();

            // 연관된 엔티티를 함께 조회한다.
            em.createQuery("select t from Team t join fetch t.members t").getResultList();


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.clear();
        }
        emf.close();
    }
}
