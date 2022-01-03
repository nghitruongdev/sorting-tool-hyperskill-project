package sorting;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Sorter {

    static List<String> getWords(List<String> lines) {
        List<String> words = new ArrayList<>();
        lines.forEach(line -> words.addAll(new ArrayList<>(Arrays.asList(line.split("\\s+")))));
        return words;
    }

    static void sortLine(List<String> lines, boolean frequentSort, String outputFile) throws IOException {
        int count = lines.size();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream console = System.out;
        try(PrintStream stream = new PrintStream(baos)){
            System.setOut(stream);
            System.out.println("Total lines: " + count + ".");
            if (frequentSort) {
                Map map = sortMap(getFrequency(lines));
                map.forEach((line, num) -> System.out.println(String.format("%s: %d time(s), %.0f%%", line, num, (((int) num) * 1d / count * 100))));
            } else {
                System.out.println("Sorted data:");
                lines.stream().sorted().forEach(System.out::println);
            }
        }
        System.setOut(console);
        outputResult(baos.toByteArray(), outputFile);
    }

    static void sortWord(List<String> lines, boolean frequentSort, String outputFile) throws IOException {
        List<String> words = getWords(lines);
        int count = words.size();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream console = System.out;
        try(PrintStream stream = new PrintStream(baos)) {
            System.setOut(stream);
            System.out.println("Total words: " + count + ".");
            if (frequentSort) {
                Map map = sortMap(getFrequency(words));
                map.forEach((line, num) -> System.out.println(String.format("%s: %d time(s), %.0f%%", line, num, (((int) num) * 1d / count * 100))));
            } else {
                System.out.print("Sorted data: ");
                words.stream().sorted().forEach(word -> System.out.print(word + " "));
                System.out.println();
            }
        }
        System.setOut(console);
        outputResult(baos.toByteArray(), outputFile);
    }

    static void sortLong(List<String> lines, boolean frequentSort, String outputFile) throws IOException {
        List<Long> list = new ArrayList<>();
        getWords(lines).forEach(word -> {
          try{
              list.add(Long.parseLong(word));
          }catch(NumberFormatException e){
              System.out.println(String.format("\"%s\" is not a long. It will be skipped.", word));
          }

        });
        int count = list.size();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream console = System.out;
        try(PrintStream stream = new PrintStream(baos)) {
            System.setOut(stream);
            System.out.println("Total numbers: " + count + ".");
            if (frequentSort) {
                Map map = sortMap(getFrequency(list));
                map.forEach((item, freq) -> System.out.println(String.format("%d: %d time(s), %.0f%%", item, freq, (((int) freq) * 1d / count * 100))));
            } else {
                System.out.print("Sorted data: ");
                list.stream().sorted().forEach(item -> System.out.print(item + " "));
                System.out.println();
            }
        }
        System.setOut(console);
        outputResult(baos.toByteArray(), outputFile);
    }

    static void mergeSort(Long[] array) {
        int length = array.length;
        if (length < 2) return;

        int middle = length / 2;
        Long[] left = Arrays.copyOfRange(array, 0, middle);
        Long[] right = Arrays.copyOfRange(array, middle, length);

        mergeSort(left);
        mergeSort(right);
        merge(array, left, right);
    }

    private static void merge(Long[] originalArray, Long[] leftHalf, Long[] rightHalf) {
        int left = leftHalf.length, right = rightHalf.length;
        int i = 0, j = 0, k = 0;

        while (i < left && j < right) {
            if (leftHalf[i] <= rightHalf[j]) {
                originalArray[k] = leftHalf[i];
                i++;
            } else {
                originalArray[k] = rightHalf[j];
                j++;
            }
            k++;
        }
        while (i < left) {
            originalArray[k] = leftHalf[i];
            i++;
            k++;
        }
        while (j < right) {
            originalArray[k] = rightHalf[j];
            j++;
            k++;
        }
    }

    private static Map<Object, Integer> getFrequency(List list) {
        Set set = new HashSet(list);
        return (Map<Object, Integer>) set.stream()
                .collect(Collectors.toMap(item -> item, item -> Collections.frequency(list, item)));
    }

    private static Map<Object, Integer> sortMap(Map<Object, Integer> map) {
        return map.entrySet().stream()
                .sorted(((t, t1) -> {
                    int num = t.getValue() - t1.getValue();
                    if (t.getKey() instanceof String) {
                        return num != 0 ? num : t.getKey().toString().compareTo(t1.getKey().toString());
                    }
                    return num != 0 ? num : (int) (((Long) t.getKey()) - ((Long) t1.getKey()));
                }))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    static void outputResult(byte[] data, String fileName) throws IOException {
        if(fileName == null){
            System.out.println(new String(data));
            return;
        }
        try(FileOutputStream stream = new FileOutputStream(fileName)){
            stream.write(data);
        }
    }

    //    private static void insertionSort(Long[] array) {
//        int size = array.length;
//        //suppose array[0] is sorted subarray
//        //start i from 1
//        for (int i = 1; i < size; i++) {
//            long key = array[i];
//            int j = i - 1;
//
//            while (j >= 0 && key < array[j]) {
//                array[j + 1] = array[j];
//                --j;
//            }
//            array[j + 1] = key;
//        }
//    }

}
