package org.example.commands;

import org.example.utils.GlueParams;
import org.example.utils.GlueReturn;

import java.util.List;

public interface Glue {
    List<String> getCommands();
    GlueReturn glue(String command, GlueParams params);
}
