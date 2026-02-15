package com.ME.repo;
import com.ME.entity.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class MemberRepositoryImpl implements MemberRepository {
    /**
     * SessionFactory är en tung och dyr resurs.
     *
     * - Skapas en gång i HibernateUtil
     * - Återanvänds i hela applikationen
     * - Injiceras via konstruktor (enkel dependency injection)
     */
    private final SessionFactory sessionFactory;

    public MemberRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Sparar en Customer i databasen.
     *
     * Flöde:
     * 1. Öppna en ny Hibernate Session
     * 2. Starta en transaktion
     * 3. Persist:a Customer-objektet
     * 4. Committa transaktionen
     *
     * OBS:
     * - Ingen affärslogik här
     * - Ingen validering av input
     * - Vi antar att Customer redan är korrekt skapad
     *   i service-lagret
     */
    @Override
    public List<Member> readMember() {

        try (Session session = sessionFactory.openSession()) {
            return session
                    .createNativeQuery("SELECT * FROM members", Member.class)
                    .getResultList();
        }

    }

    /*@Override
    public Member findMember() {
        Member m =
        return m;
    }*/

    @Override
    public void saveMember(Member member) {

        // try-with-resources säkerställer att sessionen alltid stängs
        try (Session session = sessionFactory.openSession()) {
            // Startar en databastransaktion
            var tx = session.beginTransaction();

            // Gör customer persistent
            // INSERT sker först när transaktionen committas
            session.persist(member);

            // Committar transaktionen → data skrivs till databasen
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
}
