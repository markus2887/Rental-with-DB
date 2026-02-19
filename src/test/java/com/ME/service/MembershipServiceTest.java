package com.ME.service;

import com.ME.entity.Member;
import com.ME.repo.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        String name = "Markus   "; //name kommer trimmas
        int level = 2;

        membershipService.saveMember(name, level);

        assertEquals(1, membershipService.getMemberList().size());

        Member saved = membershipService.getMemberList().get(0);

        assertEquals("Markus", saved.getName()); //name trimmas
        assertEquals(2, saved.getLevel());
        assertEquals("", saved.getHistory());
    }

    @Test
    void searchMemberByNameMethod_ifMemberExistsShouldReturnFoundMessage() {
        // Arrange
        Member mockMember = new Member("mockMember", 1, "");

        when(memberRepository.findByName("mockMember")).thenReturn(Optional.of(mockMember));

        // Act
        String result = membershipService.searchMemberByName("mockMember");

        // Assert
        assertEquals("Hittade medlemmen mockMember med medlemsnivå 1 och historik:\n.", result);
    }

    @Test
    void searchMemberByNameMethod_ifMemberNotExistsShouldReturnNotFoundMessage() {
        // Arrange
        when(memberRepository.findByName("Anna")).thenReturn(Optional.empty());

        // Act
        String result = membershipService.searchMemberByName("Anna");

        // Assert
        assertEquals("Anna hittades inte tyvärr.", result);
        verify(memberRepository, times(1)).findByName("Anna");
    }
}
