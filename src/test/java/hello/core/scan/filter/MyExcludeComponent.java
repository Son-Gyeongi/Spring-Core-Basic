package hello.core.scan.filter;

import java.lang.annotation.*;

// 애노테이션 만들어보자
// MyExcludeComponent가 붙은건 ComponentScan에 제외 할거야
@Target(ElementType.TYPE) // TYPE은 클래스레벨에 붙는거다.
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {
}
