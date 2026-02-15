package com.ME.service;
import com.ME.entity.Member;
import com.ME.repo.MemberRepositoryImpl;
import com.ME.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MembershipService {

    MemberRepositoryImpl mRepo = new MemberRepositoryImpl(HibernateUtil.getSessionFactory());

    private final ObservableList<Member> memberList = FXCollections.observableArrayList();

    public MembershipService() throws Exception {
    }

    public void loadMember() {
        memberList.setAll(mRepo.readMember());
    }

    public ObservableList<Member> getMemberList() {
        return memberList;
    }

    public Member addButtonClicked(TextField nameInput, TextField lvlInput, TableView<Member> mTable, Label labelResult) {
        String name = nameInput.getText();
        int lvl = Integer.parseInt(lvlInput.getText());
        Member member = new Member(name.trim(), lvl, "");
        mTable.getItems().add(member);
        mRepo.saveMember(member);
        labelResult.setText("Medlem " + nameInput.getText() + " skapad!");
        nameInput.clear();
        lvlInput.clear();

        return member;
    }

    public void deleteButtonClicked(TableView<Member> mTable) {
        Member selected = mTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }

        mRepo.deleteMember(selected);
        memberList.remove(selected);
    }

    public void searchMember(ObservableList<Member> membersList, String userName, Label labelResult) {
        Member foundMember = membersList.stream()
                .filter(m -> m.getName().equalsIgnoreCase(userName))
                .findFirst()
                .orElse(null);

        if (foundMember != null) {
            labelResult.setText("Hittade användarnamn " + foundMember.getName() + " med ID nr: " + foundMember.getId() + " och medlemsnivå: " + foundMember.getLevel());
        } else {
            labelResult.setText("Användarnamnet '" + userName + "' hittades tyvärr inte.");
        }
    }

    public Member searchMemberR(String userName, ObservableList<Member> memberListIn) {
        Member foundMember = memberListIn.stream()
                .filter(m -> m.getName().equalsIgnoreCase(userName))
                .findFirst()
                .orElse(null);

        return foundMember;
    }

}
