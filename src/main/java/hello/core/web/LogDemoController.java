package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor // 생성자에 @Autowired가 있는 코드 자동생성, final멤버변수 의존관계 자동주입
public class LogDemoController {

    private final LogDemoService logDemoService;
//    private final Mylogger mylogger; // 생성자로 의존관계 주입할 때는 request요청이 없어서 에러가 난다.
    // => 해결1 ObjectProvider로 요청이 들어올 때
    // MyLogger를 주입받는게 아니라 MyLogger를 찾을 수 있는 Dependency Lookup할 수 있는 애가 주입이된다.
    // myLoggerProvider는 주입시점에 주입을 받는다.
    /**
     * controller에 고객 요청이 와서 (controller에 고객 요청이 왔다는 건 Http가 살아있는 상태이다.)
     * 스코프를 쓸 수 있는 상황이다.(Http request가 들어왔으니깐)
     */
    private final ObjectProvider<MyLogger> myLoggerProvider;

    @RequestMapping("log-demo")
    @ResponseBody // view 화면이 없이 바로 문자로 반환하고 싶을 때, 문자 그대로 응답
    public String longDemo(HttpServletRequest request) throws InterruptedException {
        // HttpServletRequest - 자바에서 제공하는 표준 서블릿 규약이 있는데
        // 그게 HttpRequest 정보를 받을 수 있다. 고객 요청정보를 받을 수 있다.

        String requestUrl = request.getRequestURL().toString(); // 고객이 어떤 url로 요청했는지 알 수 있다.
        MyLogger myLogger = myLoggerProvider.getObject();
        myLogger.setRequestURL(requestUrl);

        myLogger.log("controller test");
        Thread.sleep(1000); // 다양한 요청이 들어오는 걸 보려고, 1000ms는 1초
        logDemoService.logic("testId");
        return "OK";
    }
}
