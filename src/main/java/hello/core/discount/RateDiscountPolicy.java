package hello.core.discount;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component // @ComponentScan 대상이 되면서 스프링 빈으로 등록
//@Qualifier("mainDiscountPolicy") // 추가구분자, 특별한 이름 부여, 문자로 작성되서 컴파일 타임에 오류를 잡을 수 없다.
//@Primary // 같은 타입의 여러개의 빈이 있을 때 무조건 먼저 선택된다.
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy {

    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }
}
