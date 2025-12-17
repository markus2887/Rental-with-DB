package com.ME.OOP2;

public class SequenceNumberProviderLazy {
    private int number;
    private static SequenceNumberProviderLazy instance;

    private SequenceNumberProviderLazy() {
        number = 13;
    }
    public static SequenceNumberProviderLazy getInstance(){
        if(instance==null){
            instance = new SequenceNumberProviderLazy();
        }
        return instance;
    }

    public int getNextNumber(){
        return number++;
    }
}