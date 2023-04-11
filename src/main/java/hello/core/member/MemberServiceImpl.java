package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
Impl은 관례같은 거다.
구현체가 하나만 있을 때는 그냥 인터페이스명 뒤에 Impl이라고 많이 쓴다.
 */
@Component // @ComponentScan 대상이 되면서 스프링 빈으로 등록
public class MemberServiceImpl implements MemberService {

    // 가입을 하고 회원 찾기 위해 필요
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    // 생성자를 통해서 MemberRepository에 구현체가 뭐가 들어갈 지 정할거다.
    /**
     * @Component 작성 후 자동 의존 관계 주입
     * ac.getBean(MemberRepository.class) 이런식으로 동작한다고 생각하면 된다.
     */
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트 용도
    // MemberServiceImpl에 있는 MemberRepository 꺼내보자
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
