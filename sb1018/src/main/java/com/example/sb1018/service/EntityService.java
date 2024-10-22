package com.example.sb1018.service;

import com.example.sb1018.entity.Dept;
import com.example.sb1018.entity.Emp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.List;

@Slf4j
@Service
public class EntityService {

    @PersistenceUnit
    private EntityManagerFactory emf;

    public Dept updateDept(Integer deptNo, String deptName){

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Dept dept = em.find(Dept.class, deptNo);
        if(dept != null){
            dept.setDname(deptName);
            log.info("update dept {} with name {}" , deptNo, deptName);
        }else {
            log.info("해당 {} 부서가 없습니다.", deptNo);
        }
        tx.commit();
        return dept;
    }

    public List<Emp> selectAll(){
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        String jpql = "SELECT e FROM Emp e";
        TypedQuery<Emp> query = em.createQuery(jpql, Emp.class);
        List<Emp> emps = query.getResultList();
        tx.commit();

        return emps;
    }

    public Emp selectByEmp(Integer empno){
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        String jpql = "SELECT e FROM Emp e where e.empno = :empno";
        TypedQuery<Emp> query = em.createQuery(jpql, Emp.class);
        query.setParameter("empno", empno);
        Emp emps = query.getSingleResult();
        tx.commit();

        return emps;
    }

    public void insertEmp(Emp emp){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        emp.setSal(3000.0);
        emp.setComm(0.0);
        em.persist(emp);
        tx.commit();
    }

    public void deleteEmp(Integer empNo){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Emp emp = em.find(Emp.class, empNo);
        em.remove(emp);
        tx.commit();
    }
}
