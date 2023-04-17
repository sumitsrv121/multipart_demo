package com.test;

public class App {
    private static int ways;
    private static int destI;
    private static int destJ;

    public static void main(String[] args) {
        /*
        {1, 0, 0, 0}
        {1, 1, 0, 1}
        {1, 1, 1, 0}
        {1, 1, 1, 1}

         */
        ways = 0;
        int[][] arr = {{1, 0, 0, 0}, {1, 1, 0, 1}, {1, 1, 1, 0}, {1, 1, 1, 1}};
        destI = arr.length - 1;
        destJ = arr[arr.length - 1].length - 1;
        numberOfWays(arr, 0, 0);
        System.out.println(ways);
    }

    private static void numberOfWays(int[][] arr, int i, int j) {
        if (i == destI && j == destJ) {
            ways++;
            return;
        }
        // move right
        if (j + 1 <= destJ && arr[i][j + 1] != 0) {
            numberOfWays(arr, i, j + 1);
        }
        if (i + 1 <= destI && arr[i + 1][j] != 0) {
            numberOfWays(arr, i + 1, j);
        }
    }
}
