package org.example.sections;

import com.opencsv.exceptions.CsvException;
import lombok.Getter;
import org.example.columns.MappingColumns;
import org.example.tables.Table;
import org.example.utils.ReadCSV;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Module {
    public static final int[] SECTION_START = {0};
    public static final String EXPECTED_SECTION_BEGINNING = "Expected section beginning in line %d";

    private final String name;
    List<List<String>> code;
    Table<MappingColumns> mapping;
    @Getter
    List<Section> sections;

    public Module(String path) throws IOException, CsvException {
        code = ReadCSV.readCSVWithoutColumns(path);
        name = new File(path).getName().replace("Page.csv", "");
        mapping = ReadCSV.readCSVWithEnumColumns(MappingColumns.class, "mappings/"+ name +"Map.csv");
        sections = new ArrayList<>();
    }

    public void parse() {
        int lineNum = 0;
        while(lineNum < code.size()) {
            List<String> row = code.get(lineNum);

            lineNum += skipEmptyRows(lineNum, code);

            assertThereIsOnlyTheseInRow(SECTION_START, EXPECTED_SECTION_BEGINNING.formatted(lineNum));
            String sectionName = row.get(0);
            Section section = new Section(sectionName);
            sections.add(section);
            lineNum += section.parse(code, ++lineNum);

            lineNum++;
        }

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
                ", mapping=" + mapping +
                ", sections=" + sections +
                '}';
    }
}
