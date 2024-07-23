package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    /**
     * 준영속 상태의 Entity를 수정하는 2가지 방법
     */

    // 변경감지(Dirty Checking) 사용
    // param: 파라미터로 넘어온 준영속 상태의 Entity
    @Transactional
    public Item updatItem(Long itemId, UpdateItemDto itemDto) {
        // findItem은 영속상태
        Item findItem = itemRepository.findOne(itemId);
        findItem.change(itemDto.getName(), itemDto.getPrice(), itemDto.getStockQuantity());

        // @Transactional에 의해 Transaction이 commit되면 따로 save를 해주지 않아도 JPA가 변경을 감지해서 자동으로 DB에 query를 날림
        return findItem;
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
