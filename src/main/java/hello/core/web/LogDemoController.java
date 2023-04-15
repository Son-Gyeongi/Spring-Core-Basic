package hello.core.web;

import hello.core.common.Mylogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor // 생성자에 @Autowired가 있는 코드 자동생성, final멤버변수 의존관계 자동주입
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final Mylogger mylogger;

    @RequestMapping("log-demo")
    @ResponseBody // view 화면이 없이 바로 문자로 반환하고 싶을 때, 문자 그대로 응답
    public String longDemo(HttpServletRequest request) {
        // HttpServletRequest - 자바에서 제공하는 표준 서블릿 규약이 있는데
        // 그게 HttpRequest 정보를 받을 수 있다. 고객 요청정보를 받을 수 있다.

        String requestUrl = request.getRequestURL().toString(); // 고객이 어떤 url로 요청했는지 알 수 있다.
        mylogger.setRequestURL(requestUrl);

        mylogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }
}
