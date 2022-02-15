package secureerp.dao;

import secureerp.Util;
import secureerp.model.SalesModel;
import secureerp.view.TerminalView;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.*;

public class SalesDAO {
    private static final int ID_TABLE_INDEX = 0;
    private static final int CUSTOMER_ID_TABLE_INDEX = 1;
    private static final int PRODUCT_TABLE_INDEX = 2;
    private static final int PRICE_TABLE_INDEX = 3;
    private static final int TRANSACTION_DATE_TABLE_INDEX = 4;
    private static final String DATA_FILE = "src/main/resources/sales.csv";
    private static final List<SalesModel> salesModels= new ArrayList<>();
    private static final String[] headers = {"Id", "Customer Id", "Product", "Price", "Transaction Date"};


    public static String[] getTransactionsIDs(){
        String[] iDs = new String[salesModels.size()];
        for (int i = 0; i < iDs.length; i++) {
            iDs[i]= salesModels.get(i).getId();
        }
        return iDs;
    }

    public static void listTransactions(){
        List<String> transactions=Util.readDatabaseFromFile(DATA_FILE);
        for (String tr : transactions) {
            String[] tempString=tr.split(";");
            salesModels.add(new SalesModel(
                    tempString[ID_TABLE_INDEX],
                    tempString[CUSTOMER_ID_TABLE_INDEX],
                    tempString[PRODUCT_TABLE_INDEX],
                    Float.parseFloat(tempString[PRICE_TABLE_INDEX]),
                    LocalDate.parse(tempString[TRANSACTION_DATE_TABLE_INDEX])
            ));
        }
    }

    public static String[][] createPrintableTable(){
        String[][] printableTable= new String[salesModels.size()+1][headers.length];
        printableTable[0]=headers;
        for (int i = 0; i < salesModels.size(); i++) {
            printableTable[i+1]=salesModels.get(i).toTableRow();
        }
        return printableTable;
    }

    public static void createTransaction(String[] transaction){
        SalesModel newTransaction= new SalesModel(
                transaction[ID_TABLE_INDEX],
                transaction[CUSTOMER_ID_TABLE_INDEX],
                transaction[PRODUCT_TABLE_INDEX],
                Float.parseFloat(transaction[PRICE_TABLE_INDEX]),
                LocalDate.parse(transaction[TRANSACTION_DATE_TABLE_INDEX])
        );
        salesModels.add(newTransaction);
    }

    public static void updateTransaction(String transactionID, String[] updateTransaction){
        int transactionIndex=Arrays.asList(getTransactionsIDs()).indexOf(transactionID);
        salesModels.get(transactionIndex).setId(updateTransaction[ID_TABLE_INDEX]);
        salesModels.get(transactionIndex).setCustomerId(updateTransaction[CUSTOMER_ID_TABLE_INDEX]);
        salesModels.get(transactionIndex).setProduct(updateTransaction[PRODUCT_TABLE_INDEX]);
        salesModels.get(transactionIndex).setPrice(Float.parseFloat(updateTransaction[PRICE_TABLE_INDEX]));
        salesModels.get(transactionIndex).setTransactionDate(LocalDate.parse(updateTransaction[TRANSACTION_DATE_TABLE_INDEX]));
    }

    public static boolean checkIfSelectedIDValid(String transactionID){
        return Arrays.asList(getTransactionsIDs()).contains(transactionID);
    }

    public static void deleteTransaction(String transactionID) {
        int transactionIndex;
        if (Arrays.asList(getTransactionsIDs()).contains(transactionID)) {
            transactionIndex = Arrays.asList(getTransactionsIDs()).indexOf(transactionID);
            salesModels.remove(transactionIndex);
        } else {
            throw new NoSuchElementException("Transaction not found by the ID: " + transactionID);
        }
    }

    public static void sumPriceBetweenDates(String startDate, String endDate){
        float sumPrice = 0;
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        for (SalesModel sm : salesModels) {
            if(start.compareTo(sm.getTransactionDate()) <= 0 && end.compareTo(sm.getTransactionDate()) >= 0) {
                sumPrice += sm.getPrice();
            }
        }
        TerminalView.printGeneralList("The sum of prices of all transactions between the two dates is " + sumPrice + "\n");
    }

    private static List<String> readTransactionFromFile() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(String.valueOf(Util.getPath(DATA_FILE))))) {
            while (bufferedReader.ready()) {
                lines.add(bufferedReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static float biggestRevenue() {
        List<Float> biggest = new ArrayList<>();
        for (SalesModel sm : salesModels) {
            biggest.add(sm.getPrice());
        }
        return Collections.max(biggest);
    }
    public static String biggestRevenueProduct(){

        Map<String, Float> map = new HashMap<>();
        for (SalesModel salesModel : salesModels) {
            map.put(salesModel.getProduct(), map.getOrDefault(salesModel.getProduct(), 0f) + salesModel.getPrice());
        }
        float maxValueInMap = (Collections.max(map.values()));
        for (Map.Entry<String, Float> entry : map.entrySet()) {
            if (entry.getValue() == maxValueInMap) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static int transactionBetween(String startDate, String endDate){
        List<LocalDate> ld = new ArrayList<>();
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        for (SalesModel sm : salesModels) {
            if(start.compareTo(sm.getTransactionDate()) <= 0 && end.compareTo(sm.getTransactionDate()) >= 0) {
                ld.add(sm.getTransactionDate());
            }
        }
        return ld.size();
    }

    public static void writeTransactionsToFile(){
        try (BufferedWriter bufferedWriter=
                     new BufferedWriter(new FileWriter(String.valueOf(Util.getPath(DATA_FILE))))){
            for (SalesModel sm : salesModels) {
                bufferedWriter.write(String.join(";",sm.toTableRow()));
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
        salesModels.clear();
    }
}


