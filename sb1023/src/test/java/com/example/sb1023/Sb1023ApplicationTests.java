package com.example.sb1023;

import com.example.sb1023.entity.Member;
import com.example.sb1023.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.List;

@SpringBootTest
class Sb1023ApplicationTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    void contextLoads() {
        Member member1 = new Member("member1", "회원1");
        Member member2 = new Member("member1", "회원1");
        Team team = new Team("team1", "팀1");

        member1.setTeam(team);
        member2.setTeam(team);
        System.out.println(member1);
        System.out.println(member2);
    }

    @Test
    void insert_test(){
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Team team1 = new Team("팀1", "team1");
        em.persist(team1);
        Member member1 = new Member("회원1", "member1");
        member1.setTeam(team1);
        em.persist(member1);
        Member member2 = new Member("회원2","member2" );
        member2.setTeam(team1);
        em.persist(member2);
        tx.commit();
        System.out.println(team1);
        System.out.println(member1);
    }

    @Test
    void find_test(){
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        String jpql = "select m from Member m join m.team t where t.name=:teamName";
        List<Member> members = em.createQuery(jpql, Member.class)
                        .setParameter("teamName", "팀1")
                .getResultList();
        for(Member member : members){
            System.out.println(member);
        }

        tx.commit();
    }

    @Test
    void find_test2(){
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Member member = em.find(Member.class, "회원1");
        System.out.println(member);

        System.out.println(member.getTeam());

        tx.commit();
    }

    @Test
    void update_test(){
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Team team2 = new Team("team2" , "팀2");
        em.persist(team2);

        Member member = em.find(Member.class, "회원2");
        member.setTeam(team2);

        tx.commit();

    }

    @Test
    void test_연관관계제거(){
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Member member1 = em.find(Member.class, "회원2");
        member1.setTeam(null);

        tx.commit();

    }

    @Test
    void test_연관된_엔티티삭제(){
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

//        Team team2 = em.find(Team.class, "team2");
//        em.remove(team2);

        Team team1 = em.find(Team.class, "team1");
        em.remove(team1);

        tx.commit();

    }

    @Test
    void test_양방향_탐색(){
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Team team = em.find(Team.class, "team1");
        for(Member member: team.getMembers()){
            System.out.println(member.getTeam().getName());
        }
//        for(Member member : team.getMembers()){
//            System.out.println(member);
//        }
//        team.getMembers().forEach(t -> System.out.println("member.username=" + t.getUsername()));
        tx.commit();

    }
}
