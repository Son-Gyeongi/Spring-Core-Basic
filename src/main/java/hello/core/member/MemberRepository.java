package hello.core.member;

public interface MemberRepository {

    void save(Member member);

    Member findById(Long memberId);

    /*
    인터페이스와 구현체 파일을 만들 때 위치를 같은 패키지에 두는 거보다
     다른 패키지에 두는 게 설계상 좋다. 예제가 복잡해져서 그냥 같은 패키지에 넣겠다.
    */
}
