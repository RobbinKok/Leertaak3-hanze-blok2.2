import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVGenerator {

    private static final String directory = "export";

    private DB_connect db_connect;
    private int type = 0;

    public CSVGenerator(DB_connect db_connect) {
        this.db_connect = db_connect;
    }


    /**
     * type 0 = hum
     * type 1 = windspeed
     * type 2 = map
     * type 3 = past 7 days
     *
     * @param data
     * @param type
     * @return
     */
    public String getCSV(ArrayList<String> data, int type) throws FileNotFoundException {
        this.type = type;
        int counter = 0;
        List<String[]> dataLines = new ArrayList<>();
        for (String d : data) {
            HashMap<String, String> map = new JSONParser(d).toObject();


            if (counter == 0) {
                String[] head = new String[map.size()];
                int headCounter = 0;
                for (String k : map.keySet()) {
                    head[headCounter] = k;
                    headCounter++;
                }
                dataLines.add(head);
            }
            String[] body = new String[map.size()];
            int bodyCounter = 0;
            for (String m : map.values()) {
                body[bodyCounter] = m;
                bodyCounter++;
            }
            dataLines.add(body);

            counter++;
        }

        return createCSV(dataLines);
    }

    private String createCSV(List<String[]> dataLines) throws FileNotFoundException {
        File f = new File(CSVGenerator.directory);
        if (f.exists() && f.isDirectory()) {
            String name = getName();
            File csvOutputFile = new File(directory + "/" + name + ".CSV");
            try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
                dataLines.stream()
                        .map(this::convertToCSV)
                        .forEach(pw::println);
            }
            System.out.println();


            return csvOutputFile.getAbsolutePath();
        }
        return "";
    }

    private String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    private String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }

        if (escapedData.startsWith(" ")) {
            escapedData = escapedData.substring(1);
        }
        return escapedData;
    }

    private String getName() {
        String name = "export_";
        switch (type) {
            case 0:
                name += "humidity";
                break;
            case 1:
                name += "windspeed";
                break;
            case 2:
                name += "map";
                break;
            case 3:
                name += "czech_republic_past_7_days";
                break;
            default:
                break;
        }

        name += "_";
        name += new Date().toString().toLowerCase(Locale.ROOT).replace(" ", "_");
        return name;
    }
}
