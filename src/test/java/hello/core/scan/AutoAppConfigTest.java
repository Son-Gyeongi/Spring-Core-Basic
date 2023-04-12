package hello.core.scan;

import hello.core.AutoAppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class AutoAppConfigTest {

    @Test
    void basicScan() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        // 잘 조회되는지 보자
        MemberService memberService = ac.getBean(MemberService.class);
        // 검증
        assertThat(memberService).isInstanceOf(MemberService.class);

        // @Autowired 필드 주입으로 했을 때 memberRepository값이 있는지
        // OrderServiceImpl에서 getMemberRepository메서드로 꺼내보자
        OrderServiceImpl bean = ac.getBean(OrderServiceImpl.class);
        MemberRepository memberRepository = bean.getMemberRepository();
        System.out.println("memberRepository = " + memberRepository);
    }
}
