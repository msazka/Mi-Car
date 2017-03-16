package com.nu.micar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> cricket = new ArrayList<String>();
        cricket.add("LEE 7794");
        cricket.add("LEE 7794");
        cricket.add("LEE 7794");


        List<String> football = new ArrayList<String>();
        football.add("LEE 7794");
        football.add("LEE 7794");
        football.add("LEE 7794");

        List<String> basketball = new ArrayList<String>();
        basketball.add("LEE 7794");
        basketball.add("LEE 7794");
        basketball.add("LEE 7794");

        expandableListDetail.put("Honda", cricket);
        expandableListDetail.put("Toyota", football);
        expandableListDetail.put("Suzuki", basketball);
        return expandableListDetail;
    }
}
