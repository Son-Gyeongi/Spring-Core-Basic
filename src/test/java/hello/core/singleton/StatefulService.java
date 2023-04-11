package hello.core.singleton;

public class StatefulService {
    /**
     * 중요! 싱글톤 방식의 주의점
     * 진짜 공유 필드는 조심해야 한다! 스프링 빈은 항상 무상태(stateless)로 설계하자.
     */

//    private int price; // 상태를 유지하는 필드

    // 주문할 때 가격 저장
//    public void order(String name, int price) {
//        System.out.println("name = " + name + " price = " + price);
//        this.price = price; // 여기가 문제!
//    }

    // price 꺼내기
//    public int getPrice() {
//        return price;
//    }

    // 해결 : 필드 대신에 자바에서 공유되지 않는 지역변수, 파라미터, ThreadLocal 등을 사용해야 한다.
    public int order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        return price;
    }
}
