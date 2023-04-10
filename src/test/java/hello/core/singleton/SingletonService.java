package hello.core.singleton;

// 테스트 케이스는 아니고 순수한 애플리케이션에 영향을 안주고 싱글톤만 간단하게 만들어서 테스트
/** 싱글톤 패턴 구현
 * 아래와 같이 작성하면 완벽한 싱글톤이 작성 되었다.
 * 자바가 뜨면서 static영역에 있는 SingletonService를 초기화하면서 new로 객체를 생성해서 가지고 있고
 * instance의 참조를 꺼낼 수 있는 방법은 getInstance메서드 밖에 없다.
 * SingletonService를 생성할 수 있는 건 아무것도 없다.
 */
public class SingletonService {

    /**
     * 자기 자신을 내부에 private으로 하나를 static으로 가지고 있다.
     * 이렇게 하면 클래스 레벨에 올라가기 때문에 하나만 존재하게 된다.
     * static영역에 하나만 만들어져서 올라간다.
     * 이렇게 하면 자바가 뜰때 SingletonService에 static 영역에 오른쪽에 new가 있네하고
     * 내부적으로 실행해서 객체(자기자신)를 생성에서 instance참조에 넣어놓는다.
     * 그러면 자기자신 객체(인스턴스)를 딱 하나 생성해서 instance안에 들어가있다.
     */
    // 1. static 영역에 객체를 딱 1개만 생성해둔다.
    private static final SingletonService instance = new SingletonService();

    // SingletonService 조회
    // 2. public으로 열어서 객체 인스턴스가 필요하면 이 static메서드를 통해서만 조회하도록 허용한다.
    public static SingletonService getInstance() {
        return instance;
    }

    /**
     * 중요
     * private으로 내부에 SingletonService를 감추려고 해도 아래와 같이(내부 클래스에서는 private 사용가능하니 외부 클래스에서)
     * SingletonService 객체를 생성하면 아무 소용이 없다.
     * //    public static void main(String[] args) {
     * //        SingletonService singletonService1 = new SingletonService();
     * //        SingletonService singletonService2 = new SingletonService();
     * //    }
     * -> 그래서 private 생성자를 쓴다.
     */
    // 3. 생성자를 private으로 선언해서 외부에서 new 키워드를 사용한 객체 생성을 못하게 막는다.
    private SingletonService() {}

    // 그 다음에 로직이 하나 있어야 한다.
    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}

/**
 * SingletonService로 싱글톤 패턴 구현을 해보았다.
 * 그러면 이제 AppConfig에 있는 모두를 싱글톤 패턴에 맞춰서 넣으면 된다.
 * getInstance()로 반환하게 하면된다. 그러면 싱글톤이 다 적용이 된다.
 * 그런데! 그렇게 할 필요가 없다.
 * 스프링 컨테이너를 쓰면 스프링 컨테이너가 기본적으로 객체를 다 싱글톤으로 만들어서 관리해준다.
 * 싱글톤을 사용하면 고객 요청이 많이 들어와도 객체를 하나도 안 만들고
 * 이미 만들어져 있는 객체를 재활용해서 사용 -> 성능이 좋아진다.
 */
