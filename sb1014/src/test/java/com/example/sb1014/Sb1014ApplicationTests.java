package com.example.sb1014;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class Sb1014ApplicationTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    void testMemo() {
//        System.out.println(memoRepository.getClass().getName());
        IntStream.range(0, 10).forEach(i -> {
//            System.out.println(i);
            Memo memo = Memo.builder().memoText("샘플" +  i).build();
//            System.out.println(memo);
            memoRepository.save(memo);
        });

        Long mno = 1L;
        Optional<Memo> result = memoRepository.findById(mno);
        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }else {
            System.out.println("없음");
        }

        Memo memo1 = Memo.builder().id(1L).memoText("샘플100").build();
        memoRepository.save(memo1);

        List<Memo> memos = memoRepository.findAll();
        for(Memo memo : memos) {
            System.out.println(memo);
        }

        memoRepository.deleteById(mno);

    }

    @Test
    void select_Memo() {
        Long mno = 9L;
        Optional<Memo> result = memoRepository.findById(mno);
        System.out.println(result);
    }

    @Test
    void findAll_Memo() {
        List<Memo> memos = memoRepository.findAll();
        for(Memo memo : memos) {
            System.out.println(memo);
        }
    }

    @Test
    void update_Memo() {
        Memo memo = Memo.builder().id(1L).memoText("샘플100").build();
        memoRepository.save(memo);
    }

    @Test
    void delete_Memo() {
        Long mno = 1L;
        memoRepository.deleteById(mno);
    }

    @Test
    void test_queryMethod(){
        List<Memo> memos = memoRepository.findByIdBetween(2L, 7L);
        for(Memo memo : memos) {
            System.out.println(memo);
        }
    }

    @Test
    void test_queryMethod2(){
        List<Memo> memos = memoRepository.findByIdBetweenOrderByIdDesc(2L, 7L);
        for(Memo memo : memos) {
            System.out.println(memo);
        }
    }

    @Test
    void test_queryAnnotation(){
        System.out.println(memoRepository.getCount() + "건의 자료가 있다");
        List<Memo> memos = memoRepository.getListDesc();
        for(Memo memo : memos) {
            System.out.println(memo);
        }
    }
}
