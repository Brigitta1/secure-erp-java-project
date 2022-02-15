package secureerp.view;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Formatter;
import java.util.stream.Stream;

public class TerminalView {

    private static final Scanner scanner = new Scanner(System.in);
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final int FIRST_LAST_LINE_PATTERN_REPEAT_NUMBER=31;
    public static final int MID_LINE_PATTERN_REPEAT_NUMBER=30;

    /**
     * Prints a single message to the terminal
     *
     * @param message information to be printed
     */
    public static void printMessage(String message) {
        System.out.println(ANSI_BLUE+message+ANSI_RESET);
    }

    /**
     * Prints options in standard menu format like this:
     *      Main Menu:
     *      (1) Store manager
     *      (2) Human resources manager
     *      (3) Inventory manager
     *      (0) Exit program
     *
     * @param title the title of the menu (first row)
     * @param options array of all available options in menu as Strings
     */
    public static void printMenu(String title, String[] options) {
        System.out.println("\t"+title);
        for (int i = 1; i < options.length; i++) {
            System.out.printf("\t(%d) "+options[i]+"%n",i);
        }
        System.out.printf("\t(%d) "+options[0]+"%n%n",0);
    }

    /**
     * Prints out any type of non-tabular data
     *
     * @param result String with result to be printed
     * @param label label String
     */
    public static void printGeneralResults(String result, String label) {
        System.out.println("\t"+label);
        System.out.println("\t"+result);
    }

    public static void printGeneralList(String result) {
        System.out.println("\t" + result);
    }

    /*
     /--------------------------------\
     |   id   |   product  |   type   |
     |--------|------------|----------|
     |   0    |  Bazooka   | portable |
     |--------|------------|----------|
     |   1    | Sidewinder | missile  |
     \--------------------------------/
    */
    /**
     * Prints tabular data like above example
     *
     * @param table 2 dimensional array to be printed as table
     */
    public static void printTable(String[][] table) {
        Integer maxLength= Arrays.stream(table).flatMap(Stream::of).map(String::length).max(Integer::compareTo).orElse(null);
        Formatter formatter = new Formatter();
        formatter.format("/"+"-".repeat(table[0].length*(maxLength+1))+"\\\n");
        for (int i = 0; i < table.length; i++) {
            StringBuilder formatterStringBuilder=new StringBuilder();
            for (int j = 0; j < table[i].length; j++) {
                formatterStringBuilder.append("|").append(centerString(table[i][j],maxLength));
            }
            formatterStringBuilder.append("|\n");
            formatter.format(formatterStringBuilder.toString());
            if(i!=table.length-1) formatter.format("|"+("-".repeat(maxLength)+"|").repeat(table[0].length)+"\n");
        }
        formatter.format("\\"+"-".repeat(table[0].length*(maxLength+1))+"/\n");
        System.out.println(formatter);
    }

    public static String centerString(String str, int maxLength){
        if(str.length()>=maxLength){
            return  str;
        }
        StringBuilder stringBuilder= new StringBuilder();
        String pad=" ";
        stringBuilder.append(pad.repeat((maxLength-str.length()) / 2));
        stringBuilder.append(str);
        while(stringBuilder.length()<maxLength){
            stringBuilder.append(pad);
        }
        return stringBuilder.toString();
    }

    /**
     * Gets single String input from the user
     *
     * @param label the label before the user prompt
     * @return user input as String
     */
    public static String getInput(String label) {
        System.out.println("\t"+label);
        return scanner.nextLine();
    }

    /**
     * Gets a list of String inputs from the user
     *
     * @param labels array of Strings with the labels to be displayed before each prompt
     * @return array of user inputs
     */
    public static String[] getInputs(String[] labels) {
        String[] input= new String[labels.length];
        for (int i = 0; i < labels.length; i++) {
            System.out.printf("%1d, "+labels[i],i+1);
            input[i]=scanner.nextLine();
        }
        return input;
    }

    /**
     * Prints out error messages to terminal
     *
     * @param message String with error details
     */
    public static void printErrorMessage(String message) {
        System.out.println(ANSI_RED+message+ANSI_RESET);
    }

}

