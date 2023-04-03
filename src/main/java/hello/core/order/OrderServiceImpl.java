package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    /**
     * MemberRepository에서 회원 찾기
     * DiscountPolicy는 고정 할인 정책
     */
    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

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
}
