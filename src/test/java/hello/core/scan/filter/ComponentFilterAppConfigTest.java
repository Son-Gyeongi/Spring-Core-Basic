package hello.core.scan.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.context.annotation.ComponentScan.*;

public class ComponentFilterAppConfigTest {

    @Test
    void filterScan() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
        BeanA beanA = ac.getBean("beanA", BeanA.class);
        assertThat(beanA).isNotNull();

        // BeanB 조회
        // 없는 빈이라서 조회하는 순간 Exception(예외) 터진다. MyExcludeComponent로 ComponentScan 대상에서 빠진다.
        // NoSuchBeanDefinitionException: No bean named 'beanB' available
//        ac.getBean("beanB", BeanB.class);
        assertThrows( // org.junit.jupiter.api.Assertions
                NoSuchBeanDefinitionException.class,
                () -> ac.getBean("beanB", BeanB.class)
        );
    }

    // 나만의 Component를 스캔할 수 있는 기능이 만들어진다.
    @Configuration
    @ComponentScan(
//            includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
            includeFilters = @Filter(classes = MyIncludeComponent.class),
//            excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class)
            excludeFilters = @Filter(classes = MyExcludeComponent.class)
    )
    // includeFilters 포함하는 필터, excludeFilters 제외하는 필터
    // FilterType.ANNOTATION 어노테이션과 관련된 필터 만든다. 그리고 기본값이라서 생략해도 동작 잘된다.
    static class ComponentFilterAppConfig {
    }
}
