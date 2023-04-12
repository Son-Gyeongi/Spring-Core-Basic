package hello.core.order;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    // 수정자 주입 테스트
    // 순수한 자바로 코드 짜보자
    @Test
    void createOrder() {
        // OrderServiceImpl 잘 만들었는지 테스트
        OrderServiceImpl orderService = new OrderServiceImpl();
        orderService.createOrder(1L, "itemA", 10000);
        /**
         * 수정자 주입 결과
         * java.lang.NullPointerException
         * 의존관계 설정을 해주지 않아서
         */
    }

}