package hello.core.member;

/*
Impl은 관례같은 거다.
구현체가 하나만 있을 때는 그냥 인터페이스명 뒤에 Impl이라고 많이 쓴다.
 */
public class MemberServiceImpl implements MemberService {

    // 가입을 하고 회원 찾기 위해 필요
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;
    // 생성자를 통해서 MemberRepository에 구현체가 뭐가 들어갈 지 정할거다.
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
}
