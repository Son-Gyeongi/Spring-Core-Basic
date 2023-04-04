package hello.core;

import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

/**
 * AppConfig : 애플리케이션 전체를 설정하고 구성
 * AppConfig는 애플리케이션의 실제 동작에 필요한 구현 객체를 생성
 * 객체의 생성과 연결은 AppConfig가 담당
 */
public class AppConfig {

    public MemberService memberService() {
        /**
         * 생성자를 통해서 new 인스턴스 객체가 들어간다. - 생성자 주입
         */
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService() {
        /**
         * OrderServiceImpl은 사용하는 필드가 2개이다.
         * MemberRepository
         * DiscountPolicy
         */
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }
}
