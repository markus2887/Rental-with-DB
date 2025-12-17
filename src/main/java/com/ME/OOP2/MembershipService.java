package com.ME.OOP2;
import com.ME.OOP2.entity.Member;
import com.ME.OOP2.entity.Rental;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MembershipService {

    MemberRegistry mReg = new MemberRegistry();

    public MembershipService() throws Exception {
    }

    public void addButtonClicked(TextField nameInput, TextField lvlInput, TableView<Member> mTable, Label labelResult) {
        String name = nameInput.getText();
        int lvl = Integer.parseInt(lvlInput.getText());
        Member member = new Member(name, lvl, "");
        mTable.getItems().add(member);
        labelResult.setText("Medlem " + nameInput.getText() + " skapad!");
        nameInput.clear();
        lvlInput.clear();
    }

    public void deleteButtonClicked(TableView<Member> mTable) {
        ObservableList<Member> memberSelected, allMembers;
        allMembers = mTable.getItems();
        memberSelected = mTable.getSelectionModel().getSelectedItems();
        memberSelected.forEach(allMembers::remove);
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

    public Member searchMemberR(ObservableList<Member> membersList, String userName) {
        Member foundMember = membersList.stream()
                .filter(m -> m.getName().equalsIgnoreCase(userName))
                .findFirst()
                .orElse(null);

        return foundMember;
    }

}
