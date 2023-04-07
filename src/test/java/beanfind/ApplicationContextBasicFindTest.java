package beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ApplicationContextBasicFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
//        System.out.println("memberService = " + memberService);
//        System.out.println("memberService.getClass() = " + memberService.getClass());
        // isInstanceOf - memberService의 객체는 어떤거야?
        // memberService가 memberServiceImpl의 인스턴스이면 성공
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("이름 없이 타입으로만 조회")
    void findBeanByType() {
        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    // getBean에서 인터페이스가 아닌 구체 클래스 타입으로 조회
    // AppConfig에서 MemberService를 보면 MemberServiceImpl이 인스턴스로 있다. 그래서 가능
    // 그런데 구체적으로 적는 방법은 좋지 않다. 역할과 구현을 구분해야하고 역할에 의존해야 하는데
    // 이러한 경우 구현에 의존하게 된다.
    @Test
    @DisplayName("구체 타입으로 조회") // 구체 타입으로 조회 시 유연성 떨어짐
    void findBeanByName2() {
        MemberService memberService = ac.getBean("memberService", MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    // 항상 테스트는 실패 테스트를 만들어야 한다.
    @Test
    @DisplayName("빈 이름으로 조회X")
    void findBeanByNameX() {
//        ac.getBean("xxxxx", MemberService.class);
//        MemberService xxxxx = ac.getBean("xxxxx", MemberService.class);
        // 에러 NoSuchBeanDefinitionException: No bean named 'xxxxx' available

        /**
         * 테스트 코드 검증 해야하는데 junit5와서 조금 지저분해졌다.
         * org.junit.jupiter.api.Assertions.assertThrows
         * assertThrows 자바8의 람다 기능을 쓴다.
         * 무조건 예외가 터져야 테스트 성공
         * () -> ac.getBean("xxxxx", MemberService.class) 이 로직이 실행됐을 때
         * 왼쪽에 NoSuchBeanDefinitionException.class 예외가 터져야 한다.
         */
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("xxxxx", MemberService.class));
    }
}
