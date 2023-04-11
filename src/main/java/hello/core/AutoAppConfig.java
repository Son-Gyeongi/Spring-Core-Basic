package hello.core;

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
        // @ComponentScan으로 Component를 뒤져서 스프링 빈으로 자동으로 등록 하는데 그 중에서 뺼거를 지정
        // @Configuration이 붙은 AppConfig(설정정보 수동으로 등록) 클래스파일과 충돌되지 않게 하기 위해서
        excludeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
}
