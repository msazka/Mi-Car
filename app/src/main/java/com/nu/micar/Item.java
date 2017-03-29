package com.nu.micar;

import java.util.List;

/**
 * Created by ashfaqn on 3/29/2017.
 */

public class Item {
    public int type;
    public String header;
    public MiCarTrackDetails miCarTrackDetails;
    public List<Item> invisibleChildren;

    public Item(int type, String header) {
        this.type = type;
        this.header = header;
    }

    public Item(int type, MiCarTrackDetails miCarTrackDetails) {
        this.type = type;
        this.miCarTrackDetails = miCarTrackDetails;
    }
}
