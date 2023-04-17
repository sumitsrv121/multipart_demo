package com.test;

public class Application {
    public static void main(String[] args) {
        //  ["((()))","(()())","(())()","()(())","()()()"]
        // (
        // () ((
        int n = 10;
        generateParenthesis(n - 1, n, "(");
    }

    private static void generateParenthesis(int left, int right, String output) {
        if (right == 0 && left != 0) {
            return;
        }
        if (right < left) {
            return;
        }
        if (left == 0 && right == 0) {
            System.out.println(output);
            return;
        }
        if (left > 0) {
            generateParenthesis(left - 1, right, output + "(");
            generateParenthesis(left, right - 1, output + ")");
        } else if (right > 0) {
            generateParenthesis(left, right - 1, output + ")");
        }

    }
}
