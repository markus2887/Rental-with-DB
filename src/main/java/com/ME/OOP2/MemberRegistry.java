package com.ME.OOP2;

import com.ME.OOP2.entity.Member;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MemberRegistry {

    public MemberRegistry() throws Exception{
    }

    public ObservableList<Member> runJsonMembers() throws Exception {
        //Json funktionalitet
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        //Spara till JSON Members
        //mapper.writeValue(new File("members.json"), membersList);

        //Läsa in JSON Members
        List<Member> fromFile = Arrays.asList(mapper.readValue(new File("members.json"), Member[].class));
        ObservableList<Member> membersList = FXCollections.observableArrayList(fromFile);
        return membersList;
    }

    ObservableList<Member> membersList = runJsonMembers();



    public ObservableList<Member> getMember() {
        /* Members nedan läses in från members.json istället.
        membersList.add(new Member("Adam", 1 ,""));
        membersList.add(new Member("Sam", 1, ""));
        membersList.add(new Member("Kalle", 2, ""));
        membersList.add(new Member("Markus", 2, ""));
        membersList.add(new Member("Smocke", 1, ""));
        membersList.add(new Member("MimmiPigg", 1, ""));
        membersList.add(new Member("MussePigg", 1, ""));
        membersList.add(new Member("KalleAnka", 1, ""));
        membersList.add(new Member("Knatte", 1, ""));
        membersList.add(new Member("Fnatte", 1, ""));
        membersList.add(new Member("Tjatte", 1, ""));
        membersList.add(new Member("MårtenGås", 1, ""));
        membersList.add(new Member("FarmorAnka", 1, ""));
        membersList.add(new Member("FarbrorJoakim", 1, ""));
        membersList.add(new Member("VonPluring", 1, ""));
        membersList.add(new Member("Hulken", 1, ""));
        membersList.add(new Member("IronMan", 1, ""));
        membersList.add(new Member("SpiderMan", 1, ""));
        */
        return membersList;
    }




}
