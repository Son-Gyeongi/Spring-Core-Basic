package hello.core.xml;

import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class XmlAppContext {

    @Test
    void xmlAppContext() {
        ApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
        // application 빈 조회
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);

        /** 결과
         * 아래 4개가 싱글톤 빈으로 등록이 되었다고 나온다.
         * Creating shared instance of singleton bean 'memberService'
         * Creating shared instance of singleton bean 'memberRepository'
         * Creating shared instance of singleton bean 'orderService'
         * Creating shared instance of singleton bean 'discountPolicy'
         */
    }
}
