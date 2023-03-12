package hello.core.member;

public class MemberServiceImpl implements MemberService{

    // 철저하게 DIP 를 지키고있음. (MemberRepository 인터페이스에만 의존함. 구체적인 클래스에 전혀 의존하지 않음)
    private final MemberRepository memberRepository;

    // MemberServiceImpl 입장에서 생성자를 통해 어떤 구현 객체가 들어올지(주입될지)는 알 수 없다 (생성자 주입패턴)
    // MemberServiceImpl 의 생성자를 통해서 어떤 구현 객체를 주입할지는 오직 외부( AppConfig )에서 결정된다.
    // MemberServiceImpl 은 이제부터 의존관계에 대한 고민은 외부(AppConfig)에 맡기고 실행에만 집중하면 된다
    // MemberServiceImpl 입장에서 보면 의존관계를 외부(AppConfig) 에서 주입해주는 것 같다고 해서, DI(Dependency Injection) 우리말로 의존관계 주입 또는 의존성 주입이라고 한다.
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
