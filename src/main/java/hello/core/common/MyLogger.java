package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value = "request") // request스코프 이 빈은 HTTP요청 당 하나씩 생성되고, HTTP요청이 끝나는 시점에 소멸
// 로그를 출력하기 위한 클래스
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("["+ uuid + "]" + "[" + requestURL + "] " + message);
    }

    // 고객요청이 들어올 때 최초로 필요하다고 스프링한테 달라고 할때 init 호출
    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();// 글로벌하게 유니크한 아이디가 생성된다. 이거는 절대 겹치지 않는다.
        // this를 사용해서 주소까지 나오게한다.
        System.out.println("["+ uuid + "] request scope bean create: " + this);
    }

    // request 스코프는 소멸이된다.
    // 스프링 관리하다가 고객 요청이 우리 서버에서 빠져나가면 그때 close호출되면서 빈이 소멸된다.
    @PreDestroy
    public void close() {
        System.out.println("["+ uuid + "] request scope bean close: " + this);
    }
}
