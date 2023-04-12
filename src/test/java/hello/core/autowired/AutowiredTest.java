package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption() {
        // 값이 안들어오는 거 테스트
        // TestBean.class 넣어주면 TestBean.class가 스프링 빈으로 등록이 된다. ComponentScan 하는거처럼
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
        // 실행하면
        // 스프링 컨테이너 올라올 때 @Autowired가 다 호출이 된다. TestBean.class에서 3가지 방법 다 호출된다.
        /**
         * 결과
         * noBean2 = null
         * noBean3 = Optional.empty
         *
         * 1번은 자체가 호출이 안됐다.
         * @Autowired(required = false) 이렇게 하고 의존관계가 없으면 메서드가 아예 호출이 안된다.
         *
         * 2번 @Nullable 넣어주면 호출은 된다. 대신에 null이 들어온다.
         *
         * 3번 자바8에서 제공하는 Optional문법 주입할 대상이 없으면, 스프링빈이 없으면 Optional.empty를 넣어준다.
         * 값이 있으면 Optional안에 값이 감싸져 있을거다.
         *
         * 참고 : @Nullable, Optional은 스프링 전반에 걸쳐서 지원된다.
         * 예를 들어 생성자 자동 주입에서 특정 필드에만 사용해도 된다.
         */
    }

    // 테스트 전 임의의 스프링 빈을 만들자
    // 임의의 테스트 클래스 만들자
    static class TestBean {
        // 자동 주입 대상을 옵션으로 처리하는 방법 3가지
        // 1. @Autowired(required=false) 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 안됨
        // (required = true) // Member가 스프링 빈으로 등록이 안되서 예외가 터진다.
        @Autowired(required = false)
        public void setNoBean1(Member noBean1) {
            // Member는 스프링이 관리하는 빈이 아니다.
            // 그래서 스프링 컨테이너에 관리되는 게 없다.
            System.out.println("noBean1 = " + noBean1);
        }

        // 2. org.springframework.lang.@Nullable 자동 주입할 대상이 없으면 null이 입력된다.
        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {
            System.out.println("noBean2 = " + noBean2);
        }

        // 3. 자바8의 Optional<> 자동 주입할 대상이 없으면 Optional.empty가 입력된다.
        @Autowired
        public void setNoBean3(Optional<Member> noBean3) {
            System.out.println("noBean3 = " + noBean3);
        }
    }
}
