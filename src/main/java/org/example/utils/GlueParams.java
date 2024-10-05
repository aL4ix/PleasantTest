package org.example.utils;

import java.util.List;

public class GlueParams {
    List<String> params;
    int index;

    public GlueParams(List<String> params) {
        this.params = params;
        index = 0;
    }

    public String getNextString() {
        return params.get(index++);
    }
}
