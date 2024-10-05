package org.example.commands;

import lombok.Getter;
import org.example.utils.GlueReturn;
import org.example.utils.GlueReturnedType;
import org.example.utils.GlueParams;

import java.util.Arrays;
import java.util.List;

public class Browser {
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
        Commands enumValue = Commands.valueOf(command);
        switch (enumValue) {
            case TYPE -> {
                type(params.getNextString(), params.getNextString());
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
        System.out.println("Returned: %s %s".formatted(value, element));
    }
    public void clickOn(String element) {
        System.out.println("Returned: %s".formatted(element));
    }
}
