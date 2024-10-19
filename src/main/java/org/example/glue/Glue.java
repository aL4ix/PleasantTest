package org.example.glue;

import org.example.utils.PleasantTestException;
import org.example.utils.StringUtils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Glue {
    private final Map<String, ParsedGlue> parsedAtConstructTime;
    private final static String REGEX = "\\{(\\w+)}|\\{(\\*)}";

    public Glue() {
        parsedAtConstructTime = parse();
    }

    public Set<String> getCommands() {
        return parsedAtConstructTime.keySet();
    }

    public GlueReturn glue(String command, GlueParams paramsFromCall, File file, int lineNum) {
        ParsedGlue parsedGlue = parsedAtConstructTime.get(command);
        List<Object> params = constructParams(file, lineNum, paramsFromCall, parsedGlue);
        Method method = parsedGlue.method();
        GlueReturnType glueReturnType = getGlueReturnType(method);

        Object returned;
        try {
            returned = method.invoke(this, params.toArray());
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            PleasantTestException exception = new PleasantTestException(
                    file, lineNum, "Exception when running command '%s', params '%s'", command, params);
            exception.addSuppressed(e);
            throw exception;
        }
        return new GlueReturn(returned, glueReturnType);
    }

    private static List<Object> constructParams(File file, int lineNum, GlueParams paramsFromCall, ParsedGlue parsedGlue) {
        String[] splitPattern = parsedGlue.pattern().split(",");
        splitPattern = Arrays.copyOfRange(splitPattern, 1, splitPattern.length);
        List<Object> params = new ArrayList<>();
        for (String oneStrFromPattern : splitPattern) {
            oneStrFromPattern = oneStrFromPattern.stripLeading();
            Pattern regexPattern = Pattern.compile(REGEX);
            Matcher matcher = regexPattern.matcher(oneStrFromPattern);
            if (matcher.find()) {
                if (!StringUtils.isEmptyOrNull(matcher.group(1))) {
                    params.add(paramsFromCall.getNextString());
                } else if (!StringUtils.isEmptyOrNull(matcher.group(2))) {
                    params.add(paramsFromCall.getTheRestAsList());
                }
            } else {
                paramsFromCall.assertNextStringIsEqualTo(oneStrFromPattern, file, lineNum);
            }
        }
        return params;
    }

    private static GlueReturnType getGlueReturnType(Method method) {
        GlueReturnType glueReturnType;
        // Cannot implement in a switch in java 17 for some reason
        Class<?> returnType = method.getReturnType();
        if (returnType.equals(String.class)) {
            glueReturnType = GlueReturnType.STRING;
        } else if (returnType.equals(void.class)) {
            glueReturnType = GlueReturnType.PRI_VOID;
        } else if (returnType.equals(int.class)) {
            glueReturnType = GlueReturnType.PRI_INT;
        } else if (returnType.equals(Object.class)) {
            glueReturnType = GlueReturnType.OBJECT;
        } else {
            glueReturnType = GlueReturnType.OTHER;
            System.out.println("WARNING: Received %s".formatted(returnType));
        }
        return glueReturnType;
    }

    private Map<String, ParsedGlue> parse() {
        Map<String, ParsedGlue> result = new HashMap<>();
        for (Method method : getClass().getMethods()) {
            if (method.isAnnotationPresent(GlueAnnotation.class)) {
                GlueAnnotation annotation = method.getAnnotation(GlueAnnotation.class);
                String pattern = annotation.value();
                String command = pattern.substring(0, pattern.indexOf(','));
                result.put(command, new ParsedGlue(method, pattern));
            }
        }
        return result;
    }
}
