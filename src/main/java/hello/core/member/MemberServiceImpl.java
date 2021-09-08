package hello.core.member;

public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    // MemberServiceImpl은 MemoryMemberRepository를 직접 의존하지 않는다
    // 단지 MemberRepository 인터페이스만 의존한다
    // MemoryMemberRepository 객체의 생성과 연결(주입)은 외부에서(AppConfig) 담당한다
    // DIP 완성



    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
