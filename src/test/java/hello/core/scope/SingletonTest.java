package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.*;

public class SingletonTest {

    @Test
    void singletonBeanFind() {
        // AnnotationConfigApplicationContext 파라미터는 컴포넌트클래스를 넣어주는거다.
        // SingletonBean을 넣어주면 자동으로 ComponentScan으로 되서 등록이된다.
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);

        SingletonBean singletonBean1 = ac.getBean(SingletonBean.class);
        SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);
        System.out.println("singletonBean1 = " + singletonBean1);
        System.out.println("singletonBean2 = " + singletonBean2);

        // 검증
        // isSameAs는 ==랑 같다.
        assertThat(singletonBean1).isSameAs(singletonBean2);

        ac.close();
    }

    // 싱글톤 빈이 있는 설정정보를 만들어보자
    @Scope("singleton") // 디폴트라서 안해도 되지만 공부할 겸 적는다.
    static class SingletonBean {
        @PostConstruct
        public void init() {
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("SingletonBean.destroy");
        }
    }
}
