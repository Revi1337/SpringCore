package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);   // 어노테이션 기반으로 연결된 설정파일을 가져옴
        MemberService memberService = ctx.getBean("memberService", MemberService.class); // 설정파일에서 Bean 을 갖고오는데, Bean 의 이름은 기본적으로 @Bean 이 달려있는 메서드 이름임

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("find Member = " + findMember.getName());
    }

}
