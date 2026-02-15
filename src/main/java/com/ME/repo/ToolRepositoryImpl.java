package com.ME.repo;

import com.ME.entity.Tool;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class ToolRepositoryImpl implements ToolRepository {

    private final SessionFactory sessionFactory;

    public ToolRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Tool> readTool() {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createNativeQuery("SELECT * FROM tools", Tool.class)
                    .getResultList();
        }
    }

    @Override
    public void saveTool(Tool tool) {
        try (Session session = sessionFactory.openSession()) {
            var tx = session.beginTransaction();
            session.persist(tool);
            tx.commit();
        }
    }

    @Override
    public void updateTool(Tool tool) {
        try (Session session = sessionFactory.openSession()) {
            var tx = session.beginTransaction();
            session.merge(tool);
            tx.commit();
        }
    }

    @Override
    public void deleteTool(Tool tool) {
        try (Session session = sessionFactory.openSession()) {
            var tx = session.beginTransaction();

            session.remove(tool);
            tx.commit();
        }
    }
}
