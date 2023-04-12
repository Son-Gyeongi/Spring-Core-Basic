package hello.core.order;

import hello.core.AppConfig;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {
    /**
     * 단위 테스트 중요하다.
     * 여기서 말하는 단위 테스트는 스프링이나 컨테이너의 도움없이
     * 순수하게 자바 코드로 테스트 하는 걸 말한다.
     */

//    MemberService memberService = new MemberServiceImpl(memberRepository);
//    OrderService orderService = new OrderServiceImpl();

    MemberService memberService;
    OrderService orderService;
    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    @Test
    void createOrder() {
        // given
        /**
         * 타입을 primitive type 'long'을 써도
         * 되지만 null을 넣을 수 없다. 이후 db에 null이 들어갈 수 있고
         * 객체 생성 단계에서 null이 들어갈 수 있어서 Long으로 썼다.
         */
        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        // when
        Order order = orderService.createOrder(memberId, "itemA", 10000);

        //then
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

    // 필드 주입 테스트
    @Test
    void fieldInjectionTest() {
        OrderServiceImpl orderService = new OrderServiceImpl(); // 객체 생성
        // memberRepository를 변경할 수 있는 방법이 없다.
        // 스프링에서 돌리는게 아니라 순수한 자바코드로 돌린다.
//        orderService.createOrder(1L, "memberA", 10000);
        // => 결과
        // NullPointerException 예외가 뜬다.

        // => 해결방법은 OrderServiceImpl에 setter를 만들어준다. new로 생성하는 건 @Autowired가 안된다.
        orderService.setMemberRepository(new MemoryMemberRepository());
        orderService.setDiscountPolicy(new FixDiscountPolicy());
        // => 이 다음에 호출하면 MemoryMemberRepository, FixDiscountPolicy 시용한다.
        orderService.createOrder(1L, "memberA", 10000);
    }
}
