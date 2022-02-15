package secureerp.controller;

import secureerp.Util;
import secureerp.dao.HRDAO;
import secureerp.dao.SalesDAO;
import secureerp.view.TerminalView;

import java.util.NoSuchElementException;


public class HRController {
    private final static int EMPLOYEE_INPUT_CLEARENCE_LEVEL = 5;

    public static String[] ADD_EMPLOYEE_QUESTIONS = {
            "ID's added automatically, please push 'enter' button to continue \n",
            "New employee's Name\n",
            "New employee's Birthdate\n",
            "New employee's Department\n",
            "New employee's clearance\n"
    };
    public static String[] UPDATE_EMPLOYEE_QUESTIONS = {
            "ID is immutable",
            "Employee's Name\n",
            "Employee's Birthdate\n",
            "Employee's Department\n",
            "Employee's clearance\n"
    };

    public static void listEmployees() {
        TerminalView.printTable(HRDAO.createPrintableTable());
    }

    public static void addEmployee() {
        String[] employeeWannabe = TerminalView.getInputs(ADD_EMPLOYEE_QUESTIONS);
        employeeWannabe[0] = Util.generateUniqueId(HRDAO.getEmployeesIDs());
        HRDAO.createEmployee(employeeWannabe);
    }

    public static void updateEmployee() {
        String employeeID = TerminalView.getInput("Please, provide the ID of the employee to be updated!");
        if (HRDAO.checkIfSelectedIDValid(employeeID)) {
            String[] updatedEmployee = TerminalView.getInputs(UPDATE_EMPLOYEE_QUESTIONS);
            updatedEmployee[0] = employeeID;
            HRDAO.updateEmployee(employeeID, updatedEmployee);
        }

    }

    public static void deleteEmployee() {
        String employeeID = TerminalView.getInput("Please, provide the ID of the employee to be deleted!");
        try {
            HRDAO.deleteEmployee(employeeID);
        } catch (NoSuchElementException e) {
            TerminalView.printErrorMessage(e.toString());
        }
    }

    public static void getOldestAndYoungest() {
        TerminalView.printTable(HRDAO.oldestYoungestEmployees());
    }

    public static void getAverageAge() {
        TerminalView.printMessage("Average Age is " + HRDAO.getAverageAge() + "\n");

    }

    public static void nextBirthdays() {
        TerminalView.printErrorMessage(HRDAO.nextBirthday());
    }

    public static void countEmployeesWithClearance() {
        int numberOfEmployeesWithClearance = 0;
        int[] clearances = HRDAO.clearanceOfEmployee();
        for (int clearance : clearances) {
            if (clearance >= EMPLOYEE_INPUT_CLEARENCE_LEVEL) {
                numberOfEmployeesWithClearance++;
            }
        }
        TerminalView.printMessage("Number of employees with clearance: " + numberOfEmployeesWithClearance + "\n");
    }

    public static void countEmployeesPerDepartment() {
        TerminalView.printMessage("Employees per departments: "+ HRDAO.employeesPerDepartment()+"\n");
    }

    public static void runOperation(int option) {
        switch (option) {
            case 1: {
                listEmployees();
                break;
            }
            case 2: {
                addEmployee();
                break;
            }
            case 3: {
                updateEmployee();
                break;
            }
            case 4: {
                deleteEmployee();
                break;
            }
            case 5: {
                getOldestAndYoungest();
                break;
            }
            case 6: {
                getAverageAge();
                break;
            }
            case 7: {
                nextBirthdays();
                break;
            }
            case 8: {
                countEmployeesWithClearance();
                break;
            }
            case 9: {
                countEmployeesPerDepartment();
                break;
            }
            case 0:
                HRDAO.writeEmployeesToFile();
                return;
            default:
                throw new IllegalArgumentException("There is no such option.");
        }
    }

    public static void displayMenu() {
        String[] options = {
                "Back to main menu",
                "List employees",
                "Add new employee",
                "Update employee",
                "Remove employee",
                "Oldest and youngest employees",
                "Employees average age",
                "Employees with birthdays in the next two weeks",
                "Employees with clearance level",
                "Employee numbers by department",
        };
        TerminalView.printMenu("Human Resources", options);
    }

    public static void menu() {
        HRDAO.readEmployeesFromFile();
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

