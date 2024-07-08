package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    // DB에 insert문 날리는 2가지 방법
    // 1. Test코드에서는 기본적으로 Transaction을 commit하지 않고 Rollback 해버려서 DB 저장이 되지 않음
//    @Rollback(value = false)
    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        // 2. EntityManager flush()를 통해 강제로 DB에 insert함
        em.flush();
        assertEquals(member,memberRepository.findOne(savedId));
    }

    // tdd

    @Test/*(expected = IllegalStateException.class)*/
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        try {
            memberService.join(member1); // 예외가 튀어나와야함!
        } catch (IllegalStateException e) {
            return;
        }

        //then
        fail("예외가 발생해야 합니다.");
    }
}