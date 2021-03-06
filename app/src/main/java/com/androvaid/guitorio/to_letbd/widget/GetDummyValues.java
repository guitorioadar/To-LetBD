package com.androvaid.guitorio.to_letbd.widget;

import java.util.ArrayList;

public class GetDummyValues {
    private String names;
    private static ArrayList<Values> values;

    public GetDummyValues() {
        addData();
    }

    public ArrayList<Values> addData() {
        values = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            values.add(new Values(String.valueOf(i), "value " + i));
        }
        return values;
    }

    public ArrayList<String> getValues() {
        ArrayList<String> myValues = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            myValues.add(values.get(i).value);
        }
        return myValues;
    }

    public ArrayList<String> getIds() {
        ArrayList<String> myIds = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            myIds.add(values.get(i).id);
        }
        return myIds;
    }

    static class Values {
        String id;
        String value;

        public Values(String id, String value) {
            this.id = id;
            this.value = value;
        }
    }

}