package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class ConfigurationSingletonTest {

    @Test
    // @Configuration이 잘 되는지 보는거다.
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        // 빈 조회
        // Impl로 꺼내는 이유는 테스트용도로 따로 만든 메서드가 Impl에 있어서
        // 원래는 구체타입을 꺼내면 안좋다.
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        // 진짜 MemoryMemberRepository를 꺼내보자
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        // memberRepository 꺼내보자
        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberService에서 참조하는 레파지토리 memberRepository = " + memberRepository1);
        System.out.println("orderService에서 참조하는 레파지토리 memberRepository = " + memberRepository2);
        /**
         * 결과 같은 객체가 나온다.
         * memberService에서 참조하는 레파지토리 memberRepository = hello.core.member.MemoryMemberRepository@bae47a0
         * orderService에서 참조하는 레파지토리 memberRepository = hello.core.member.MemoryMemberRepository@bae47a0
         */
        System.out.println("memberRepository = " + memberRepository);
        /**
         * 결과
         * memberRepository = hello.core.member.MemoryMemberRepository@bae47a0
         * 위 3개 다 같은 스프링 빈이다.
         */

        // 검증
        // memberService.getMemberRepository()[MemoryMemberRepository]는 memberRepository랑 같다.
        assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }
}
