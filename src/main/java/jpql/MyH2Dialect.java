package jpql;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 08/01/2020
 * Time: 8:46 오후
 **/
public class MyH2Dialect extends H2Dialect {
    public MyH2Dialect() {
        // 사용자 정의 함수를 사용하려면, Dialect를 생성한뒤, 생성자에서 등록을 해주어야한다.
        registerFunction("group_concat", new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));
    }
}
