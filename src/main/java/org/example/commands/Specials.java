package org.example.commands;

import org.example.glue.Glue;
import org.example.glue.GlueAnnotation;
import org.example.glue.Memory;
import org.example.utils.PleasantTestException;

import java.io.File;
import java.util.List;

@SuppressWarnings("unused")
public class Specials extends Glue {
    private final File file;
    private final Memory memory;
    public Specials(File file, Memory memory) {
        this.file = file;
        this.memory = memory;
    }

    @GlueAnnotation("Step, {string}, {string}")
    public void step(String step, String expected) {
        System.out.println("Step, %s, %s".formatted(step, expected));
    }
    @GlueAnnotation("[Arguments], {*}")
    public void arguments(List<String> args) {
        System.out.println("[Arguments], %s".formatted(args));
        for (String arg : args) {
            String[] split = arg.split("=");
            if (split.length > 2) {
                throw new PleasantTestException(file, 0, "Initialization has more than one equals symbol", arg);
            }
            if (split.length == 2) {
                String key = split[0];
                key = key.substring(1, key.length()-1);
                String value = split[1];
                memory.set(key, value);
                System.out.println("Memory: '%s'='%s'".formatted(key, value));
            }
        }
    }
    @GlueAnnotation("Expect, {*}")
    public void expect(List<String> args) {
        System.out.println("Expect, %s".formatted(args));
    }
}
