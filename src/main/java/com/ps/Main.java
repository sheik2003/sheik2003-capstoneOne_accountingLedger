package com.ps;

import java.io.*;
import java.time.*;
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

    private static void addDeposit() {

        System.out.println("Please enter the date of the Deposit(format yyyy-mm-dd): ");
        String depositDateInString = scanner.nextLine();
        LocalDate depositDate = LocalDate.parse(depositDateInString);

        System.out.println("Please enter the time of the Deposit(format hh:mm:ss): ");
        String depositTimeInString = scanner.nextLine();
        LocalTime depositTime =  LocalTime.parse(depositTimeInString);

        System.out.println("Please enter the description of the Deposit: ");
        String depositDescription = scanner.nextLine();

        System.out.println("Please enter the name of the Vendor: ");
        String depositVendor = scanner.nextLine();

        System.out.println("Please enter the payment amount: ");
        double depositAmount = scanner.nextDouble();


        transactions.add(new Transaction(depositDate,depositTime,depositDescription,depositVendor,depositAmount));

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("transactions.csv",true));
            String formattedTransaction = String.format("%s|%s|%s|%s|%.2f", depositDate,depositTime,depositDescription,depositVendor,depositAmount);
            bufferedWriter.write(formattedTransaction);
            System.out.println("✅ Deposit added successfully!");
            bufferedWriter.newLine();
            bufferedWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void makePayment() {
        System.out.println("Please enter the date of the Payment(format yyyy-mm-dd): ");
        String paymentDateInString = scanner.nextLine();
        LocalDate paymentDate = LocalDate.parse(paymentDateInString);

        System.out.println("Please enter the time of the Payment(format hh:mm:ss): ");
        String paymentTimeInString = scanner.nextLine();
        LocalTime paymentTime =  LocalTime.parse(paymentTimeInString);

        System.out.println("Please enter the description of the payment: ");
        String paymentDescription = scanner.nextLine();

        System.out.println("Please enter the name of the Vendor: ");
        String paymentVendor = scanner.nextLine();

        System.out.println("Please enter the payment amount: ");
        double userPaymentAmount = scanner.nextDouble();
        double convertedUserPaymentAmount = userPaymentAmount * -1;

        transactions.add(new Transaction(paymentDate,paymentTime,paymentDescription,paymentVendor,convertedUserPaymentAmount));

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("transactions.csv",true));
            String formattedTransaction = String.format("%s|%s|%s|%s|%.2f", paymentDate, paymentTime, paymentDescription, paymentVendor, convertedUserPaymentAmount);
            bufferedWriter.write(formattedTransaction);
            System.out.println("✅ Payment added successfully!");
            bufferedWriter.newLine();
            bufferedWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    private static void displayLedger() {

        int ledgerCallInput;
        do {
            System.out.println("Welcome to the ledger");
            System.out.println("1)ALl Tranactions");
            System.out.println("2)Deposit");
            System.out.println("3)Payments");
            System.out.println("4)Reports");
            System.out.println("0)Go back to the HomesScreen ");
            ledgerCallInput = scanner.nextInt();
            scanner.nextLine();

            switch (ledgerCallInput){
                case 1:
                    displayAll();
                    break;
                case 2:
                    displayDeposits();
                    break;
                case 3:
                    displayPayments();
                    break;
                case 4:
                    handleReportCall();
                    break;
                case 5:
                    searchByVendor();
                    break;
                case 0:
                    System.out.println("Going back to the homeScreen...");
                    break;
                default:
                    System.out.println("Wrong Choice entered please try again");
            }

        }while (ledgerCallInput != 0);

    }


    private static void displayAll(){

        System.out.println("Transactions format: Date | Time | Description | Vendor | Amount");
        for (Transaction transaction : transactions){
        System.out.printf("%s | %s | %s | %s | %.2f%n", transaction.getDate(),transaction.getTime(),transaction.getDescription(),transaction.getVendor(),transaction.getAmount());

    }
    }

    private static void handleReportCall(){
        int reportCallInput;

        do {
            System.out.println("1)Display Month to date");
            System.out.println("2)Display Previous Month");
            System.out.println("3)Display Year to date");
            System.out.println("4)Display Previous Year");
            System.out.println("5)Search by Vendor");
            System.out.println("0)Go back to Ledger Screen");

            reportCallInput = scanner.nextInt();
            scanner.nextLine();

            switch (reportCallInput){
                case 1:
                    displayMonthToDate();
                    break;
                case 2:
                    displayPreviousMonth();
                    break;
                case 3:
                    displayYearToDate();
                    break;
                case 4:
                    displayPreviousYear();
                    break;
                case 5:
                    searchByVendor();
                    break;
                case 0:
                    System.out.println("Going back to Ledger page...");
                    break;
                default:
                    System.out.println("Wrong choice please try again");
            }
        }while (reportCallInput != 0);

    }

    private static void displayDeposits(){

        System.out.println("Transactions format: Date | Time | Description | Vendor | Amount");

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0){
                System.out.printf("%s | %s | %s | %s | %.2f%n", transaction.getDate(),transaction.getTime(),transaction.getDescription(),transaction.getVendor(),transaction.getAmount());
            }

        }
    }


    private static void displayPayments(){

        System.out.println("Transactions format: Date | Time | Description | Vendor | Amount");

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0){
                System.out.printf("%s | %s | %s | %s | %.2f%n", transaction.getDate(),transaction.getTime(),transaction.getDescription(),transaction.getVendor(),transaction.getAmount());
            }

        }
        }

        private static void displayMonthToDate() {

            LocalDate timeNow = LocalDate.now();

            for (Transaction transaction : transactions) {
                if (transaction.getDate().getMonth() == timeNow.getMonth() &&
                        transaction.getDate().getYear() == timeNow.getYear()) {
                    System.out.printf("%s | %s | %s | %s | %.2f%n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
                }
            }
        }

    private static void displayPreviousMonth() {

        LocalDate timeNow = LocalDate.now();
        LocalDate previousMonth = timeNow.minusMonths(1);

        for (Transaction transaction : transactions) {
            if (transaction.getDate().getMonth() == previousMonth.getMonth() &&
                    transaction.getDate().getYear() == previousMonth.getYear()) {
                System.out.printf("%s | %s | %s | %s | %.2f%n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
            }
        }
    }

    private static void displayYearToDate() {

        LocalDate timeNow = LocalDate.now();

        for (Transaction transaction : transactions) {
            if (transaction.getDate().getYear() == timeNow.getYear()) {
                System.out.printf("%s | %s | %s | %s | %.2f%n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
            }
        }
    }

    private static void displayPreviousYear() {

        LocalDate timeNow = LocalDate.now();
        LocalDate previousYear = timeNow.minusYears(1);

        for (Transaction transaction : transactions) {
            if (transaction.getDate().getYear() == previousYear.getYear()) {
                System.out.printf("%s | %s | %s | %s | %.2f%n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
            }
        }
    }

    private static void searchByVendor() {

        System.out.println("Please enter the name of the Vendor to filter search: ");
        String userVendorFilter = scanner.nextLine();
        for (Transaction transaction : transactions) {
            if (userVendorFilter.equalsIgnoreCase(transaction.getVendor())) {
                System.out.printf("%s | %s | %s | %s | %.2f%n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());

            }
        }
    }

    private static void customSearch(){
        System.out.println("Enter start date: ");

        for (Transaction transaction : transactions){

        }
    }


}

