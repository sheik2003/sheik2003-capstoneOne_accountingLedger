package com.ps;

import java.io.*;
import java.time.*;
import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Transaction> transactions = new ArrayList<>();

    //main
    public static void main(String[] args) {

        //make a report class and make it static to save space
        //hash map for vendor summarry
        //name of vendor as key
        //if set, add the total balance
        //if doesnt exit sets to it
        //list through all the keys
        //get all the keys to retrieve the value
        //.entry
        //setting them is very efficent


//refix variable name to look nicer
        //put in differnet files to make this file less
        //create diff classes , one or two more classes don't over do it
        //look into utility classes

        loadTransactions();
        int homeScreenCommand;
        displayHomeScreenAsciiArt();

        do {
            System.out.println("\n+------------------------ 🏠 Home Screen Menu ------------------------+");
            System.out.println("  | [1] 💰 Add Deposit                                                |");
            System.out.println("  | [2] 🧾 Make Payment                                               |");
            System.out.println("  | [3] 📘 Ledger                                                     |");
            System.out.println("  | [4] 📊 Account Summary                                            |");
            System.out.println("  | [0] ❌ Exit                                                       |");
            System.out.println("+------------------------------------------------------------------------+");

            System.out.print("🔢 What would you like to do? ");

            try {
                homeScreenCommand = scanner.nextInt();
                scanner.nextLine();
            }catch (InputMismatchException e){
                System.out.println("❌ Invalid input. Please enter a number from the menu.");
                scanner.nextLine();
                homeScreenCommand = -1;

            }


            switch (homeScreenCommand) {
                case 1:
                    addDeposit();
                    break;
                case 2:
                    makePayment();
                    break;
                case 3:
                    displayLedger();
                    break;
                case 4:
                    accountSummary();
                    break;
                case 0:
                    System.out.println("Exiting");
                    break;
                default:
                    System.out.println("Invalid Input Please Try Again With A Different Choice ");

            }
        }
        while (homeScreenCommand != 0);
    }



    private static void loadTransactions() {

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"));

            String input;

            while ((input = bufferedReader.readLine()) != null) {
                String[] fields = input.split("\\|");

                LocalDate date = LocalDate.parse(fields[0]);
                LocalTime time = LocalTime.parse(fields[1]);
                String description = fields[2];
                String vendor = fields[3];
                double amount = Double.parseDouble(fields[4]);

                Transaction transaction = new Transaction(date, time, description, vendor, amount);

                transactions.add(transaction);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void addDeposit() {

        LocalDate depositDate = null;
        while (depositDate == null) {
            try {
                System.out.println("Please enter the date of the Deposit (format yyyy-mm-dd): ");
                String depositDateInString = scanner.nextLine().trim();
                depositDate = LocalDate.parse(depositDateInString);
            } catch (Exception e) {
                System.out.println("❌ Invalid date format. Please use yyyy-mm-dd.");
            }
        }

        LocalTime depositTime = null;
        while (depositTime == null) {
            try {
                System.out.println("Please enter the time of the Deposit (format hh:mm:ss): ");
                String depositTimeInString = scanner.nextLine().trim();
                depositTime = LocalTime.parse(depositTimeInString);
            } catch (Exception e) {
                System.out.println("❌ Invalid time format. Please use hh:mm:ss.");
            }
        }

        System.out.println("Please enter the description of the Deposit: ");
        String depositDescription = scanner.nextLine().trim();

        System.out.println("Please enter the name of the Vendor: ");
        String depositVendor = scanner.nextLine().trim();

        System.out.println("Please enter the payment amount: ");
        double depositAmount = scanner.nextDouble();


        transactions.add(new Transaction(depositDate, depositTime, depositDescription, depositVendor, depositAmount));

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("transactions.csv", true));
            String formattedTransaction = String.format("%s|%s|%s|%s|%.2f", depositDate, depositTime, depositDescription, depositVendor, depositAmount);
            bufferedWriter.write(formattedTransaction);
            System.out.println("✅ Deposit added successfully!");
            System.out.println("====================================");
            bufferedWriter.newLine();
            bufferedWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void makePayment() {
        System.out.println("========== 💸 Make Payment ==========");

        LocalDate paymentDate = null;
        while (paymentDate == null) {
            try {
                System.out.println("Please enter the date of the Payment (format yyyy-mm-dd): ");
                String paymentDateInString = scanner.nextLine().trim();
                paymentDate = LocalDate.parse(paymentDateInString);
            } catch (Exception e) {
                System.out.println("❌ Invalid date format. Please use yyyy-mm-dd.");
            }
        }

        LocalTime paymentTime = null;
        while (paymentTime == null) {
            try {
                System.out.println("Please enter the time of the Payment (format hh:mm:ss): ");
                String paymentTimeInString = scanner.nextLine().trim();
                paymentTime = LocalTime.parse(paymentTimeInString);
            } catch (Exception e) {
                System.out.println("❌ Invalid time format. Please use hh:mm:ss.");
            }
        }


        System.out.println("Please enter the description of the payment: ");
        String paymentDescription = scanner.nextLine().trim();

        System.out.println("Please enter the name of the Vendor: ");
        String paymentVendor = scanner.nextLine().trim();

        System.out.println("Please enter the payment amount: ");
        double userPaymentAmount = scanner.nextDouble();
        double convertedUserPaymentAmount = userPaymentAmount * -1;

        transactions.add(new Transaction(paymentDate, paymentTime, paymentDescription, paymentVendor, convertedUserPaymentAmount));

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("transactions.csv", true));
            String formattedTransaction = String.format("%s|%s|%s|%s|%.2f", paymentDate, paymentTime, paymentDescription, paymentVendor, convertedUserPaymentAmount);
            bufferedWriter.write(formattedTransaction);
            System.out.println("✅ Payment added successfully!");
            System.out.println("====================================");
            bufferedWriter.newLine();
            bufferedWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void displayLedger() {

        int ledgerCallInput;
        do {
            System.out.println("\n=====================================");
            System.out.println("        🧾  Ledger Menu");
            System.out.println("=====================================");
            System.out.println("  [1] 📄 All Transactions");
            System.out.println("  [2] 💰 Deposits");
            System.out.println("  [3] 💸 Payments");
            System.out.println("  [4] 📊 Reports");
            System.out.println("  [0] 🔙 Return to Home Screen");
            System.out.println("=====================================");
            System.out.print("Select an option: ");


            try {
                ledgerCallInput = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Please enter a number from the menu.");
                scanner.nextLine();
                ledgerCallInput = -1;
            }

            switch (ledgerCallInput) {
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
                    System.out.println("Returning to homeScreen...");
                    break;
                default:
                    System.out.println("Invalid Choice Please Try Again With A Different Choice");//make a nicer message
            }

        } while (ledgerCallInput != 0);
    }

    private static void accountSummary() {

        int accountSummaryInput;

        do {
            System.out.println("\n=====================================");
            System.out.println("        📊 Account Summary");
            System.out.println("=====================================");
            System.out.println("  [1] 💵 Current Balance");
            System.out.println("  [2] \uD83D\uDECD\uFE0F Vendor Spending");
            System.out.println("  [3] \uD83D\uDCC6 Spending Summary");
            System.out.println("  [0] 🔙 Return to Ledger Menu");
            System.out.println("=====================================");
            System.out.print("Select an option: ");

            try {
                accountSummaryInput = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Please enter a number from the menu.");
                scanner.nextLine();
                accountSummaryInput = -1;
            }

            switch (accountSummaryInput){
                case 1:
                    displayCurrentBalance();
                    break;
                case 2:
                    displayVendorSpending();//implement
                    break;
                case 3:
                    displaySpendingSummary();//implment
                    break;
                case 0:
                    System.out.println("Returning to homeScreen...");
                    break;
                default:
                    System.out.println("Invalid Choice Please Try Again With A Different Choice");

            }


        }
        while (accountSummaryInput != 0);


    }



    private static void displayCurrentBalance() {

        double currentUserBalance = 0;
        for (Transaction transaction:transactions){
            currentUserBalance += transaction.getAmount();
        }
        System.out.println("=====================================");
        System.out.printf("     Your Current Balance is:\n\n");
        System.out.printf("           💰 $%,.2f%n", currentUserBalance);
    }


    private static void displayVendorSpending() {

        Map<String, Double> vendorTotals = new HashMap<>();

        for (Transaction transaction:transactions){

            //check if tbe vendor is already in if in add to it
            if (vendorTotals.containsKey(transaction.getVendor())){
                double currentTotal = vendorTotals.get(transaction.getVendor());
                vendorTotals.put(transaction.getVendor(),currentTotal+ transaction.getAmount());
            }
            else
            {
                vendorTotals.put(transaction.getVendor(), transaction.getAmount());
            }

        }
        for (Map.Entry<String,Double>entry : vendorTotals.entrySet()){
            System.out.println("Vendor: " + entry.getKey()+ "| Total: " + entry.getValue());
        }
    }


    private static void displaySpendingSummary() {
        int spendingSummaryInput = 0;

        do {
            System.out.println("1)This months spending ");
            System.out.println("2)Last months spending ");
            System.out.println("3)Year to Date ");
            System.out.println("4)Last year");
            System.out.println("0)Exit");

            spendingSummaryInput = scanner.nextInt();
            scanner.nextLine();//buffer

            switch (spendingSummaryInput){
                case 1:
                    thisMonthSummary();
                    break;
                case 2:
                    lastMonthSummary();
                    break;
                case 3:
                    thisYearsSummary();
                    break;
                case 4:
                    lastYearSummary();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid Choice Please Try Again With A Different Choice");
            }
        }
        while ( spendingSummaryInput != 0);


    }

    private static void lastYearSummary() {

        LocalDate timeNow = LocalDate.now();
        LocalDate lastYear = timeNow.minusYears(1);
        Map<String,Double> lastYearToDateSummary = new HashMap<>();

        for (Transaction transaction:transactions){
            if (transaction.getDate() .getYear() == lastYear.getYear()) {

                if (transaction.getAmount() > 0) {
                    //when setting keys make it lower case
                    //key shouldn't be displayed
                    double currentIncome = lastYearToDateSummary.getOrDefault("income", 0.0);
                    lastYearToDateSummary.put("income", currentIncome + transaction.getAmount());

                } else {
                    double currentExpense = lastYearToDateSummary.getOrDefault("expense", 0.0);
                    lastYearToDateSummary.put("expense", currentExpense + Math.abs(transaction.getAmount()));
                }
            }



        }

        for (Map.Entry<String, Double> entry : lastYearToDateSummary.entrySet()) {
            System.out.println(entry.getKey() + ": $" + String.format("%.2f", entry.getValue()));
        }
        //what if empty **something to think about**
        //if other categories hashmap would be a good fit
        double income = lastYearToDateSummary.getOrDefault("income", 0.0);
        double expense = lastYearToDateSummary.getOrDefault("expense", 0.0);

        displayBarSummary(income,expense);


    }

    private static void thisYearsSummary() {

        LocalDate timeNow = LocalDate.now();
        Map<String,Double> yearToDateSummary = new HashMap<>();

        for (Transaction transaction:transactions){
        if (transaction.getDate() .getYear() == timeNow. getYear()) {

            if (transaction.getAmount() > 0) {
                //when setting keys make it lower case
                //key shouldn't be displayed
                double currentIncome = yearToDateSummary.getOrDefault("income", 0.0);
                yearToDateSummary.put("income", currentIncome + transaction.getAmount());

            } else {
                double currentExpense = yearToDateSummary.getOrDefault("expense", 0.0);
                yearToDateSummary.put("expense", currentExpense + Math.abs(transaction.getAmount()));
            }


        }
    }

        for (Map.Entry<String, Double> entry : yearToDateSummary.entrySet()) {
        System.out.println(entry.getKey() + ": $" + String.format("%.2f", entry.getValue()));
    }
    //what if empty **something to think about**
    //if other categories hashmap would be a good fit
    double income = yearToDateSummary.getOrDefault("income", 0.0);
    double expense = yearToDateSummary.getOrDefault("expense", 0.0);
    double profit = income - expense;
        System.out.printf("Profit: $%.2f\n", profit);
}

    private static void lastMonthSummary() {
        Map<String, Double> lastMonthSummary = new HashMap<>();
        // if within previous month
        LocalDate timeNow = LocalDate.now();
        LocalDate previousMonth = timeNow.minusMonths(1);
//time.now.getYear
        //doesn't read as easily
        //this doesn't read as easuk
        for (Transaction transaction : transactions) {
            if (transaction.getDate().getMonth() == previousMonth.getMonth() &&
                    transaction.getDate().getYear() == previousMonth.getYear()) {

                if (transaction.getAmount() > 0) {
                    //when setting keys make it lower case
                    //key shouldn't be displayed
                    double currentIncome = lastMonthSummary.getOrDefault("income", 0.0);
                    lastMonthSummary.put("income", currentIncome + transaction.getAmount());

                } else {
                    double currentExpense = lastMonthSummary.getOrDefault("expense", 0.0);
                    lastMonthSummary.put("expense", currentExpense + Math.abs(transaction.getAmount()));
                }
            }
        }

        for (Map.Entry<String, Double> entry : lastMonthSummary.entrySet()) {
            System.out.println(entry.getKey() + ": $" + String.format("%.2f", entry.getValue()));
        }
//what if empty **something to think about**
        //if other categories hashmap would be a good fit
        double income = lastMonthSummary.getOrDefault("income", 0.0);
        double expense = lastMonthSummary.getOrDefault("expense", 0.0);
        double profit = income - expense;
        System.out.printf("Profit: $%.2f\n", profit);
    }

    private static void thisMonthSummary() {
        Map<String, Double> accountSummary = new HashMap<>();
        //if within this month
        LocalDate timeNow = LocalDate.now();
        for (Transaction transaction:transactions){

            if (transaction.getDate().getMonth() == timeNow.getMonth() &&
                    transaction.getDate() .getYear () == timeNow.getYear()){


                if (transaction.getAmount() > 0){
                    double currentIncome = accountSummary.getOrDefault("Income", 0.0);
                    accountSummary.put("Income", currentIncome + transaction.getAmount());

                }
                else {
                    double currentExpense = accountSummary.getOrDefault("Expense", 0.0);
                    accountSummary.put("Expense", currentExpense + Math.abs(transaction.getAmount()));
                }
            }


        }
        for (Map.Entry<String,Double>entries : accountSummary.entrySet()){
            System.out.println(entries.getKey() + entries.getValue());
        }
        double income = accountSummary.getOrDefault("Income",0.0);
        double expense = accountSummary.getOrDefault("Expense",0.0);
        double profit = income - expense;
        System.out.printf("Profit: $%.2f\n", profit);

    }

    private static void displayAll() {
        System.out.println("\nTransactions format:");
        System.out.println(" Date       | Time     | Description           | Vendor           |    Amount ");
        System.out.println("--------------------------------------------------------------------------------");

        for (Transaction transaction : transactions) {
            printTransaction(transaction);
        }

        System.out.println("--------------------------------------------------------------------------------");
    }

    private static void handleReportCall() {
        int reportCallInput;

        do {
            System.out.println("\n=====================================");
            System.out.println("         📊  Reports Menu");
            System.out.println("=====================================");
            System.out.println("  [1] 🗓️  Month-to-Date");
            System.out.println("  [2] 📅  Previous Month");
            System.out.println("  [3] 🗓️  Year-to-Date");
            System.out.println("  [4] 📆  Previous Year");
            System.out.println("  [5] 🏷️  Search by Vendor");
            System.out.println("  [6] 🔍 Custom Search");
            System.out.println("  [0] 🔙 Back to Ledger Menu");
            System.out.println("=====================================");
            System.out.print("Select an option: ");

            reportCallInput = scanner.nextInt();
            scanner.nextLine();

            switch (reportCallInput) {
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
                case 6:
                    customSearch();
                    break;
                case 0:
                    System.out.println("Returning to homeScreen...");
                    break;
                default:
                    System.out.println("Invalid Choice Please Try Again With A Different Choice");
            }
        } while (reportCallInput != 0);

    }

    private static void displayDeposits() {
        System.out.println("\nTransactions format: Date       | Time   | Description           | Vendor          | Amount");
        System.out.println("------------------------------------------------------------------------------------------");

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0) {
                printTransaction(transaction);
            }
        }

        System.out.println("------------------------------------------------------------------------------------------");
    }

