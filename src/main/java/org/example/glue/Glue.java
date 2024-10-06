package org.example.glue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Glue {
    private Map<String, ParsedGlue> parsed;

    public Glue() {
        parsed = parse();
    }
    public Set<String> getCommands() {
        return parsed.keySet();
    }
    public GlueReturn glue(String command, GlueParams glueParams) {
        ParsedGlue parsedGlue = parsed.get(command);
        String[] split = parsedGlue.pattern().split(",");
        split = Arrays.copyOfRange(split, 1, split.length);
        List<String> params = new ArrayList<>();
        for (String s : split) {
            s = s.stripLeading();
            if (s.startsWith("{") && s.endsWith("}")) { // TODO add regex for interpolation
                params.add(glueParams.getNextString());
            } else {
                glueParams.assertString(s);
            }
        }
        Method method = parsedGlue.method();

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

        Object returned;
        try {
            returned = method.invoke(this, params.toArray());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return new GlueReturn(returned, glueReturnType);
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
