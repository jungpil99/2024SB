package com.example.sb1018;

import com.example.sb1018.entity.Dept;
import com.example.sb1018.entity.Emp;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.*;
import java.util.List;

@SpringBootTest
class Sb1018ApplicationTests {


    @PersistenceUnit
    private EntityManagerFactory emf;

    @PersistenceContext
    private EntityManager em;


    @Test
    void 영속성_find_test() {

        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성

        EntityTransaction transaction = em.getTransaction(); // 엔티티 매니저로 엔티티 트랜젝션에 트랜젝션 정보를 전송
        transaction.begin();
        Dept dept = em.find(Dept.class, 10); //find = select
        dept.setDname("서울");
        System.out.println(dept);
        transaction.commit();
    }

    @Test
    void 영속성_merge_test2() {

        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성

        EntityTransaction transaction = em.getTransaction(); // 엔티티 매니저로 엔티티 트랜젝션에 트랜젝션 정보를 전송
        transaction.begin();
        Dept dept = em.find(Dept.class, 10); //find = select
        dept.setDname("인천");
        System.out.println(dept);
        em.detach(dept);  //detach를 하는 순간 영속성을 잃는다
        em.merge(dept); // merge = update
        transaction.commit(); // update 가 발생
    }

    @Test
    void  영속성_persist_test2(){
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성

        em.getTransaction().begin();

        Dept newDept = new Dept();
        newDept.setDeptno(50);
        newDept.setDname("연구소");
        newDept.setLoc("서울");

        em.persist(newDept);

        em.getTransaction().commit();
    }

    @Test
    void  영속성_remove_test2() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction(); // 엔티티 매니저로 엔티티 트랜젝션에 트랜젝션 정보를 전송
        transaction.begin();

        Dept dept = em.find(Dept.class, 50);
        System.out.println(dept);
        em.remove(dept);
        transaction.commit();
    }

    @Test
    void  update_test2() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction(); // 엔티티 매니저로 엔티티 트랜젝션에 트랜젝션 정보를 전송
        transaction.begin();

        Dept dept = em.find(Dept.class, 10);
        dept.setDname("Accounting");
        dept.setLoc("Seoul");
        System.out.println(dept);
        transaction.commit();
    }

    @Test
    void createQuery_Test(){
        Dept dept = em.find(Dept.class, 10);
        System.out.println(dept);
        System.out.println("================================================");
        TypedQuery<Dept> query = em.createQuery("select d from Dept d", Dept.class);
        List<Dept> depts = query.getResultList();
        for (Dept d : depts) {
            System.out.println(d);
        }
    }

    @Test
    void createQuery_Test2(){

        System.out.println("================================================");
        TypedQuery<Emp> query2 = em.createQuery("select e from Emp e", Emp.class);
        List<Emp> emps = query2.getResultList();
        for (Emp e : emps) {
            System.out.println(e);
        }
    }

    @Test
    void createQuery_Test3(){
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction(); // 엔티티 매니저로 엔티티 트랜젝션에 트랜젝션 정보를 전송
        transaction.begin();

        System.out.println("================================================");
        TypedQuery<Dept> query = em.createQuery("select d from Dept d", Dept.class);
        List<Dept> depts = query.getResultList();
        for (Dept d : depts) {
            System.out.println(d);
        }
        System.out.println(depts.get(0));
        depts.get(0).setLoc("서울");
        transaction.commit();
    }

    @Test
    void createQuery_Test4(){
        String jpql = "select d from Dept d where d.dname = :aaa";

        TypedQuery<Dept> query = em.createQuery(jpql, Dept.class);
        query.setParameter("aaa", "Accounting");
        List<Dept> depts = query.getResultList();
        Dept dept = depts.get(0);
        System.out.println(dept);

        System.out.println("================================================");
        String jpql2 = "select d from Emp d where d.deptno = :deptNo";
        TypedQuery<Emp> query2 = em.createQuery(jpql2, Emp.class);
        query2.setParameter("deptNo", dept.getDeptno());
        List<Emp> emps = query2.getResultList();
        for (Emp e : emps) {
            System.out.println(e);
        }
    }
}
