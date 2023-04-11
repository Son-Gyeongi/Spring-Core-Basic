package hello.core.member;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/*
회원 저장소
데이터베이스 확정이 되지 않아서 MemoryMemberRepository만 만들었다.
Memory에 저장하는 거라서 서버 껐다가 켜면 데이터 없어진다. 그래서 테스트로 사용
 */
@Component // @ComponentScan 대상이 되면서 스프링 빈으로 등록
public class MemoryMemberRepository implements MemberRepository {

    /*
    저장소라서 Map이 있어야 한다.
    HashMap을 쓰기는 했지만 원래는 ConcurrentHashMap<>()을 써야한다. 동시성 문제가 있을 수 있어서
     */
    private static Map<Long,Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
        // 오류 처리를 다 해야하는데 오류 처리가 핵심이 아니니깐 넘어가겠다.
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
