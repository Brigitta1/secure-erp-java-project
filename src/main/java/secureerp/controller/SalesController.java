package secureerp.controller;

import secureerp.Util;
import secureerp.dao.SalesDAO;
import secureerp.view.TerminalView;

import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

public class SalesController {

    public static String[] transactionQuestions={
            "ID's added automatically",
            "Customer ID's added automatically",
            "Product's name\n",
            "Product's price\n",
            "Transaction's date\n",
    };

    public static String[] UPDATE_TRANSACTION_QUESTIONS={
            "ID is immutable",
            "Customer ID's added automatically",
            "Product's name\n",
            "Product's price\n",
            "Transaction's date\n"
    };

    public static void listTransactions() {

        TerminalView.printTable(SalesDAO.createPrintableTable());
    }

    public static void addTransaction() {

        String[] transaction = TerminalView.getInputs(transactionQuestions);
        transaction[0] = Util.generateUniqueId(SalesDAO.getTransactionsIDs());
        transaction[1] = Util.generateUniqueId(SalesDAO.getTransactionsIDs());
        SalesDAO.createTransaction(transaction);
    }

    public static void updateTransactions() {

        TerminalView.printTable(SalesDAO.createPrintableTable());
        String transactionID=TerminalView.getInput("Please, provide the ID of the transaction to be updated!");
        if(SalesDAO.checkIfSelectedIDValid(transactionID)){
            String[] updatedTransaction=TerminalView.getInputs(UPDATE_TRANSACTION_QUESTIONS);
            updatedTransaction[0]=transactionID;
            updatedTransaction[1] = Util.generateUniqueId(SalesDAO.getTransactionsIDs());
            SalesDAO.updateTransaction(transactionID,updatedTransaction);
        }
    }

    public static void deleteTransactions() {

        TerminalView.printTable(SalesDAO.createPrintableTable());
        String transactionID=TerminalView.getInput("Please, provide the ID of the transaction to be deleted!");
        try {
            SalesDAO.deleteTransaction(transactionID);
        } catch (NoSuchElementException e){
            TerminalView.printErrorMessage(e.toString());
        }
    }

    public static void getBiggestRevenueTransaction() {
        TerminalView.printMessage("The biggest revenue is "+ SalesDAO.biggestRevenue()+"\n");
    }

    public static void getBiggestRevenueProduct() {
        TerminalView.printMessage("The biggest revenue product is: "+ SalesDAO.biggestRevenueProduct()+"\n");
    }

    public static void countTransactionsBetween() {
        String startDate = TerminalView.getInput("Please enter start date.");
        String endDate = TerminalView.getInput("Please enter end date.");
        try{
            TerminalView.printMessage("The number of transactions between the dates is: " + SalesDAO.transactionBetween(startDate, endDate) + "\n");
        } catch (DateTimeParseException e) {
            TerminalView.printErrorMessage(e.toString());
        }    }

    public static void sumTransactionsBetween() {
        String startDate = TerminalView.getInput("Please enter start date.");
        String endDate = TerminalView.getInput("Please enter end date.");
        try{
            SalesDAO.sumPriceBetweenDates(startDate, endDate);
        } catch (DateTimeParseException e) {
            TerminalView.printErrorMessage(e.toString());
        }
    }

    public static void runOperation(int option) {
        switch (option) {
            case 1: {
                listTransactions();
                break;
            }
            case 2: {
                addTransaction();
                break;
            }
            case 3: {
                updateTransactions();
                break;
            }
            case 4: {
                deleteTransactions();
                break;
            }
            case 5: {
                getBiggestRevenueTransaction();
                break;
            }
            case 6: {
                getBiggestRevenueProduct();
                break;
            }
            case 7: {
                countTransactionsBetween();
                break;
            }
            case 8: {
                sumTransactionsBetween();
                break;
            }
            case 0:
                SalesDAO.writeTransactionsToFile();
                return;
            default:
                throw new IllegalArgumentException("There is no such option");
        }
    }

    public static void displayMenu() {
        String[] options = {
                "Back to main menu",
                "List transactions",
                "Add new transaction",
                "Update transaction",
                "Remove transaction",
                "Get the transaction that made the biggest revenue",
                "Get the product that made the biggest revenue altogether",
                "Count number of transactions between",
                "Sum the price of transactions between"
        };

        TerminalView.printMenu("Sales", options);
    }

    public static void menu() {
        SalesDAO.listTransactions();
        int operation = -1;
        while (operation != 0) {
            displayMenu();
            String userInput = TerminalView.getInput("Select an operation");
            if (Util.tryParseInt(userInput)) {
                operation = Integer.parseInt(userInput);
                runOperation(operation);
            } else {
                TerminalView.printErrorMessage("This is not a number");
            }
        }
    }
}

