package hello.core.beandefinition;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * 스프링은 BeanDefinition으로 스프링 빈 설정 메타 정보를 추상화한다.
 * 스프링 빈을 만들때 2가지 방법이 있는데
 * 하나는 직접적으로 스프링 빈을 등록하는 방법
 * 두번째는 factoryBean이라는 걸 통해서 등록을 하는 방법
 * 우리가 일반적으로 자바Config를 쓰는 방법은 factoryBean을 통해서 등록하는 방식
 */
public class BeanDefinitionTest {

    // 왼쪽 타입에 AnnotationConfigApplicationContext 나 GenericXmlApplicationContext를
    // ApplicationContext 타입으로 지정하면 getBeanDefinition 사용 못한다. 왜냐하면 getBeanDefinition 불러쓰려면 다른 인터페이스가 있어야한다.
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
//    GenericXmlApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");

    @Test
    @DisplayName("빈 설정 메타정보 확인") // BeanDefinition 확인
    void findApplicationBean() {
        // AnnotationConfigApplicationContext에서 getBeanDefinitionNames 이름을 꺼낸다.
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        // 반복문으로 돌리면서 ApplicationContext에서 getBeanDefinition()정보를 얻을 수 있다.
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println("beanDefinitionName = " + beanDefinitionName +
                        " beanDefinition = " + beanDefinition);
            }
        }

        /** AppConfig.class 결과
         * beanDefinitionName = appConfig beanDefinition = Generic bean: class [hello.core.AppConfig$$EnhancerBySpringCGLIB$$1f202319]; scope=singleton; abstract=false; lazyInit=null; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null
         * beanDefinitionName = memberService beanDefinition = Root bean: class [null]; scope=; abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=appConfig; factoryMethodName=memberService; initMethodName=null; destroyMethodName=(inferred); defined in hello.core.AppConfig
         * beanDefinitionName = memberRepository beanDefinition = Root bean: class [null]; scope=; abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=appConfig; factoryMethodName=memberRepository; initMethodName=null; destroyMethodName=(inferred); defined in hello.core.AppConfig
         * beanDefinitionName = orderService beanDefinition = Root bean: class [null]; scope=; abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=appConfig; factoryMethodName=orderService; initMethodName=null; destroyMethodName=(inferred); defined in hello.core.AppConfig
         * beanDefinitionName = discountPolicy beanDefinition = Root bean: class [null]; scope=; abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=appConfig; factoryMethodName=discountPolicy; initMethodName=null; destroyMethodName=(inferred); defined in hello.core.AppConfig
         *
         * bean: class [null] 클래스가 널이다. 이러한 정보가 직접적으로 드러나는것이 아니라 factorybeanName이라는 애에서 factoryMethodName이 생성이 된다. factoryMethod방식
         * scope 싱글톤 등
         * lazyInit 보통 스프링 빈은 스프링 뜰 때 컨테이너에 등록이 된다. lazyInit은 나중에 실제 사용하는 시점에 스프링 빈을 초기화하는 거다.
         * autowireMode
         * autowireCandidate autowire의 후보가 돼
         * factoryBeanName(appConfig)에 있는 factoryMethodName(memberService)를 호출해서 실제 빈을 생성할 수 있구나
         * initMethodName 초기화메서드
         * destroyMethodName
         * 실제 정의는 defined in 패키지이름 에 되어있다는 것이다.
         */

        /** appConfig.xml 결과
         * beanDefinitionName = memberService beanDefinition = Generic bean: class [hello.core.member.MemberServiceImpl]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in class path resource [appConfig.xml]
         * beanDefinitionName = memberRepository beanDefinition = Generic bean: class [hello.core.member.MemoryMemberRepository]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in class path resource [appConfig.xml]
         * beanDefinitionName = orderService beanDefinition = Generic bean: class [hello.core.order.OrderServiceImpl]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in class path resource [appConfig.xml]
         * beanDefinitionName = discountPolicy beanDefinition = Generic bean: class [hello.core.discount.RateDiscountPolicy]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in class path resource [appConfig.xml]
         *
         * Generic bean: class [hello.core.member.MemberServiceImpl] 빈에 대한 정보가 명학하게 들어가있다.
         * defined in class path resource [appConfig.xml] 이 appConfig.xml이다.
         * factoryBeanName이랑 factoryMethodName 다 빠져있다.
         */

        /**
         * 예전에는 xml로 설정하면서 빈에 대한 클래스가 밖에 드러났다. Generic bean: class [hello.core.member.MemberServiceImpl]
         * 그럼데 자바Config로 바뀌면서 어떻게 되었냐면
         *
         * 스프링에 빈을 등록하는 방법은 여러가지가 있지만
         * 크게 2가지가 있다
         * 1. 직접 스프링 빈을 컨테이너에 등록하는 방법 - appConfig.xml 빈을 그대로 등록
         * 2. 우회하는 방법, factoryMethod를 쓰는 방법이 있다.
         * 자바코드(AppConfig.class)를 통해서 쓰는 방법은 factoryMethod를 통해서 등록하는 방법이다.
         * factoryMethod방식 외부에서 메서드를 호출 해서 생성이 되는 방식이다.
         */
    }
}
