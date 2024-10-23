package org.example.sections;

import org.example.columns.MappingColumns;
import org.example.glue.Glue;
import org.example.glue.GlueParams;
import org.example.glue.GlueReturn;
import org.example.glue.Memory;
import org.example.tables.Table;
import org.example.utils.PleasantTestException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Section {
    private final String name;
    private final List<List<String>> code;
    private final File file;

    public Section(String name, File file) {
        this.name = name;
        code = new ArrayList<>();
        this.file = file;
    }

    public int parse(List<List<String>> moduleCode, int lineNum) {
        String previousCommand = "";
        while(lineNum < moduleCode.size()) {
            lineNum += Module.skipEmptyRows(lineNum, moduleCode);
            List<String> originalRow = moduleCode.get(lineNum);

            if (!originalRow.get(0).isEmpty())
                break;

            // Remove the first column since sections don't use it
            List<String> row = new ArrayList<>(originalRow);
            row.remove(0);

            // Repeat the previous command if it is empty
            String command = row.get(0);
            if (command.isEmpty()) {
                command = previousCommand;
                row.set(0, command);
            }

            code.add(row);

            lineNum++;
            previousCommand = command;
        }
        if (code.isEmpty()) {
            throw new PleasantTestException(file, lineNum, "Section '%s' has no code", name);
        }
        return code.size();
    }

    public void execute(List<Table<MappingColumns>> mappings, List<Glue> glues, int lineNum, Memory memory) {
        for (List<String> rows : code) {
            String command = rows.get(0);
            List<String> paramsRow = rows.subList(1, rows.size());

            boolean found = false;
            for (Glue glue : glues) {
                if (glue.getCommands().contains(command)) {
                    found = true;
                    GlueReturn glueReturn = glue.glue(command, new GlueParams(paramsRow, memory), file, lineNum);
                }
            }
            if (!found) {
                throw new PleasantTestException(file, lineNum, "Invalid command '%s' in section '%s'", command, name);
            }
            lineNum += 1;
        }
    }

    @Override
    public String toString() {
        return "Section{" +
                "name='" + name + '\'' +
                ", code=" + code +
                ", file=" + file +
                '}';
    }
}
