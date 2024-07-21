package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.grammars.ordering.OrderingParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAdress());

        // 주문 상품 생성 (단일상품)
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
//        OrderItem orderItem1 = new OrderItem();

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);
//        Order order1 = new Order();

        // 주문 저장
        // Order Entity에서 OrderItem과 Delivery에 cascade(상속)옵션을 추가했기 때문에 두 Entity도 자동으로 persist 호출이 된다.
        orderRepository.save(order);

        return order.getId();
    }


    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        // 주문 엔티티 조회
        Order order = orderRepository.findOrder(orderId);
        // 주문 취소
        // DB에 수정 Query를 날릴 필요없이 JPA가 Order, OrderItem Entity의 변경을 감지해서 수정 Query를 날려줌!!
        order.cancel();
    }

    /**
     * 검색
     */
//    public List<Order> searchOrders(OrderSearch orderSearch) {
//        return orderRepository.finAll(orderSearch);
//    }


}
