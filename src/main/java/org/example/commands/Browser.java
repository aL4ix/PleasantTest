package org.example.commands;

import lombok.Getter;
import org.example.utils.GlueParams;
import org.example.utils.GlueReturn;
import org.example.utils.GlueReturnedType;

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
    public void type(String value, String element) {
        System.out.println("Type: %s %s".formatted(value, element));
    }
    public void clickOn(String element) {
        System.out.println("Click on: %s".formatted(element));
    }
}
