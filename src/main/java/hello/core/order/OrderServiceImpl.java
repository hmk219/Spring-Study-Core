package hello.core.order;

import hello.core.discount.DiscountPolicy;
//import hello.core.discount.FixDiscountPolicy;
//import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

/*    정액할인정책에서 정률할인정책으로 변경하려면
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); 에서
    private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); 로 코드 변경? => OCP, DIP 위반
    -> OrderServiceImpl이 DiscountPolicy 인터페이스에만 의존하도록 설계를 변경하자
    privateDiscountPolicy discountPolicy;
    -> 그리고 OrderServiceImpl에 DiscountPolicy의 구현 객체를 생성해줘야 함 -> AppConfig 등장 */

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    // 이제 OrderServiceImpl은 FixDiscountPolicy를 직접 의존하지 않는다
    // 단지 DiscountPolicy 인터페이스만 의존한다
    // FixDiscountPolicy 객체의 생성과 연결(주입)은 외부에서(AppConfig) 담당한다
    // DIP 완성

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
