package org.example.commands;

import lombok.Getter;
import org.example.utils.GlueParams;
import org.example.utils.GlueReturn;
import org.example.utils.GlueReturnedType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Browser implements Glue {
    @Getter
    enum Commands {
        TYPE("Type"), CLICK_ON("Click on");

        private final String displayedName;

        Commands(String displayedName) {
            this.displayedName = displayedName;
        }
    }

    public List<String> getCommands() {
        return Arrays.stream(Commands.values()).map(Commands::getDisplayedName).toList();
    }
    public GlueReturn glue(String command, GlueParams params) {
        Commands enumValue = Arrays.stream(Commands.values()).filter(c -> c.getDisplayedName().equals(command)).findAny().get();
        switch (enumValue) {
            case TYPE -> {
                String value = params.getNextString();
                params.assertString("on");
                String element = params.getNextString();
                type(value, element);
                return new GlueReturn(null, GlueReturnedType.VOID);
            }
            case CLICK_ON -> {
                clickOn(params.getNextString());
                return new GlueReturn(null, GlueReturnedType.VOID);
            }
        }
        return new GlueReturn(null, GlueReturnedType.VOID);
    }

    public static void parse() {
        Browser browser = new Browser();
        for (Method method : Browser.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(MoreGlue.class)) {
                MoreGlue annotation = method.getAnnotation(MoreGlue.class);
                String pattern = annotation.value();
                System.out.println(pattern);
                String[] split = pattern.split(",");
                List<String> params = new ArrayList<>();
                for (String s : split) {
                    s = s.stripLeading();
                    if (s.startsWith("{") && s.endsWith("}")) { // TODO add regex for interpolation
                        params.add("param");
                    }
                }
                System.out.println(params);
                try {
                    method.invoke(browser, params.toArray());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    @MoreGlue("Type, {string}, on, {string}")
    public void type(String value, String element) {
        System.out.println("Type: %s %s".formatted(value, element));
    }
    @MoreGlue("Click on, {string}")
    public void clickOn(String element) {
        System.out.println("Click on: %s".formatted(element));
    }
}
