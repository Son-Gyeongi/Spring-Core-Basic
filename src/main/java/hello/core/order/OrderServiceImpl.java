package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

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

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
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
