package com.sumit.multipartdemo.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        /*
        list1 = [1,2,3,4,5], list2 = [3,4,5,6,7]

1. find the common elements from these two lists (O/P -> 3,4,5)
2. Union of those two list (O/P -> 1,2,3,4,5,6,7)

         */

        List<Integer> al1 = List.of(1, 2, 3, 4, 5);
        List<Integer> al2 = List.of(3, 4, 5, 6, 7);

        List<Integer> output1 = al2.stream().filter(e -> al1.contains(e)).collect(Collectors.toList());
        System.out.println(output1);

        List<Integer> al = new ArrayList<>();
        al.addAll(al1);
        al.addAll(al2);
        HashSet<Integer> s = new HashSet<>();
        al.stream().forEach(e -> {
            if (!s.contains(e)) {
                s.add(e);
                System.out.print(e + ",");
            }
        });
    }
}
