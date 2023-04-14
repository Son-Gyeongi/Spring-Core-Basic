package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.*;

public class PrototypeTest {
    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        // 프로토타입을 조회하기 직전에 생성이 된다. 보여주려고 출력
        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class); // 이때 새로운 객체 생성되면서 init 호출된다.
        System.out.println("find prototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class); // 이때 새로운 객체 생성되면서 init 호출된다.
        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);

        // 검증
        Assertions.assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        // 빈 종료할 때 직접 호출, destroy() 호출할 필요가 있으면
        prototypeBean1.destroy();
        prototypeBean2.destroy();
        ac.close();
    }

    @Scope("prototype")
    /**
     * @Component가 없는 이유
     * AnnotationConfigApplicationContext에 파라미터로 PrototypeBean을 지정해주면
     * 이 클래스가 컴포넌트스캔 대상 처럼 동작하기 때문에 바로 스프링 빈으로 등록한다.
     * 컴포넌트 스캔처럼 등록됨 그래서 없어도 됨
     */
    static class PrototypeBean {
        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy // 프로토타입 빈은 스프링 컨테이너가 종료될 때 @PreDestroy같은 종료 메서드가 전혀 실행되지 않는다.
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
