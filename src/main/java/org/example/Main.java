package org.example;

import com.opencsv.exceptions.CsvException;
import org.example.columns.MappingColumns;
import org.example.sections.Module;
import org.example.tables.Table;
import org.example.tables.TableRow;
import org.example.utils.ReadCSV;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, CsvException {
        System.out.println("Hello world!");

        Table<MappingColumns> mapEntries = ReadCSV.readCSVWithEnumColumns(MappingColumns.class, "mappings/LoginMap.csv");
        System.out.println(mapEntries);

        List<TableRow<String>> envEntries = ReadCSV.readCSVWithColumns("environments.csv");
        System.out.println(envEntries);

        List<List<String>> page = ReadCSV.readCSVWithoutColumns("pages/LoginPage.csv");
        System.out.println(page);

        Module module = new Module("pages/LoginPage.csv");
        System.out.println(module);
        module.parse();
        System.out.println(module.getSections());
        module.execute("Login user");
    }
}