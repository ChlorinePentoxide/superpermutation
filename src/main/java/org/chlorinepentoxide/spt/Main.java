package org.chlorinepentoxide.spt;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter arguments in the format [slots] [options] [threads] [batch-size]. Please do not use whitespaces in between");
        System.out.print("Command Arguments: ");
        String[] arguments = sc.nextLine().split(" ");

        SuperPermutationEngine spe = new SuperPermutationEngine(Integer.parseInt(arguments[0]), arguments[1]);
        spe.threads = Integer.parseInt(arguments[2]);
        spe.batchSize = Integer.parseInt(arguments[3]);
        spe.start();
    }
}