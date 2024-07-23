package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            // 이때 merge가 머지? 컴하하!
            // upate 상황에 사용, 파라미터로 넘어온 준영속 Entity인 item을 영속상태로 변경함
            // ItemService.updatItem()와 비슷하rp JPA 내에서 작동, 차이점은 Entity의 변경되는 속성을 선택할 수 없음.
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
