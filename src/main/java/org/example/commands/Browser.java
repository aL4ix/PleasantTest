package org.example.commands;

import org.example.glue.GlueAnnotation;
import org.example.glue.Glue;

public class Browser extends Glue {
    @GlueAnnotation("Type, {string}, on, {string}")
    public void type(String value, String element) {
        System.out.println("Type: %s on %s".formatted(value, element));
    }
    @GlueAnnotation("Click on, {string}")
    public void clickOn(String element) {
        System.out.println("Click on: %s".formatted(element));
    }
}
