package org.example.glue;

import org.example.utils.PleasantTestException;

import java.io.File;
import java.util.List;

public class GlueParams {
    private final List<String> params;
    private int index;

    public GlueParams(List<String> params) {
        this.params = params;
        index = 0;
    }

    public String getNextString() {
        return params.get(index++);
    }

    public List<String> getTheRestAsList() {
        int size = params.size();
        List<String> strings = params.subList(index, size);
        index = size;
        return strings;
    }

    public void assertNextStringIsEqualTo(String expected, File file, int lineNum) {
        String actual = params.get(index++);
        if (!actual.equals(expected)) {
            throw new PleasantTestException(file, lineNum, "Expected %s but found %s", expected, actual);
        }
    }
}
