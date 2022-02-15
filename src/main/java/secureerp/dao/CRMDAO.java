package secureerp.dao;

import secureerp.Util;
import secureerp.model.CRMModel;

import java.io.*;
import java.util.*;

public class CRMDAO {
    private final static int ID_TABLE_INDEX = 0;
    private final static int NAME_TABLE_INDEX = 1;
    private final static int EMAIL_TABLE_INDEX = 2;
    private final static int SUBSCRIBED_TABLE_INDEX = 3;
    private final static String DATA_FILE = "crm.csv";
    public static String[] headers = {"Id", "Name", "Email", "Subscribed"};
    private final static List<CRMModel> crmModels = new ArrayList<>();


    public static void readCustomerFromFile() {
        List<String> users=Util.readDatabaseFromFile(DATA_FILE);
        for (String user : users) {
            String[] tempString=user.split(";");
            crmModels.add(new CRMModel(
                    tempString[ID_TABLE_INDEX],
                    tempString[NAME_TABLE_INDEX],
                    tempString[EMAIL_TABLE_INDEX],
                    tempString[SUBSCRIBED_TABLE_INDEX].equals("1") ? true : false
            ));
        }
    }

    public static void writeCustomersToFile() {
        try (BufferedWriter bufferedWriter =
                     new BufferedWriter(new FileWriter(String.valueOf(Util.getPath(DATA_FILE)), false))) {
            for (CRMModel crmModel : crmModels) {
                bufferedWriter.write(String.join(";",crmModel.toTableRow()));
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
        crmModels.clear();
    }

    public static String[][] createPrintableTable() {
        String[][] printableTable = new String[crmModels.size() + 1][headers.length];
        printableTable[0] = headers;
        for (int i = 0; i < crmModels.size(); i++) {
            printableTable[i + 1] = crmModels.get(i).toTableRow();
        }
        return printableTable;
    }

    public static String[] getCustomersIDs() {
        String[] IDs = new String[crmModels.size()];
        for (int i = 0; i < IDs.length; i++) {
            IDs[i] = crmModels.get(i).getId();
        }
        return IDs;
    }

    public static void createCustomer(String[] potentialCustomer) {
        CRMModel newCustomer = new CRMModel(
                potentialCustomer[ID_TABLE_INDEX],
                potentialCustomer[NAME_TABLE_INDEX],
                potentialCustomer[EMAIL_TABLE_INDEX],
                Boolean.parseBoolean(potentialCustomer[SUBSCRIBED_TABLE_INDEX])
        );
        crmModels.add(newCustomer);
    }

    public static void updateCustomer(String customerID, String[] customer) {
        int customerIndex = Arrays.asList(getCustomersIDs()).indexOf(customerID);
        crmModels.get(customerIndex).setName(customer[NAME_TABLE_INDEX]);
        crmModels.get(customerIndex).setEmail(customer[EMAIL_TABLE_INDEX]);
        crmModels.get(customerIndex).setSubscribed(Boolean.parseBoolean(customer[SUBSCRIBED_TABLE_INDEX]));
    }

    public static void deleteCustomer(String customerToBeDeleted) {
        int customerIndex;
        if (Arrays.asList(getCustomersIDs()).contains(customerToBeDeleted)) {
            customerIndex = Arrays.asList(getCustomersIDs()).indexOf(customerToBeDeleted);
            crmModels.remove(customerIndex);
        }
    }

    public static String[] getCustomersEmails() {
        String[] emails = new String[crmModels.size()];
        for (int i = 0; i < emails.length; i++) {
            emails[i] = crmModels.get(i).getEmail();
        }
        return emails;
    }

    public static Integer[] getSubscriptionStatus() {
        Integer[] subscription = new Integer[crmModels.size()];
        for (int i = 0; i < subscription.length; i++) {
            if (crmModels.get(i).isSubscribed()) {
                subscription[i] = 1;
            } else {
                subscription[i] = 0;
            }
        }
        return subscription;
    }

    public static List<String> getSubscribedEmails() {
        List<String> subscribedEmails = new ArrayList<>();
        String[] customerEmails = getCustomersEmails();
        Integer[] subscriptionStatus = getSubscriptionStatus();
        for (int i = 0; i < customerEmails.length; i++) {
            if (subscriptionStatus[i] == 1) {
                subscribedEmails.add(customerEmails[i]);
            }
        }
        return subscribedEmails;
    }

}
