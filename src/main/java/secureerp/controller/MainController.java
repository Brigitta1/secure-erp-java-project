package secureerp.controller;

import secureerp.Util;
import secureerp.view.TerminalView;

public class MainController {

    public static void loadModule(int option) {
        switch (option) {
            case 1: {
                CRMController.menu();
                break;
            }
            case 2: {
                SalesController.menu();
                break;
            }
            case 3: {
                HRController.menu();
                break;
            }
            case 0:
                return;
            default:
                throw new IllegalArgumentException("There is no such module");
        }
    }

    public static void displayMenu() {
        String[] options = {
                "Exit program",
                "Customer Relationship Management (CRM)",
                "Sales",
                "Human Resources"
        };
        TerminalView.printMenu("Main menu", options);
    }

    public static void menu() {
        int module = -1;
        while (module != 0) {
            displayMenu();
            String userInput = TerminalView.getInput("Select module");
            if (Util.tryParseInt(userInput)) {
                module = Integer.parseInt(userInput);
                loadModule(module);
            } else {
                TerminalView.printErrorMessage("This is not a number");
            }
        }
        TerminalView.printMessage("Goodbye!");
    }
}
