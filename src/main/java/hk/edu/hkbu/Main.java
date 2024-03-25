package hk.edu.hkbu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Main {
    public static void main(String[] args) {

        // String inputFile = "/Users/peter/Downloads/fraudTrain.csv";
        // String outputFile = "/Users/peter/Downloads/fraudTrainData.arff";
        String inputFile = "/Users/peter/Downloads/fraudTest.csv";
        String outputFile = "/Users/peter/Downloads/fraudTestData.arff";

        try {
            // Read the input file
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line;
            StringBuilder output = new StringBuilder();

            String header = getHeader();
            output.append(header);

            int lineCount = 0;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                if (lineCount == 1) {
                    continue;
                }

                // Perform conversions and filtering
                String convertedLine = convertAndFilter(line);
                output.append(convertedLine).append("\n");
            }

            reader.close();

            // Write to the output file
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            writer.write(output.toString());
            writer.close();

            System.out.println("File conversion completed successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static String convertAndFilter(String line) {

        StringBuilder newLine = new StringBuilder();
        String[] data = parseCSVLine(line);


        for (int i = 0; i <= 22; i++) {
            if (i == 0) {
                continue;
            }

            String field = data[i];

            // data[1] - trans_date_trans_time - unix_time
            // data[2] - cc_num - hashCode
            // data[3] - merchant - hashCode
            // data[4] - category - hashCode
            // data[5] - amt - no change
            // data[6] - first - hashCode
            // data[7] - last - hashCode
            // data[8] - gender - no change.
            // data[9] - street - hashCode
            // data[10] - city - hashCode
            // data[11] - state - special handling
            // data[12] - zip - no change
            // data[13] - lat - no change
            // data[14] - long - no change
            // data[15] - city_pop - no change
            // data[16] - job - hashCode
            // data[17] - dob - age
            // data[18] - trans_num - hashCode
            // data[19] - unix_time - hashCode
            // data[20] - merch_lat - no change
            // data[21] - merch_long - no change
            // data[22] - is_fault - no change

            // data[1] - trans_date_trans_time
            if (i == 1) {
                newLine.append(strToUnixTime(field));
            }

            // data[11] - state - special handling
            if (i == 11) {
                newLine.append(convertState(field));
            }

            // data[17] - dob - age
            if (i == 17) {
                newLine.append(getAge(field));
            }

            // For the hashCode column, output the string's hashCode.
            if (i == 2 ||
                    i == 3 ||
                    i == 4 ||
                    i == 6 ||
                    i == 7 ||
                    i == 9 ||
                    i == 10 ||
                    i == 16 ||
                    i == 18 ||
                    i == 19) {
                newLine.append(field.hashCode());
            }

            // For the no change column, output the original value.
            if (i == 5 ||
                    i == 8 ||
                    i == 12 ||
                    i == 13 ||
                    i == 14 ||
                    i == 15 ||
                    i == 20 ||
                    i == 21 ||
                    i == 22) {
                newLine.append(field);
            }

            if (i != 22) {
                newLine.append(",");
            }
        }

        return newLine.toString();
    }

    private static long strToUnixTime(String dateStr) {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
            Date date = dateFormat.parse(dateStr);
            return date.getTime() / 1000; // Convert milliseconds to seconds
        } catch (ParseException e) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = dateFormat.parse(dateStr);
                return date.getTime() / 1000; // Convert milliseconds to seconds
            } catch (ParseException e2) {
                System.out.println("strToUnixTime - " + dateStr);
            }
        }

        return 0;
    }

    private static int getAge(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate birthDate = LocalDate.parse(dateStr, formatter);
            LocalDate currentDate = LocalDate.now();
            Period age = Period.between(birthDate, currentDate);
            return age.getYears();
        } catch (Exception ignored) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate birthDate = LocalDate.parse(dateStr, formatter);
                LocalDate currentDate = LocalDate.now();
                Period age = Period.between(birthDate, currentDate);
                return age.getYears();
            } catch (Exception ignored2) {
                System.out.println("getAge - " + dateStr);
            }
            return 0;
        }
    }

    private static String convertState(String field) {

        if (field.length() != 2) {
            System.out.println(" a two-letter string.");
            return "0";
        }

        char firstLetter = field.charAt(0);
        char secondLetter = field.charAt(1);
        int result = (firstLetter - 'A' + 1) * 26 + (secondLetter - 'A' + 1);
        return result + "";
    }

    private static String getHeader() {
        return """
@relation fraudTranDetect
@attribute 'trans_date_trans_time' numeric
@attribute 'cc_num' numeric
@attribute 'merchant' numeric
@attribute 'category' numeric
@attribute 'amt' numeric
@attribute 'first' numeric
@attribute 'last' numeric
@attribute 'gender' {F, M}
@attribute 'street' numeric
@attribute 'city' numeric
@attribute 'state' numeric
@attribute 'zip' numeric
@attribute 'lat' numeric
@attribute 'long' numeric
@attribute 'city_pop' numeric
@attribute 'job' numeric
@attribute 'dob' numeric
@attribute 'trans_num' numeric
@attribute 'unix_time' numeric
@attribute 'merch_lat' numeric
@attribute 'merch_long' numeric
@attribute class-att {0, 1}
@data\n""";

    }

    private static String[] parseCSVLine(String line) {
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        String[] data = new String[0];

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                data = appendValue(data, sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }

        data = appendValue(data, sb.toString());

        return data;

    }

    private static String[] appendValue(String[] data, String value) {
        String[] newData = new String[data.length + 1];
        System.arraycopy(data, 0, newData, 0, data.length);
        newData[data.length] = value;
        return newData;
    }
}