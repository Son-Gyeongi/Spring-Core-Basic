package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        // 빈 찾기
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        // 빈 찾고 count 더하기
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        // 다른 클라이언트가 두번째 호출한 경우 - 두번째 프로토타입
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    // prototypeBean을 의존관계 주입으로 가져와서 사용
    @Test
    void singletonClientUsePrototype() {
        // ClientBean.class, PrototypeBean.class 두 개 다 컴포넌트스캔한다.
        // 둘 다 자동 빈 등록으로 등록이 된다.
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(2);
    }

    // 싱글톤빈 만들자
    // prototypeBean을 의존관계 주입으로 가져와서 사용
    @Scope("singleton") // 기본값이라서 안해줘도 되지만 테스트를 위해서 적음
    static class ClientBean {
        // 싱글톤빈 안에서 프로토타입을 생성하면 새로운 프로토타입 빈이 생성 안된다.
//        private final PrototypeBean prototypeBean; // 생성 시점에 주입
//        // 의존관계 주입
//        @Autowired // 생략 가능
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }
        //=> 해결1 ClientBean이 그냥 @Autowired로 ApplicationContext를 받는다.
        @Autowired
        private ApplicationContext ac;

        public int logic() {
            // 해결1 logic 호출할 때마다 컨테이너에서 받으면 된다.
            PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
            // return prototypeBean.getCount(); 한줄로 합치키 단축키 ctrl+alt+N
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        // count 증가
        public void addCount() {
            count++;
        }

        // 조회
        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            // this : 현재 나를 보여주고 나의 참조값을 알 수 있다.
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() { // 애는 호출이 안된다.
            System.out.println("PrototypeBean.destroy");
        }
    }
}
