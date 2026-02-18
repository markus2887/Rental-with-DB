package com.ME.repo;
import com.ME.entity.Member;
import com.ME.entity.Rental;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class MemberRepositoryImpl implements MemberRepository {

    private final SessionFactory sessionFactory;

    public MemberRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Member> readMember() {

        try (Session session = sessionFactory.openSession()) {
            return session
                    .createNativeQuery("SELECT * FROM members", Member.class)
                    .getResultList();
        }

    }

    @Override
    public void saveMember(Member member) {
        try (Session session = sessionFactory.openSession()) {
            var tx = session.beginTransaction();
            session.persist(member);
            tx.commit();
        }
    }

    @Override
    public void updateMember(Member member) {
        try (Session session = sessionFactory.openSession()) {
            var tx = session.beginTransaction();
            session.merge(member);
            tx.commit();
        }
    }

    @Override
    public void deleteMember(Member member) {
        try (Session session = sessionFactory.openSession()) {
            var tx = session.beginTransaction();
            session.remove(member);
            tx.commit();
        }
    }

    @Override
    public Optional<Member> findById(long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Member.class, id));
        }
    }

    @Override
    public Optional<Member> findByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            return session.createNativeQuery(
                    "SELECT * FROM members WHERE LOWER(name) = LOWER(:name)", Member.class)
                    .setParameter("name", name)
                    .uniqueResultOptional();
        }
    }
}
