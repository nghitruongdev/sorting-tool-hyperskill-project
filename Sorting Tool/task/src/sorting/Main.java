package sorting;

import java.io.*;
import java.util.*;

import static sorting.Sorter.*;


public class Main {
    public static void main(final String[] args) throws IOException {
        Map<String, String> map = getArgValue(args);
        String sortType = map.get("-sortingType");
        String dataType = map.get("-dataType");
        String inputFile = map.get("-inputFile");
        String outputFile = map.get("-outputFile");
        List<String> list = getInput(inputFile);

        boolean isFrequentSort = sortType.equals("byCount");
        switch (dataType) {
            case "line":
                sortLine(list, isFrequentSort, outputFile);
                break;
            case "long":
                sortLong(list, isFrequentSort, outputFile);
                break;
            default:
                sortWord(list, isFrequentSort, outputFile);
                break;
        }
//        sortLine(new ArrayList<String>(Arrays.asList(new String[]{"Hello ae", "Abc", "aBc", "bcd"})));
    }

    private static Map<String, String> getArgValue(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("-sortingType", "natural");
        map.put("-dataType", "word");
        map.put("-inputFile", null);
        map.put("-outputFile", null);
        for (int i = 0; i < args.length; i++) {
            try {
                if (args[i].startsWith("-")) {
                    if (map.containsKey(args[i])) map.put(args[i], args[i + 1]);
                    else throw new IllegalArgumentException();
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                if (args[i].equals("-sortingType")) System.out.println("No sorting type defined!");
                if (args[i].equals("-dataType")) System.out.println("No data type defined!");
                System.exit(0);
            } catch (IllegalArgumentException e) {
                System.out.println(String.format("\"%s\" ís not a valid parameter. It will be skipped.", args[i]));
            }
        }
        return map;
    }

    private static List<String> getInput(String inputFile) throws IOException {
        List<String> list = new ArrayList<>();
        if(inputFile != null) System.setIn(new FileInputStream(inputFile));
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String s;
            while ((s = reader.readLine()) != null) {
                list.add(s);
            }
        }
        return list;
    }


}

