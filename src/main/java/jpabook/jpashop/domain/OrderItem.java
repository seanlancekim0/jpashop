package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    // 생성 메서드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStcok(count);
        return orderItem;
    }

    // 생성 메서드 이외의 방식으로 선언하는 것을 방지해주는 역할을 하는 protected 생성자
    // @NoArgsConstructor(access = AccessLevel.PROTECTED)로 대체
//    protected OrderItem() {
//    }

    // 비즈니스 로직
    public void cancel() {
        getItem().addStock(count);
    }

    // 조회 로직

    /**
     * 주문상품 전체가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
