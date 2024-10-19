package org.example.commands;

import org.example.glue.Glue;
import org.example.glue.GlueAnnotation;

import java.util.List;

public class Builtin extends Glue {
    @GlueAnnotation("Steps, {string}, {string}")
    public void steps(String step, String expected) {
        System.out.println("Steps, %s, %s".formatted(step, expected));
    }
    @GlueAnnotation("[Arguments], {*}")
    public void arguments(List<String> args) {
        System.out.println("[Arguments], %s".formatted(args));
    }
    @GlueAnnotation("Expect, {*}")
    public void expect(List<String> args) {
        System.out.println("Expect, %s".formatted(args));
    }
}
