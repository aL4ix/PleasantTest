package org.example.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.example.tables.TableRowImpl;
import org.example.tables.Table;
import org.example.tables.TableRow;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadCSV {
    public static <T extends Enum<T> & ColumnEnum> Table<T> readCSVWithEnumColumns(Class<T> enumClass, String csvFile) throws IOException, CsvException {
        List<TableRow<T>> csvData = new ArrayList<>();
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(csvFile)).build()) {
            String[] header = csvReader.readNext();
            T[] enumConstants = enumClass.getEnumConstants();
            if (header.length != enumConstants.length) {
                throw new CsvException("Number of columns in CSV file does not match number of enum constants");
            }
            for (int i = 0; i < header.length; i++) {
                if (!header[i].equals(enumConstants[i].getColumnName())) {
                    throw new CsvException("Column name in CSV file does not match enum constant: " + header[i] + " vs " + enumConstants[i].getColumnName());
                }
            }
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                TableRow<T> tableRow = new TableRowImpl<>();
                for (int i = 0; i < values.length; i++) {
                    tableRow.setCell(enumConstants[i], values[i]);
                }
                csvData.add(tableRow);
            }

        }
        return new Table<>(csvData);
    }

    public static List<TableRow<String>> readCSVWithColumns(String csvFile) throws IOException, CsvException {
        List<TableRow<String>> csvData = new ArrayList<>();
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(csvFile)).build()) {
            String[] header = csvReader.readNext();

            String[] values;
            while ((values = csvReader.readNext()) != null) {
                TableRow<String> tableRow = new TableRowImpl<>();
                for (int i = 0; i < values.length; i++) {
                    tableRow.setCell(header[i], values[i]);
                }
                csvData.add(tableRow);
            }

        }
        return csvData;
    }

    public static List<List<String>> readCSVWithoutColumns(String csvFile) throws IOException, CsvException {
        List<List<String>> csvData = new ArrayList<>();
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(csvFile)).build()) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                csvData.add(Arrays.asList(values));
            }
        }
        return csvData;
    }
}