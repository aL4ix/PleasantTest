package org.example.sections;

import com.opencsv.exceptions.CsvException;
import lombok.Getter;
import org.example.columns.MappingColumns;
import org.example.commands.Browser;
import org.example.commands.Glue;
import org.example.tables.Table;
import org.example.utils.ReadCSV;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Module {
    public static final int[] SECTION_START = {0};
    public static final String EXPECTED_SECTION_BEGINNING = "Expected section beginning in line %d";

    private final String name;
    List<List<String>> code;
    List<Table<MappingColumns>> mappings;
    @Getter
    Map<String, Section> sections;
    List<Glue> glues;

    public Module(String path) throws IOException, CsvException {
        code = ReadCSV.readCSVWithoutColumns(path);
        name = new File(path).getName().replace("Page.csv", "");
        mappings = new ArrayList<>();
        Table<MappingColumns> mapping = ReadCSV.readCSVWithEnumColumns(MappingColumns.class, "mappings/"+ name +"Map.csv");
        mappings.add(mapping);
        sections = new LinkedHashMap<>();
        glues = new ArrayList<>();
        glues.add(new Browser());
    }

    public void parse() {
        int lineNum = 0;
        while(lineNum < code.size()) {
            List<String> row = code.get(lineNum);

            lineNum += skipEmptyRows(lineNum, code);

            assertThereIsOnlyTheseInRow(SECTION_START, EXPECTED_SECTION_BEGINNING.formatted(lineNum));
            String sectionName = row.get(0);
            Section section = new Section(sectionName);
            sections.put(sectionName, section);
            lineNum += section.parse(code, ++lineNum);

            lineNum++;
        }

    }

    public void execute(String sectionName) {
        sections.get(sectionName).execute(mappings, glues);
    }

    public static void assertThereIsOnlyTheseInRow(int[] columns, String message) {

    }

    public static int skipEmptyRows(int startingLineNum, List<List<String>> code) {
        int size = 0;

        while(startingLineNum + size < code.size()) {
            List<String> row = code.get(startingLineNum);

            boolean empty = false;
            for (String cell : row) {
                if (!cell.isEmpty()) {
                    empty = true;
                    break;
                }
            }
            if (empty) {
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
