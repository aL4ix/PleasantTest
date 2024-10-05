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

    public void assertString(String expected) {
        String actual = params.get(index++);
        if (!actual.equals(expected)) {
            throw new IllegalArgumentException("Expected %s but found %s".formatted(expected, actual));
        }
    }
}
