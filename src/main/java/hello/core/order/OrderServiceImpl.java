package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // @ComponentScan 대상이 되면서 스프링 빈으로 등록
public class OrderServiceImpl implements OrderService {

    /**
     * final은 무조건 값이 할당 되어야 한다. - 기본으로 값을 주든, 생성자를 통해서 주든
     * MemberRepository에서 회원 찾기
     * DiscountPolicy는 고정 할인 정책 -> RateDiscountPolicy
     * => 객체지향의 5가지 원칙 SOLID 중 OCP, DIP 위반
     * => DIP 위반 : 인터페이스(추상화)와 구현클래스(구체화)를 다 의존한다.
     * => OCP 위반 : 구현클래스를 의존하면서 구현클래스가 바뀌면 OrderServiceImpl클라이언트도 변경해줘야 한다.
     *     private final MemberRepository memberRepository = new MemoryMemberRepository();
     * //    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
     * //    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
     *     private DiscountPolicy discountPolicy; // 구체에 의존하지 않고 추상화에 의존 -> 구현체가 없어서 실행 시 NullPointerException이 일어난다.
     */

    private MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;

    /**
     * 수정자 주입(setter주입) - final은 빼야함
     * private MemberRepository memberRepository;
     * private DiscountPolicy discountPolicy;
     *
     * @Component가 OrderServiceImpl을 스프링 컨테이너에 등록이 된다.
     * 스프링 컨테이너에는 2가지 라이프 사이클이 있다.
     * 1. 스프링 빈을 등록
     * 2. 연관관계 자동으로 주입 (@Autowired가 걸린 애들을 자동으로 주입)
     * 수정자 주입은 라이프 사이클에서 2번째 단계에서 일어난다.
     */
    @Autowired // @Autowired 뺴면 당연히 의존관계 자동 주입 되지 않는다.
    public void setMemberRepository(MemberRepository memberRepository) {
        System.out.println("setter주입 memberRepository = " + memberRepository);
        this.memberRepository = memberRepository;
    }
    @Autowired // @Autowired 뺴면 당연히 의존관계 자동 주입 되지 않는다.
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        System.out.println("setter주입 discountPolicy = " + discountPolicy);
        this.discountPolicy = discountPolicy;
    }

    /**
     * @Component 작성 후 자동 의존 관계 주입
     * ac.getBean(MemberRepository.class) 이런식으로 동작한다고 생각하면 된다.
     * 생성자 주입은 스프링 라이프 사이클 빈은 등록할 때 같이 자동 의존 관계가 주입이 된다.
     * 처음 생성할 때 new OrderServiceImpl(memberRepository, discountPolicy)를 호출한다.
     * 이때 @Autowired 확인하고 memberRepository, discountPolicy를 스프링 빈에서 찾아와서 OrderServiceImpl 생성한다.
     */
//    @Autowired 생략가능, 스프링 빈에 생성자가 딱 하나 있으면 자동으로 Autowired가 적용이 된다.
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        System.out.println("1. OrderServiceImpl.OrderServiceImpl");
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        /**
         * 주문 생성 요청이 오면 회원 정보를 먼저 조회를 하고
         * 그 다음 할인정책에다가 회원을 넘긴다.
         */
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        /**
         * 설계가 잘 되었다. OrderService는 할인에 대해서 알지 못하고
         * 할인은 discountPolicy가 알아서 하고 결과만 던져준다. OrderService는 모른다.
         * 이러한 부분이 단일 책임 원칙(Single Responsibility Principle)을 잘 지킨거다.
         * 할인에 대한 변경이 필요하면 할인쪽만 고치면 된다.
         */

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
