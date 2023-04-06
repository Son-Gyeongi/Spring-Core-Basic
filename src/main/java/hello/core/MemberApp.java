package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
//        MemberService memberService = new MemberServiceImpl(memberRepository);
        /**
         * 기존에는 우리가 MemberApp에서 직접 객체를 생성했는데
         * 이제는 AppConfig를 이용해서 개발해보자
         */
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();
        /**
         * MemberApp을 AppConfig 버전에서 스프링을 사용하는 버전으로 바꾸자
         * 스프링은 모든게 ApplicationContext라는 걸로 시작이 된다.
         * 이게 스프링 컨테이너다, 애가 모든 객체들을 관리해준다. @Bean으로 선언된 것들을 관리
         * AnnotationConfigApplicationContext : @Configuration, @Bean 애노테이션 기반으로 Config하고있다.
         * AppConfig.class에 있는 환경설정 정보를 가지고 스프링이 AppConfig.class 안에 있는
         * @Bean이 붙은 거를 스프링 컨테이너에다가 자기가 객체 생성한 거를 다 집어넣어서 관리해준다.
         * applicationContext.getBean("내가 AppConfig에서 꺼낼 메서드를 써준다.(객체)", 타입)
         */
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        // 이름은 memberService고 타입은 MemberService.class야
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("findMember = " + findMember.getName());
    }
}
