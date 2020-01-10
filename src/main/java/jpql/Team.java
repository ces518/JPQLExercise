package jpql;

import org.hibernate.annotations.BatchSize;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 06/01/2020
 * Time: 1:52 오후
 **/
@Entity
public class Team {

    @Id @GeneratedValue
    private Long id;
    private String name;

    // lazy loading 상태에서, 한번에 IN 쿼리로 100개(사이즈값 만큼) 씩 넘긴다.
    // 1 + 1 방식으로 해결한다.
    // 페이징을 해서 팀이 10개라면, IN 쿼리로 팀의 아이디가 10개가 세팅되어 10개의 팀과 연관된 멤버를 모두 긁어온다.
    // N + 1 문제를 해결하는 다른 방법이다.
    @BatchSize(size = 100)
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
