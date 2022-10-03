package com.wizard;

import org.paukov.combinatorics3.Generator;

import java.util.*;
import java.util.stream.Collectors;

public class AlibabaPasswordCracker {
    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);

        System.out.println("Please enter the digits to use:");
        String digitStr = keyboard.nextLine();
        List<Integer> digitsArray  = getDigitsArray(digitStr);

        String[] passwords = new String[]{"123", "1111", "13", "143"};

        //First find all subsets of the entered digits.
        List<List<Integer>> subsets = Generator
                .subset(digitsArray)
                .simple()
                .stream()
                .collect(Collectors.<List<Integer>>toList());

        List<List<Integer>> permutationsOverall = new ArrayList<>();

        // Now find all permutations of these digits
        for (List<Integer> subsetIntegers : subsets) {
            List<List<Integer>> permutations = new ArrayList<>();
            if (subsetIntegers.size() > 0) {
                permutations = Generator
                        .permutation(subsetIntegers)
                        .withRepetitions(subsetIntegers.size())
                        .stream()
                        .collect(Collectors.<List<Integer>>toList());
            }

            permutationsOverall.addAll(permutations);
        }


        // Convert the array of integers into a string list
        Set<String> combinations = new HashSet<>();
        for (List<Integer> integerList : permutationsOverall) {
            StringBuilder str = new StringBuilder();
            for (Integer i :  integerList) {
                str.append(i);
            }
            combinations.add(str.toString());
        }

        for (String str :combinations) {
            System.out.println(str);
        }

        // Check if the password is present in all possible combinations of the string
        int matches = 0;
        for (String password: passwords) {
            if (combinations.contains(password)) {
                matches++;
            }
        }

        System.out.println("_______________________________________________");
        System.out.println("Alibaba can  open: " + matches + " boxes");
    }

    private static List<Integer> getDigitsArray(String digits) {
        List<Integer> digitsArray = new ArrayList<>();
        for (int i=0; i< digits.length();i++) {
            if (i==digits.length()-1) {
                digitsArray.add(Integer.parseInt(digits.substring(i)));
            } else {
                digitsArray.add(Integer.parseInt(digits.substring(i, i + 1)));
            }
        }
        return digitsArray;
    }

}
