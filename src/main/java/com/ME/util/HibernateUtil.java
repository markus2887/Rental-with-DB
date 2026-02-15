package com.ME.util;

import com.ME.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.InputStream;
import java.util.Properties;

/**
 * HibernateUtil
 *
 * Ansvar:
 * - Skapa och hålla en enda SessionFactory för hela applikationen
 * - Läsa Hibernate-konfiguration från properties-fil
 *
 * Pedagogiskt:
 * - Detta är INFRASTRUKTURKOD
 * - Ska inte innehålla affärslogik
 * - Ska inte unit-testas
 */
public final class HibernateUtil {

    /**
     * Namnet på properties-filen som innehåller Hibernate-inställningar.
     *
     * Ligger normalt i:
     * src/main/resources/hibernate.properties
     */
    private static final String PROPERTIES_FILE = "hibernate.properties";

    /**
     * En enda SessionFactory för hela applikationen.
     *
     * - static + final = skapas exakt en gång
     * - thread-safe
     *
     * Detta är ett klassiskt "Singleton"-liknande mönster.
     */
    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    /**
     * Privat konstruktor.
     *
     * Förhindrar att någon skapar instanser av HibernateUtil.
     * Klassen används ENDAST via statiska metoder.
     */
    private HibernateUtil() { }

    /**
     * Returnerar den gemensamma SessionFactory-instansen.
     *
     * Används av repository-implementationer.
     */
    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    /**
     * Stänger SessionFactory när applikationen avslutas.
     *
     * Viktigt:
     * - Frigör resurser
     * - Stänger DB-anslutningar
     */
    public static void shutdown() {
        if (!SESSION_FACTORY.isClosed()) {
            SESSION_FACTORY.close();
        }
    }

    /**
     * Bygger SessionFactory.
     *
     * Körs EN gång när klassen laddas.
     */
    private static SessionFactory buildSessionFactory() {

        try {
            // -------------------------------------------------
            // 1. Läs in Hibernate-properties
            // -------------------------------------------------

            Properties properties = new Properties();

            // Läser hibernate.properties från classpath (resources)
            try (InputStream in =
                         HibernateUtil.class
                                 .getClassLoader()
                                 .getResourceAsStream(PROPERTIES_FILE)) {

                // Om filen inte hittas → krascha direkt
                if (in == null) {
                    throw new IllegalStateException(
                            PROPERTIES_FILE + " not found in resources"
                    );
                }

                // Ladda properties till Properties-objektet
                properties.load(in);
            }

            // -------------------------------------------------
            // 2. Skapa Hibernate Configuration
            // -------------------------------------------------

            Configuration configuration = new Configuration();

            // Sätt alla properties (DB-url, dialect, show_sql, etc.)
            configuration.setProperties(properties);

            // -------------------------------------------------
            // 3. Registrera alla entity-klasser
            // -------------------------------------------------

            configuration.addAnnotatedClass(Car.class);
            configuration.addAnnotatedClass(Tool.class);
            configuration.addAnnotatedClass(Movie.class);
            configuration.addAnnotatedClass(Rental.class);
            configuration.addAnnotatedClass(Member.class);


            /*
             * Viktigt pedagogiskt:
             * Hibernate måste veta vilka @Entity-klasser som finns.
             * I Spring Boot görs detta automatiskt – här gör vi det manuellt.
             */

            // -------------------------------------------------
            // 4. Bygg ServiceRegistry
            // -------------------------------------------------

            ServiceRegistry serviceRegistry =
                    new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties())
                            .build();

            // -------------------------------------------------
            // 5. Bygg SessionFactory
            // -------------------------------------------------

            return configuration.buildSessionFactory(serviceRegistry);

        } catch (Exception e) {

            // Om SessionFactory inte kan byggas är applikationen obrukbar
            throw new ExceptionInInitializerError(
                    "Failed to build SessionFactory: " + e.getMessage()
            );
        }
    }
}