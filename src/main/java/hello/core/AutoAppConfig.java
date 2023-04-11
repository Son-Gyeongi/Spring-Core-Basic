package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration // 설정정보라서 붙여준다.
/**
 * @ComponentScan
 * 스프링 빈을 모두 긁어와서 자동으로 스프링 빈으로 끌어올려야한다.
 * @Component가 붙은 클래스를 찾아서 자동으로 스프링 빈 등록
 */
@ComponentScan(
//        basePackages = "hello.core.member", // 탐색할 패키지의 시작 위치 지정
//        basePackageClasses = AutoAppConfig.class, // AutoAppConfig클래스 파일의 패키지 참고(package hello.core;)여기서부터 찾는다.
        // basePackages, basePackageClasses 지정하지 않으면 default값은 뭘까?
        // @ComponentScan이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다.
        // AutoAppConfig클래스 패키지 참고(package hello.core;) 여기서부터 시작해서 하위패키지 다 뒤진다.

        // @ComponentScan으로 Component를 뒤져서 스프링 빈으로 자동으로 등록 하는데 그 중에서 뺼거를 지정
        // @Configuration이 붙은 AppConfig(설정정보 수동으로 등록) 클래스파일과 충돌되지 않게 하기 위해서
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

    /**
     * MemoryMemberRepository랑 이름이 같은 빈 한개 생성
     * 수동 빈 등록이 자동 빈 등록보다 우선권을 가진다.
     * 수동 빈이 자동 빈을 오버라이딩 해버린다.
     * AutoAppConfigTest 실행 결과
     * Overriding bean definition for bean 'memoryMemberRepository' with a different definition: replacing [Generic bean: class [hello.core.member.MemoryMemberRepository]
     */
//    @Bean(name = "memoryMemberRepository")
//    // MemoryMemberRepository가 @Component 붙어있어서 클래스명의 맨 앞글자가 소문자로 바뀐다. memoryMemberRepository가
//    MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
}
