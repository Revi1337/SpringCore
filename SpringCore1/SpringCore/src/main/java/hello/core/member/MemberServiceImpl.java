package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{

    // 철저하게 DIP 를 지키고있음. (MemberRepository 인터페이스에만 의존함. 구체적인 클래스에 전혀 의존하지 않음)
    private final MemberRepository memberRepository;

    @Autowired // MemberRepository 타입의 Bean 을 의존관계를 자동으로 주입해줌. (생성자 주입)
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
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

}
