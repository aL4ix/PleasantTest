package org.example.sections;

import org.example.columns.MappingColumns;
import org.example.glue.Glue;
import org.example.glue.GlueParams;
import org.example.glue.GlueReturn;
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
        while(lineNum < moduleCode.size()) {
            lineNum += Module.skipEmptyRows(lineNum, moduleCode);
            List<String> originalRow = moduleCode.get(lineNum);

            if (!originalRow.get(0).isEmpty())
                break;

            List<String> row = new ArrayList<>(originalRow);
            row.remove(0);
            code.add(row);

            lineNum++;
        }
        if (code.isEmpty()) {
            throw new PleasantTestException(file, lineNum, "Section '%s' has no code", name);
        }
        return code.size();
    }

    public void execute(List<Table<MappingColumns>> mappings, List<Glue> glues, int lineNum) {
        String previousCommand = "";
        for (List<String> rows : code) {
            String command = rows.get(0);
            if (command.isEmpty()) {
                command = previousCommand;
            }
            boolean found = false;
            for (Glue glue : glues) {
                if (glue.getCommands().contains(command)) {
                    found = true;
                    GlueReturn glueReturn = glue.glue(command, new GlueParams(rows.subList(1, rows.size())), file, lineNum);
                }
            }
            if (!found) {
                throw new PleasantTestException(file, lineNum, "Invalid command '%s' in section '%s'", command, name);
            }
            lineNum += 1;
            previousCommand = command;
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
