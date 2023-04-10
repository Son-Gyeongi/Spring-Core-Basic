package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    // 우리가 만들었던 순수한 DI 컨테이너가 가진 문제점을 볼 거다. 그리고 나중에 스프링이랑 비교해보자
    void pureContainer() {
        AppConfig appConfig = new AppConfig();
        // 1. 조회 : 호출할 때마다 객체를 생성하는지 조회
        MemberService memberService1 = appConfig.memberService();

        // 2. 조회 : 호출할 때마다 객체를 생성하는지 조회
        MemberService memberService2 = appConfig.memberService();

        // 참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);
        /**
         * appConfig한테 memberService를 달라고할 때마다 다른 게 생성
         * 이렇게 되면 JVM메모리에 객체가 계속 생성이되서 올라간다.
         * memberService1 = hello.core.member.MemberServiceImpl@ee86bcb
         * memberService2 = hello.core.member.MemberServiceImpl@a87f8ec
         *
         * 스프링은 주로 웹 애플리케이션을 만든다.
         * 웹 애플리케이션 특징은 고객의 요청이 많다.
         * -> 호출할 때마다 무조건 새로운 객체 새롭게 생성
         * -> memberService 생성하면서 memberRepository도 같이 생성해야하니
         * 객체가 총 4개 생성되었다.
         * => 해결방안은 해당 객체가 딱 1개만 생성되고, 공유하도록 설계하면 된다. : 싱글톤 패턴
         */

        //검증 - 위에 출력문을 통해서 눈으로 확인했지만 테스트는 자동화되게 만들어야한다.
        // memberService1 != memberService2
        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
    }
}
