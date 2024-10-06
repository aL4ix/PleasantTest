package org.example.sections;

import org.example.columns.MappingColumns;
import org.example.commands.Glue;
import org.example.tables.Table;
import org.example.utils.GlueParams;

import java.util.ArrayList;
import java.util.List;

public class Section {
    private String name;
    List<List<String>> code;

    public Section(String name) {
        this.name = name;
        code = new ArrayList<>();
    }

    public int parse(List<List<String>> moduleCode, int lineNum) {
        while(lineNum < moduleCode.size()) {
            List<String> originalRow = moduleCode.get(lineNum);

            Module.skipEmptyRows(lineNum, moduleCode);

            if (!originalRow.get(0).isEmpty())
                break;

            List<String> row = new ArrayList<>(originalRow);
            row.remove(0);
            code.add(row);

            lineNum++;
        }
        return code.size();
    }

    public void execute(List<Table<MappingColumns>> mappings, List<Glue> glues) {
        for (List<String> rows : code) {
            String command = rows.get(0);
            for (Glue glue : glues) {
                if (glue.getCommands().contains(command)) {
                    glue.glue(command, new GlueParams(rows.subList(1, rows.size())));
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Section{" +
                "name='" + name + '\'' +
                ", code=" + code +
                '}';
    }
}
