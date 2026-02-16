package com.ME.repo;

import com.ME.entity.Member;
import com.ME.entity.Rental;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Optional;

/**
 * CustomerRepository
 *
 * Repository-interface för Customer.
 *
 * Ansvar:
 * - Definierar vilka databasoperationer som får göras på Customer
 * - Innehåller INGEN implementation
 *
 * Pedagogiskt:
 * - Detta är ett kontrakt
 * - Service-lagret beror på detta interface, inte på Hibernate
 * - Den faktiska databastekniken (Hibernate, JDBC, etc.)
 *   bestäms i implementationen
 *
 * Designprincip:
 * - Programmera mot interface, inte implementation
 * - Gör koden testbar (lätt att mocka i enhetstester)
 */
public interface MemberRepository {
    /**
     * Sparar en Customer i databasen.
     *
     * Kontrakt:
     * - Tar emot ett färdigt Customer-objekt
     * - Ansvarar för att göra objektet persistent
     * - Returnerar inget (vi litar på att objektet får id efter persist)
     *
     * OBS:
     * - Ingen validering här
     * - Inga affärsregler
     * - All sådan logik hör hemma i service-lagret
     */

    void saveMember(Member member);
    List<Member> readMember();
    //Member findMember();
    void updateMember(Member member);
    void deleteMember(Member member);
    Optional<Member> findById(long id);
    Optional<Member> findByName(String name);
}
