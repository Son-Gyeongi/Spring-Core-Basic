package hello.core.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

// 애노테이션 만들어보자
// @Qualifier랑 똑같이 붙일 수 있게 만들자
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy {
    // MainDiscountPolicy 애노테이션 쓰면 위에 애노테이션 기능이 다 동작한다. 스프링 컨테이너 안에서
}
