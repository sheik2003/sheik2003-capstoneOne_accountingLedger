package com.ps;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Transaction> transactions = new ArrayList<>();

    //main
    public static void main(String[] args) {


        loadTransactions();

        int homeScreenCommand;


        do {
            System.out.println("Welcome to the home screen");
            System.out.println("1) Add Deposit");
            System.out.println("2) Make Payment");
            System.out.println("3) Ledger");
            System.out.println("0) Exit");
            System.out.println("What would you like to do? ");
            homeScreenCommand = scanner.nextInt();
            scanner.nextLine();

            switch (homeScreenCommand){
                case 1:
                    addDeposit();
                    break;
                case 2:
                    makePayment();
                    System.out.println(transactions);
                    break;
                case 3:
                    displayLedger();
                    break;
                case 0:
                    System.out.println("Exiting");
                    break;
                default:
                    System.out.println("Wrong command entered please retry again with a valid choice ");

            }

        }
        while (homeScreenCommand != 0);
    }

    private static void displayLedger() {
    }

    private static void makePayment() {

        //date
        System.out.println("Please enter the date of the Payment(format yyyy-mm-dd): ");
        String paymentDateInString = scanner.nextLine();
        LocalDate paymentDate = LocalDate.parse(paymentDateInString);

        //time
        System.out.println("Please enter the time of the Payment(format h:m:s): ");
        String paymentTimeInString = scanner.nextLine();
        LocalTime paymentTime =  LocalTime.parse(paymentTimeInString);

        //description
        System.out.println("Please enter the description of the payment: ");
        String paymentDescription = scanner.nextLine();

        //vendor
        System.out.println("Please enter the name of the Vendor: ");
        String paymentVendor = scanner.nextLine();


        //amount
        System.out.println("Please enter the payment amount: ");
        double paymentAmount = scanner.nextDouble();


        transactions.add(new Transaction(paymentDate,paymentTime,paymentDescription,paymentVendor,paymentAmount));



        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("transactions.csv",true));
            String formattedTransaction = String.format("%s|%s|%s|%s|%.2f\n", paymentDate, paymentTime, paymentDescription, paymentVendor, paymentAmount);
            bufferedWriter.write(formattedTransaction);
            bufferedWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private static void addDeposit() {
    }

    private static void loadTransactions() {

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"));

            String input;

            while ((input = bufferedReader.readLine()) != null){
                String[] fields = input.split("\\|");

                LocalDate date = LocalDate.parse(fields[0]);
                LocalTime time = LocalTime.parse(fields[1]);
                String description = fields[2];
                String vendor = fields[3];
                double amount = Double.parseDouble(fields[4]);

                Transaction transaction = new Transaction(date,time,description,vendor,amount);

                transactions.add(transaction);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}

