package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    // 수정자 주입 테스트
    // 순수한 자바로 코드 짜보자
    @Test
    void createOrder() {
        // OrderServiceImpl 잘 만들었는지 테스트
//        OrderServiceImpl orderService = new OrderServiceImpl();
//        orderService.createOrder(1L, "itemA", 10000);
        /**
         * 수정자 주입 결과
         * java.lang.NullPointerException
         * 의존관계 설정을 해주지 않아서
         */

        /**
         * 생성자 주입
         * 개발자가 new OrderServiceImpl() 를 만드는 순간 컴파일 오류로
         * memberRepository와 discountPolicy가 필요하다는 걸 안다.
         * 생성자로 해야 빨리 넣을 수 있다.
         *
         * 스프링 없이 순수한 자바 코드로
         * 테스트 상에서 필요한 구현체들을 내가 직접 뭐 쓸지 정할 수 있다.
         */
        // 테스트를 위해서 회원 추가
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "name", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());
        Order order = orderService.createOrder(1L, "itemA", 10000);

        // 검증
        assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

}