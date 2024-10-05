package org.example;

import com.opencsv.exceptions.CsvException;
import org.example.columns.MappingColumns;
import org.example.utils.CSV2Map;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, CsvException {
        System.out.println("Hello world!");

        CSV2Map<MappingColumns> map = new CSV2Map<>(MappingColumns.class, "mappings/LoginMap.csv");
        List<Map.Entry<MappingColumns, String>> entries = map.readCSV();
        System.out.println(entries);
    }
}