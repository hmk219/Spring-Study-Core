package hello.core;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository());
    }
/*    MemoryMemberRepository의 객체를 생성하고
    -> MemberServiceImpl 객체를 생성하면서 생성자의 참조값으로 전달 (생성자 주입)
    => MemberServiceImpl에 MemoryMemberRepository 객체의 의존관계 주입 */


    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }
/*    MemoryMemberRepository와 FixDiscountPolicy 객체를 생성하고
    -> OrderServiceImpl 객체를 생성하면서 생성자의 참조값으로 전달 (생성자 주입)
    => OrderServiceImpl에 MemoryMemberRepository와 FixDiscountPolicy 객체의 의존관계 주입 */

}