//if its 0 handle this ,
    //maybe empty payment to someone else
    //handle edge cases
    //consider valiadtion
    //make sure it doesn't crash

    //a bunch of try and catches
    //research error handling
    //and exception handling
    //u can use throws and parent method handles

    private static void displayPayments() {

        System.out.println("Transactions format: Date | Time | Description | Vendor | Amount");

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                printTransaction(transaction);
            }

        }
    }

    private static void displayMonthToDate() {
        LocalDate timeNow = LocalDate.now();

        System.out.println("\n📅 Month-to-Date Transactions");
        System.out.println("===================================================================================");
        System.out.println("Date       | Time     | Description           | Vendor            | Amount");
        System.out.println("-----------------------------------------------------------------------------------");

        for (Transaction transaction : transactions) {
            if (transaction.getDate().getMonth() == timeNow.getMonth() &&
                    transaction.getDate().getYear() == timeNow.getYear()) {
                printTransaction(transaction);
            }
        }

        System.out.println("===================================================================================");
    }


    private static void displayPreviousMonth() {
        LocalDate timeNow = LocalDate.now();
        LocalDate previousMonth = timeNow.minusMonths(1);

        System.out.println("\n📅 Previous Month's Transactions");
        System.out.println("===================================================================================");
        System.out.println("Date       | Time     | Description           | Vendor            | Amount");
        System.out.println("-----------------------------------------------------------------------------------");

        for (Transaction transaction : transactions) {
            if (transaction.getDate().getMonth() == previousMonth.getMonth() &&
                    transaction.getDate().getYear() == previousMonth.getYear()) {
                printTransaction(transaction);
            }
        }

        System.out.println("===================================================================================");
    }


    private static void displayYearToDate() {
        LocalDate timeNow = LocalDate.now();

        System.out.println("\n📅 Year-to-Date Transactions");
        System.out.println("===================================================================================");
        System.out.println("Date       | Time     | Description           | Vendor            | Amount");
        System.out.println("-----------------------------------------------------------------------------------");

        for (Transaction transaction : transactions) {
            if (transaction.getDate().getYear() == timeNow.getYear()) {
                printTransaction(transaction);
            }
        }

        System.out.println("===================================================================================");
    }



    private static void displayPreviousYear() {

        LocalDate timeNow = LocalDate.now();
        LocalDate previousYear = timeNow.minusYears(1);

        for (Transaction transaction : transactions) {
            if (transaction.getDate().getYear() == previousYear.getYear()) {
                printTransaction(transaction);
            }
        }
    }
    private static void searchByVendor() {
        //use a set for this more complicated

        displayVendorList();
        System.out.println("\n🔍 Enter a keyword or full name to filter by Vendor:");
        String userVendorFilter = scanner.nextLine().toLowerCase();

        System.out.println("\n📦 Matching Transactions");
        System.out.println("===================================================================================");
        System.out.println("Date       | Time     | Description           | Vendor            | Amount");
        System.out.println("-----------------------------------------------------------------------------------");


        for (Transaction transaction : transactions) {
            if (userVendorFilter.equalsIgnoreCase(transaction.getVendor())) {
                printTransaction(transaction);
            }
        }
    }

    private static void displayVendorList(){
        Set<String> vendors = new HashSet<>() ;
        for (Transaction transaction:transactions){
            vendors.add(transaction.getVendor());
        }
        System.out.println("\n📋 Unique Vendors List:");
        System.out.println("========================");
        for (String vendor : vendors) {
            System.out.println("• " + vendor);
        }
        System.out.println("========================");
    }

    private static void printTransaction(Transaction transaction) {
        System.out.println("-----------------------------------------------------------------------");
        System.out.printf("%-12s | %-8s | %-20s | %-15s | %8.2f%n",
                transaction.getDate(),
                transaction.getTime(),
                transaction.getDescription(),
                transaction.getVendor(),
                transaction.getAmount());
    }


    private static void customSearch() {
        System.out.println("Enter start date: "); //specifiy format
        //extra thing would be handling multiple types of date
        //enter 3 differnt ways
        //american way
        String startDateInputPreProcessed = scanner.nextLine().trim();
        LocalDate startDate = null;
        if (!startDateInputPreProcessed.isBlank()) {
            startDate = LocalDate.parse(startDateInputPreProcessed);
        }

        System.out.println("Enter end date: ");
        String endDateInputPreProcessed = scanner.nextLine().trim();
        LocalDate endDate = null;
        if (!endDateInputPreProcessed.isBlank()) {
            endDate = LocalDate.parse(endDateInputPreProcessed);
        }

        System.out.println("Enter description keyword: ");
        String descriptionInput = scanner.nextLine().trim();

        System.out.println("Enter vendor: ");
        String vendorInput = scanner.nextLine().trim();

        System.out.println("Enter the approximate amount of the transaction"); //exact amount bad
        //no one gonna know exact amount
        //make it a range
        // reformat this to seperate method for handling date range
        //look up helper method
        //given transactions
        //method that gets a bunch of transactions and it auto handles
        //reusuable date range logic
        //given range
        double amountInput = scanner.nextDouble();
        scanner.nextLine();


        for (Transaction transaction : transactions) {
            boolean matchFound = true;

            if (startDate != null && transaction.getDate().isBefore(startDate)) {
                matchFound = false;
            }
            if (endDate != null && transaction.getDate().isAfter(endDate)) {
                matchFound = false;
            }
            if (!descriptionInput.isBlank() && !transaction.getDescription().toLowerCase().contains(descriptionInput.toLowerCase())) {
                matchFound = false;
            }
            if (!vendorInput.isBlank() && !transaction.getVendor().equalsIgnoreCase(vendorInput)) {
                matchFound = false;
            }
            if (amountInput != 0 && Math.abs(Math.abs(transaction.getAmount()) - amountInput) > 2) {
                matchFound = false;
            }


            if (matchFound) {
                printTransaction(transaction);
            }

        }

    }

    private static void displayHomeScreenAsciiArt() {
        final String RESET = "\u001B[0m";
        final String CYAN = "\u001B[36m";
        final String YELLOW = "\u001B[33m";
        final String GREEN = "\u001B[32m";
        final String BOLD = "\u001B[1m";

        System.out.println(CYAN + " __   __ _   _   ____     _     _   _  _  __ ");
        System.out.println(" \\ \\ / /| | | | | __ )   / \\   | \\ | || |/ / ");
        System.out.println("  \\ V / | | | | |  _ \\  / _ \\  |  \\| || ' /  ");
        System.out.println("   | |  | |_| | | |_) |/ ___ \\ | |\\  || . \\  ");
        System.out.println("   |_|   \\___/  |____//_/   \\_\\|_| \\_||_|\\_\\ " + RESET);
        System.out.println(YELLOW + "                                             ");
        System.out.println(GREEN + BOLD + "“Control your money, don’t let it control you” 💼\n" + RESET);
    }

    private static void displayBarSummary(double income, double expense) {
        int maxBarLength = 30;
        double maxAmount = Math.max(income, expense);
        int incomeBarLength = (int) ((income / maxAmount) * maxBarLength);
        int expenseBarLength = (int) ((expense / maxAmount) * maxBarLength);

        String incomeBar = "█".repeat(incomeBarLength);
        String expenseBar = "█".repeat(expenseBarLength);


        incomeBar = String.format("%-" + maxBarLength + "s", incomeBar);
        expenseBar = String.format("%-" + maxBarLength + "s", expenseBar);

        System.out.println();
        System.out.printf("Income  : %s $%.2f%n", incomeBar, income);
        System.out.printf("Expense : %s $%.2f%n", expenseBar, expense);
        System.out.printf("Profit  : $%.2f%n", income - expense);
    }


}

