package hello.core;

import hello.core.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @SpringBootTest
 * 스프링이 자동으로 스프링 컨테이너를 띄워서 테스트를 통합해서 실행해준다.
 */
@SpringBootTest
class CoreApplicationTests {

	// 테스트에서는 의존관계 주입에서 @Autowired를 바로 필드에 주입해도 된다.
//	@Autowired
//	MemberService memberService;

	@Test
	void contextLoads() {
	}

}
