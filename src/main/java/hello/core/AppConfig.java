package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AppConfig : 애플리케이션 전체를 설정하고 구성, 애플리케이션 설정 정보
 * AppConfig는 애플리케이션의 실제 동작에 필요한 구현 객체를 생성
 * 객체의 생성과 연결은 AppConfig가 담당
 *
 * AppConfig 리팩터링
 * 메서드 명만 봐도 어떤 역할인지 알 수 있다.
 * 설계에 대한 그림이 AppConfig 구성정보에 그대로 드러난다.
 * 역할들이 나오고 역할들에 대한 구현이 어떻게 되는지 한눈에 들어온다.
 * 역할을 세우고 그 안에 구현이 들어가게 설계하는 게 좋다.
 * 그리고 중복도 제거가 되었다.
 *
 * 지금까지 순수한 자바 코드만으로 DI를 적용했다.
 * 이제 스프링을 사용해보자
 * 설정정보에 @Configuration 애노테이션 작성, 그리고 각 메서드에 @Bean을 적어준다.
 * 그러면 각 메서드들이 스프링 컨테이너에 등록이 된다.
 */
@Configuration
public class AppConfig {

    /**
     * MemberService 역할
     */
    @Bean
    public MemberService memberService() {
        /**
         * 생성자를 통해서 new 인스턴스 객체가 들어간다. - 생성자 주입
         */
        return new MemberServiceImpl(memberRepository());
    }

    /**
     * MemberRepository 인터페이스를 관할해주는 역할
     */
    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    /**
     * OrderService 역할
     */
    @Bean
    public OrderService orderService() {
        /**
         * OrderServiceImpl은 사용하는 필드가 2개이다.
         * MemberRepository
         * DiscountPolicy
         */
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    /**
     * DiscountPolicy 역할
     */
    @Bean
    public DiscountPolicy discountPolicy() {
        /**
         * AppConfig는 배우만 바꾼다고 생각하면 된다.
         * 할인에 대한 구체 객체를 바꾼다.
         */
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
