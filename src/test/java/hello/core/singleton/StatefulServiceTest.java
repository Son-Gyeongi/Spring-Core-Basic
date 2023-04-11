package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        // bean 조회
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        /**
         * 웹에서 요청이 오면 Thread가 할당된다.
         */
        // ThreadA : 고객 요청에 A사용자가 10000원 주문
//        statefulService1.order("userA", 10000);
        int userAPrice = statefulService1.order("userA", 10000);
        // ThreadB : B사용자가 20000원 주문
        int userBPrice = statefulService2.order("userB", 20000);

        // ThreadA : 사용자A가 주문 금액 조회
//        int price = statefulService1.getPrice();
//        System.out.println("price = " + price);
        /** 중요! 싱글톤 방식의 주의점
         * 결과 20000원 (원하는 결과 10000원)
         * 같은 객체를 사용해서 중간에 2만원으로 바뀐 값이 나온다.
         */

        // userAPrice, userBPrice 지역변수는 공유되지 않는다.
        System.out.println("userAPrice = " + userAPrice);

        // 검증
//        assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }

    static class TestConfig {

        // StatefulService 전용 컨피그 만들기
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}