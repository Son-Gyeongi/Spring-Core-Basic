package beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// junit5부터는 클래스,메서드에 public 사용 안해도 된다.
class ApplicationContextInfoTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    /**
     * ApplicationContext(스프링 컨테이너)에 등록된 모든 빈 출력
     */
    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean() {
        // 빈 정의된 이름 등록
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();

        // iter 입력하고 탭
        // 리스트나 배열이 있으면 Iterate라고 하는 for문이 자동으로 완성된다.
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName); // 타입을 몰라서, 타입을 지정하지 않아서 Object로 꺼내진다.
            System.out.println("name (key) = " + beanDefinitionName + " object (value) = " + bean);
        }
    }

    @Test
    @DisplayName("애플리케이션 빈 출력하기")
    void findApplicationBean() {
        // 빈 정의된 이름 등록
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();

        // iter 입력하고 탭
        // 리스트나 배열이 있으면 Iterate라고 하는 for문이 자동으로 완성된다.
        for (String beanDefinitionName : beanDefinitionNames) {
            // getBeanDefinition : 빈에 대한 정보들, 빈(빈 하나하나)에 대한 메타데이터 정보
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            /**
             * role이 3가지가 있다.
             * Role ROLE_APPLICATION : 직접 등록한 애플리케이션 빈
             * Role ROLE_INFRASTRUCTURE : 스프링이 내부에서 사용하는 빈
             * 그 중에 ROLE_APPLICATION
             * 이거는 스프링이 내부에서 무언가를 하기 위해서 등록한 빈이 아니라
             * 내가 애플리케이션을 주로 개발하기 위해서 등록한 빈이다. 또는 외부 라이브러리 이런것들이다.
             */
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                Object bean = ac.getBean(beanDefinitionName); // 타입을 몰라서, 타입을 지정하지 않아서 Object로 꺼내진다.
                System.out.println("name (key) = " + beanDefinitionName + " object (value) = " + bean);
            }
        }
    }
}
