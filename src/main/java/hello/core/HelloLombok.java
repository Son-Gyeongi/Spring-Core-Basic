package hello.core;

import lombok.*;

/**
 * @Getter, @Setter
 * getter, setter 메서드를 자동으로 annotations processing으로 만들어준다.
 * 원래는 아래와 같이 직접 작성해야한다.
 */
@Getter
@Setter
@ToString // 클래스 안 멤버 변수로 toString 만들어준다.
public class HelloLombok {

    private String name;
    private int age;

    //@Getter, @Setter
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok();
        helloLombok.setName("test");

        System.out.println("helloLombok = " + helloLombok);
    }
}
