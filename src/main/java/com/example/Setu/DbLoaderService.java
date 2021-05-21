package com.example.Setu;

import com.example.Setu.database.SplitWiseDatabase;
import com.example.Setu.model.BalanceRegister;
import com.example.Setu.model.Group;
import com.example.Setu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

@Service
public class DbLoaderService {
    @Autowired
    SplitWiseDatabase splitWiseDatabase;
    @Autowired
    RandomStringGenerator randomStringGenerator;
    public Boolean populateDb(){
        for(int i=0;i<10;i++){
            String firstName = randomStringGenerator.generateRandomString(5);
            String lastName = randomStringGenerator.generateRandomString(5);
            User user = new User(firstName, lastName, i);
            splitWiseDatabase.getUserStore().put(i, user);
        }

        Set<Integer> memberSet1 = Set.of(1,3,5,9);
        Set<Integer> memberSet2 = Set.of(2,4,5,8,7,9);
        Set<Integer> memberSet3 = Set.of(9,2,1,4);
        Set<Integer> memberSet4 = Set.of(6,3,7,2);
        Set<Integer> memberSet5 = Set.of(1,6,7,8);

        Group group1 = new Group(0, "Group1", memberSet1, new BalanceRegister(), new ArrayList<>());
        splitWiseDatabase.getGroupStore().put(0, group1);

        Group group2 = new Group(1, "Group2", memberSet2, new BalanceRegister(), new ArrayList<>());
        splitWiseDatabase.getGroupStore().put(1, group2);

        Group group3 = new Group(2, "Group3", memberSet3, new BalanceRegister(), new ArrayList<>());
        splitWiseDatabase.getGroupStore().put(2, group3);

        Group group4 = new Group(3, "Group4", memberSet4, new BalanceRegister(), new ArrayList<>());
        splitWiseDatabase.getGroupStore().put(3, group4);

        Group group5 = new Group(4, "Group5", memberSet5, new BalanceRegister(), new ArrayList<>());
        splitWiseDatabase.getGroupStore().put(4, group5);

        return true;
    };
}
