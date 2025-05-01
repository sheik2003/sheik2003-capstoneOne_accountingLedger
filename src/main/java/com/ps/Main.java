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
            System.out.println("\n=====================================");
            System.out.println("        🏠  Home Screen Menu");
            System.out.println("=====================================");
            System.out.println("  [1] 💰 Add Deposit");
            System.out.println("  [2] 🧾 Make Payment");
            System.out.println("  [3] 📘 Ledger");
            System.out.println("  [4] 📊 Account Summary");
            System.out.println("  [0] ❌ Exit");
            System.out.println("=====================================");
            System.out.print(" What would you like to do? ");

            homeScreenCommand = scanner.nextInt();
            scanner.nextLine();

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
                case 0:
                    System.out.println("Exiting");
                    break;
                default:
                    System.out.println("Wrong command entered please retry again with a valid choice ");

            }
//collections.sort to control the order of transactions ,, pass it a method that determines order buy
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

        System.out.println("========== 💰 Add Deposit ==========");
        System.out.println("Please enter the date of the Deposit(format yyyy-mm-dd): ");
        String depositDateInString = scanner.nextLine().trim();
        LocalDate depositDate = LocalDate.parse(depositDateInString);

        System.out.println("Please enter the time of the Deposit(format hh:mm:ss): ");
        String depositTimeInString = scanner.nextLine().trim();
        LocalTime depositTime = LocalTime.parse(depositTimeInString);

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
        System.out.println("Please enter the date of the Payment(format yyyy-mm-dd): ");
        String paymentDateInString = scanner.nextLine().trim();
        LocalDate paymentDate = LocalDate.parse(paymentDateInString);

        System.out.println("Please enter the time of the Payment(format hh:mm:ss): ");
        String paymentTimeInString = scanner.nextLine().trim();
        LocalTime paymentTime = LocalTime.parse(paymentTimeInString);

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

    //make all lowercase
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


            ledgerCallInput = scanner.nextInt();
            scanner.nextLine();

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
                    System.out.println("Going back to the homeScreen...");
                    break;
                default:
                    System.out.println("Wrong Choice entered please try again");//make a nicer message
            }

        } while (ledgerCallInput != 0);
//make sure to do buffered close
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

            accountSummaryInput = scanner.nextInt();
            scanner.nextLine(); //buffer

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
                    System.out.println("Going back to HomeScreen");
                    break;
                default:
                    System.out.println("Wrong Choice entered please try again");

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
                    //lastYearSummary();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Wrong input Please Retry");
            }
        }
        while ( spendingSummaryInput != 0);


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

        System.out.println("Transactions format: Date | Time | Description | Vendor | Amount");
        for (Transaction transaction : transactions) {
            printTransaction(transaction);
        }
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
                    System.out.println("Going back to Ledger page...");
                    break;
                default:
                    System.out.println("Wrong choice please try again");
            }
        } while (reportCallInput != 0);

    }

    private static void displayDeposits() {

        System.out.println("Transactions format: Date | Time | Description | Vendor | Amount");

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0) {
                printTransaction(transaction);
            }

        }
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
//make this look a little prettier
        for (Transaction transaction : transactions) {
            if (transaction.getDate().getMonth() == timeNow.getMonth() &&
                    transaction.getDate().getYear() == timeNow.getYear()) {
                printTransaction(transaction);
            }
        }
    }

    private static void displayPreviousMonth() {

        LocalDate timeNow = LocalDate.now();
        LocalDate previousMonth = timeNow.minusMonths(1);

        for (Transaction transaction : transactions) {
            if (transaction.getDate().getMonth() == previousMonth.getMonth() &&
                    transaction.getDate().getYear() == previousMonth.getYear()) {
                printTransaction(transaction);
            }
        }
    }

    private static void displayYearToDate() {

        LocalDate timeNow = LocalDate.now();

        for (Transaction transaction : transactions) {
            if (transaction.getDate().getYear() == timeNow.getYear()) {
                printTransaction(transaction);
            }
        }
    }

    // make new transactions appear first

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
//make it partial
        //what if somone does ama
        //make sure to add to readMe if somethign adidontal is added
        //notate what you spent extra time on
        //seperate
        //alt option for vendor display
        //use a set for this more complicated
        //store in set simialr to array list, only unique value
        //notate this in readMe used set
        displayVendorList();
        System.out.println("\n");
        System.out.println("Please enter the name of the Vendor to filter search: ");
        String userVendorFilter = scanner.nextLine();
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
        System.out.printf("%s | %s | %s | %s | %.2f%n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
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

        System.out.println("Enter the exact amount of the transaction or 0 if unsure"); //exact amount bad
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
            if (!descriptionInput.isBlank() && !transaction.getDescription().contains(descriptionInput)) {
                matchFound = false;
            }
            if (!vendorInput.isBlank() && !transaction.getVendor().equalsIgnoreCase(vendorInput)) {
                matchFound = false;
            }
            if (amountInput != 0 && transaction.getAmount() != amountInput) {
                matchFound = false;
            }
            if (matchFound) {
                printTransaction(transaction);
            }

        }

    }

    private static void displayHomeScreenAsciiArt(){

        System.out.println(" __   __ _   _   ____     _     _   _  _  __ ");
        System.out.println(" \\ \\ / /| | | | | __ )   / \\   | \\ | || |/ / ");
        System.out.println("  \\ V / | | | | |  _ \\  / _ \\  |  \\| || ' /  ");
        System.out.println("   | |  | |_| | | |_) |/ ___ \\ | |\\  || . \\  ");
        System.out.println("   |_|   \\___/  |____//_/   \\_\\|_| \\_||_|\\_\\ ");
        System.out.println("                                             ");
        System.out.println("“Control your money, don’t let it control you” \uD83D\uDCBC\n ");


    }

}

