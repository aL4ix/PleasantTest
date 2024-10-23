package org.example.glue;

import org.example.utils.PleasantTestException;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GlueParams {
    private final List<String> params;
    private int index;
    private final Memory memory;
    private final static String VARIABLE_REGEX = "\\{(\\w+)}";

    public GlueParams(List<String> params, Memory memory) {
        this.params = params;
        this.memory = memory;
        index = 0;
    }

    public String getNextString() {
        String param = params.get(index);

        Pattern pattern = Pattern.compile(VARIABLE_REGEX);
        Matcher matcher = pattern.matcher(param);
        while (matcher.find()) {
            String variable = matcher.group(1);
            String replacement = memory.get(variable);
            if (replacement == null) {
                replacement = "null";
                System.out.println(variable);
                System.out.println(memory);
            }
            int start = matcher.start();
            int end = matcher.end();
            param = param.substring(0, start) + replacement + param.substring(end);
        }

        index++;
        return param;
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
