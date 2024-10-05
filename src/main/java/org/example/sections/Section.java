package org.example.sections;

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

    @Override
    public String toString() {
        return "Section{" +
                "name='" + name + '\'' +
                ", code=" + code +
                '}';
    }
}
