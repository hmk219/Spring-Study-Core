package hello.core.order;

import hello.core.discount.DiscountPolicy;
//import hello.core.discount.FixDiscountPolicy;
//import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    // final -> 생성자에 필드 누락되면 컴파일 오류로 알려줌, 생성자 주입 방식만 final 쓸 수 있다.
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

/*    기본으로 생성자 주입 방식을 사용하고 필수 값이 아닌 경우에는 수정자 주입을 옵션으로 부여
    생성자 주입과 수정자 주입은 동시에 사용할 수 있다*/

    // 생성자 통해 의존관계 주입
    // 불변, 필수인 의존관계에서 사용
    @Autowired // 생성자가 하나만 있을 땐 @Autowired 생략 가능
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

//    setter 통해 의존관계 주입
//    선택, 변경 가능성이 있는 의존관계에서 사용
//    필드 선언에서 final 빼고 setter를 public으로 열어줘야 함
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }


    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
