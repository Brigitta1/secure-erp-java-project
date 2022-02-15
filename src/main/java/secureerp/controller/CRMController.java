package secureerp.controller;

import secureerp.Util;
import secureerp.dao.CRMDAO;
import secureerp.view.TerminalView;

import java.util.ArrayList;
import java.util.List;


public class CRMController {
    public static void listCustomers() {
        TerminalView.printTable(CRMDAO.createPrintableTable());
    }

    public static String[] questionsAboutCustomer = {
            "ID's added automatically. Hit enter.\n",
            "New customer's name\n",
            "New customer's email address\n",
            "New customer's subscription status (True or False)\n"
    };

    public static void addCustomer() {
        String[] potentialCustomer = TerminalView.getInputs(questionsAboutCustomer);
        potentialCustomer[0] = Util.generateUniqueId(CRMDAO.getCustomersIDs());
        CRMDAO.createCustomer(potentialCustomer);
    }

    public static String[] questionsAboutCustomerForUpdate = {
            "ID was selected earlier. Hit enter.\n",
            "Customer's updated name\n",
            "Customer's updated email address\n",
            "Customer's updated subscription status (True or False)\n"
    };

    public static void updateCustomers() {
        String customerToUpdateID = TerminalView.getInput("Please type in the ID of the customer to be updated.");
        String[] updatedCustomer = TerminalView.getInputs(questionsAboutCustomerForUpdate);
        updatedCustomer[0] = customerToUpdateID;
        CRMDAO.updateCustomer(customerToUpdateID, updatedCustomer);
    }

    public static void deleteCustomers() {
        String customerToDelete = TerminalView.getInput("Please type in the ID of the customer.");
        CRMDAO.deleteCustomer(customerToDelete);
    }

    public static void printSubscribedEmails() {
        List<String> emailsToPrint = CRMDAO.getSubscribedEmails();
        for (String email : emailsToPrint) {
            TerminalView.printGeneralList(email);
        }
        System.out.println();
    }

    public static void runOperation(int option) {
        switch (option) {
            case 1: {
                listCustomers();
                break;
            }
            case 2: {
                addCustomer();
                break;
            }
            case 3: {
                updateCustomers();
                break;
            }
            case 4: {
                deleteCustomers();
                break;
            }
            case 5: {
                printSubscribedEmails();
                break;
            }
            case 0:
                CRMDAO.writeCustomersToFile();
                return;
            default:
                throw new IllegalArgumentException("There is no such option.");
        }
    }

    public static void displayMenu() {
        String[] options = {
                "Back to main menu",
                "List customers",
                "Add new customer",
                "Update customer",
                "Remove customer",
                "Subscribed customer emails"
        };
        TerminalView.printMenu("Customer Relationship", options);
    }

    public static void menu() {
        CRMDAO.readCustomerFromFile();
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
