package com.ME.service;
import com.ME.entity.Member;
import com.ME.entity.Rental;
import com.ME.repo.MemberRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Optional;

public class MembershipService {

    private final MemberRepository memberRepository;
    private final ObservableList<Member> memberList = FXCollections.observableArrayList();

    public MembershipService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    public void loadMember() {
        memberList.setAll(memberRepository.readMember());
    }

    public ObservableList<Member> getMemberList() {
        return memberList;
    }


    public void addButtonClicked(TextField nameInput, TextField lvlInput, TableView<Member> mTable, Label labelResult) {
        String name = nameInput.getText();
        int lvl = Integer.parseInt(lvlInput.getText());

        saveMember(name, lvl);

        labelResult.setText("Medlem " + nameInput.getText() + " skapad!");
        nameInput.clear();
        lvlInput.clear();
    }

    public void saveMember(String name, int lvl) {
        Member member = new Member(name.trim(), lvl, "");
        memberRepository.saveMember(member);
        memberList.add(member);
    }

    public void deleteButtonClicked(TableView<Member> mTable) {
        Member selected = mTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }
        memberRepository.deleteMember(selected);
        memberList.remove(selected);
    }

    public String searchMemberByName(String name) {
        Optional<Member> memberFound = memberRepository.findByName(name);

        if (memberFound.isPresent()) {
            return "Hittade medlemmen " + memberFound.get().getName() + " med medlemsnivå " + memberFound.get().getLevel() + " och historik:\n" + memberFound.get().getHistory() + ".";
        } else {
            return(name + " hittades inte tyvärr.");
        }
    }

}
