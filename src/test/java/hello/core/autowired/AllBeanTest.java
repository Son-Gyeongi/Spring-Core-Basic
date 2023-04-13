package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class AllBeanTest {

    @Test
    void findAllBean() {
        // DiscountService를 스프링 빈으로 등록
        // DiscountPolicy를 가져오기 위해서 AutoAppConfig도 스프링 빈으로 등록
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        // 비즈니스 로직을 만들어보자. TDD로 할거다
        DiscountService discountService = ac.getBean(DiscountService.class);
        // Member 만들어주자
        Member member = new Member(1L, "userA", Grade.VIP);
        // discountService에서 discount가 얼마나 되는지 보는 서비스
        // 내가 VIP이고 10000이면 discount가 얼마나 될까
        int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");

        // 검증
        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(discountPrice).isEqualTo(1000);

        int rateDiscountPrice = discountService.discount(member, 20000, "rateDiscountPolicy");
        // 검증
        assertThat(rateDiscountPrice).isEqualTo(2000);
    }

    // 새로운 서비스를 만들어서 테스트하자
    static class DiscountService {
        // Map으로 DiscountPolicy를 다 받는다.
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;

        @Autowired // 자동 의존 관계 주입이 될 때 AutoAppConfig에서 ComponentScan 한다.
        // 그러면 RateDiscountPolicy, FixDiscountPolicy 둘 다 스프링 빈에 등록된다.
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
            // 의존 관계 주입이 됐는지 안 됐는지 보자
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
            /**
             * 결과
             * policyMap = {fixDiscountPolicy=hello.core.discount.FixDiscountPolicy@56c698e3, rateDiscountPolicy=hello.core.discount.RateDiscountPolicy@47a86fbb}
             * Map은 키,밸류로 키 = 값(스프링빈)이 나온다
             *
             * policies = [hello.core.discount.FixDiscountPolicy@56c698e3, hello.core.discount.RateDiscountPolicy@47a86fbb]
             * List는 값(스프링 빈 인스턴스)만 나온다.
             */
        }

        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            return discountPolicy.discount(member, price);
        }
    }
}
