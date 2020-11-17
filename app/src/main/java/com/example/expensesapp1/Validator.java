package com.example.expensesapp1;

public class Validator {

    public boolean validate(String input1){
        if(input1.isEmpty()){
            return true;
        }
        else
            return false;
    }

    public boolean validate(String input1, String input2){
        if(input1.isEmpty()||input2.isEmpty()){
            return true;
        }
        else
            return false;
    }

}
