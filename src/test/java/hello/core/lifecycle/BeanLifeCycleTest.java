package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
//        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        // bean 조회
        NetworkClient client = ac.getBean(NetworkClient.class);
        // 중요 ac.close()호출
        // ApplicationContext를 닫아야 한다. 근데 기본 ApplicationContext에서는 close를 지원하지 않는다.
        // 그래서 ac 타입을 AnnotationConfigApplicationContext로 바꾸거나
        // 인터페이스로 ConfigurableApplicationContext 로 바꿔준다.
        // 직접 ApplicationContext를 가져다 쓸 때 close할 일이 별로 없다.
        // close는 기본 ApplicationContext에서 제공해주지 않는다.
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {
        // 빈생성
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient(); // 객체 생성
            networkClient.setUrl("http://hello-spring.dev"); // 객체 생성 후 설정이 들어올 수 있다.
            return networkClient;
            // 스프링 빈이 생성이되고 호출이 된 결과물이 스프링 빈으로 등록이 된다.
            // 빈 이름은 networkClient()메서드가 된다.
        }
    }
}
