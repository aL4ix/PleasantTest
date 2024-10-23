package org.example.sections;

import com.opencsv.exceptions.CsvException;
import lombok.Getter;
import org.example.columns.MappingColumns;
import org.example.commands.Browser;
import org.example.commands.Specials;
import org.example.glue.Glue;
import org.example.glue.Memory;
import org.example.tables.Table;
import org.example.utils.PleasantTestException;
import org.example.utils.ReadCSV;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Module {
    private static final int[] SECTION_START_PATTERN = {0};
    private static final String ERROR_EXPECTED_SECTION_BEGINNING = "Expected section beginning";

    private final File file;
    private final String name;
    private final List<List<String>> code;
    private final List<Table<MappingColumns>> mappings;
    @Getter
    private final Map<String, Section> sections;
    private final List<Glue> glues;
    private final Memory memory;

    public Module(String path) throws IOException, CsvException {
        code = ReadCSV.readCSVWithoutColumns(path);
        file = new File(path);
        String fileName = file.getName();
        mappings = new ArrayList<>();
        if (fileName.contains("pages/")) {
            name = fileName.replace("Page.csv", "");
            Table<MappingColumns> mapping = ReadCSV.readCSVWithEnumColumns(MappingColumns.class, "mappings/" + name + "Map.csv");
            mappings.add(mapping);
        } else {
            name = fileName.replace(".csv", "");
        }
        sections = new LinkedHashMap<>();
        glues = new ArrayList<>();
        glues.add(new Browser());
        memory = new Memory();
        glues.add(new Specials(file, memory));
    }

    public void parse() {
        int lineNum = 0;
        while (lineNum < code.size()) {
            lineNum += skipEmptyRows(lineNum, code);
            assertTheseExistInTheRow(file, lineNum, SECTION_START_PATTERN, ERROR_EXPECTED_SECTION_BEGINNING);
            List<String> row = code.get(lineNum);
            String sectionName = row.get(0);
            Section section = new Section(sectionName, file);
            sections.put(sectionName, section);
            lineNum += section.parse(code, ++lineNum);

            lineNum++;
        }
    }

    public void execute(String sectionName, int lineNum) {
        sections.get(sectionName).execute(mappings, glues, lineNum, memory);
    }

    public void assertTheseExistInTheRow(File file, int lineNum, int[] columns, String message) {
        List<String> row = code.get(lineNum);
        for (int column : columns) {
            String cell = row.get(column);
            if (cell == null || cell.isBlank()) {
                throw new PleasantTestException(file, lineNum, message);
            }
        }
    }

    public static int skipEmptyRows(int startingLineNum, List<List<String>> code) {
        int size = 0;

        while (startingLineNum + size < code.size()) {
            List<String> row = code.get(startingLineNum);

            boolean hasSomething = false;
            for (String cell : row) {
                if (!cell.isEmpty()) {
                    hasSomething = true;
                    break;
                }
            }
            if (hasSomething) {
                break;
            }

            System.out.println("Skipping line %d %s".formatted(startingLineNum, row));

            size++;
        }
        return size;
    }

    @Override
    public String toString() {
        return "Module{" +
                "name='" + name + '\'' +
                ", code=" + code +
                ", mappings=" + mappings +
                ", sections=" + sections +
                ", commandGlues=" + glues +
                '}';
    }
}
