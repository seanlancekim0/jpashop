package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStcokException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
// 상속 strategy: SINGLE_TABLE->한 table에 자식 class들의 attr다때려박고 쓰고싶은거 쓰기
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> catories = new ArrayList<>();

    // 비즈니스 로직

    /**
     * stock 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStcok(int quantity) {
        int restStcok = this.stockQuantity - quantity;
        if (restStcok < 0) {
            throw new NotEnoughStcokException("need more stock");
        }
        this.stockQuantity = restStcok;
    }
}
