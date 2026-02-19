package com.ME.service;

import com.ME.entity.Member;
import com.ME.repo.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MembershipServiceTest {
    private MembershipService membershipService;
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        // Mocka MemberRepository
        memberRepository = mock(MemberRepository.class);

        // Skapa service med mocked repository
        membershipService = new MembershipService(memberRepository);
    }


    @Test
    void saveMemberMethod_shouldAddMemberToRepositoryAndList() {
        String name = "Markus "; //name kommer trimmas
        int level = 2;

        membershipService.saveMember(name, level);

        assertEquals(1, membershipService.getMemberList().size());

        Member saved = membershipService.getMemberList().get(0);

        assertEquals("Markus", saved.getName()); //name trimmas
        assertEquals(2, saved.getLevel());
        assertEquals("", saved.getHistory());
    }

}
