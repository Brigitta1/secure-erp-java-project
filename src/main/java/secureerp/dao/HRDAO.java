package secureerp.dao;

import secureerp.Util;
import secureerp.model.HRModel;
import secureerp.view.TerminalView;

import java.io.*;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;


public class HRDAO {
    private final static int ID_TABLE_INDEX = 0;
    private final static int NAME_TABLE_INDEX = 1;
    private final static int BIRTH_DATE_TABLE_INDEX = 2;
    private final static int DEPARTMENT_TABLE_INDEX = 3;
    private final static int CLEARANCE_TABLE_INDEX = 4;
    private final static String DATA_FILE = "src/main/resources/hr.csv";

    private final static List<HRModel> hrModels= new ArrayList<>();
    public static String[] headers = {"Id", "Name", "Date of birth", "Department", "Clearance"};

    /**Read employees' IDs
     *
     * @return employees' IDs in a string array
     */

    public static String[] getEmployeesIDs(){
        String[] IDs = new String[hrModels.size()];
        for (int i = 0; i < IDs.length; i++) {
            IDs[i]= hrModels.get(i).getId();
        }
        return IDs;
    }

    /**Create a HRModel list from a String array
     */

    public static void readEmployeesFromFile(){
        List<String> users=Util.readDatabaseFromFile(DATA_FILE);
        for (String user : users) {
            String[] tempString=user.split(";");
            hrModels.add(new HRModel(
                    tempString[ID_TABLE_INDEX],
                    tempString[NAME_TABLE_INDEX],
                    LocalDate.parse(tempString[BIRTH_DATE_TABLE_INDEX]),
                    tempString[DEPARTMENT_TABLE_INDEX],
                    Integer.parseInt(tempString[CLEARANCE_TABLE_INDEX])
            ));
        }
    }

    /**Creat a 2D String array for TerminalView.printTable
     *
     * @return 2D String array
     */

    public static String[][] createPrintableTable(){
        String[][] printableTable= new String[hrModels.size()+1][headers.length];
        printableTable[0]=headers;
        for (int i = 0; i < hrModels.size(); i++) {
            printableTable[i+1]=hrModels.get(i).toTableRow();
        }
        return printableTable;
    }

    /**Write a HRModel employees to hr.csv file
     */

    public static void writeEmployeesToFile(){
        try (BufferedWriter bufferedWriter=
                     new BufferedWriter(new FileWriter(String.valueOf(Util.getPath(DATA_FILE))))){
            for (HRModel hrModel : hrModels) {
                bufferedWriter.write(String.join(";",hrModel.toTableRow()));
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
        hrModels.clear();
    }

    /**Update an employee by id
     *
     * @param updatedEmployee values for change
     * @param employeeID ID of employee
     */
    public static void updateEmployee(String employeeID, String[] updatedEmployee){
        int employeeIndex=Arrays.asList(getEmployeesIDs()).indexOf(employeeID);
        if(!Objects.equals(updatedEmployee[NAME_TABLE_INDEX], "")) hrModels.get(employeeIndex).setName(updatedEmployee[NAME_TABLE_INDEX]);
        if(!Objects.equals(updatedEmployee[BIRTH_DATE_TABLE_INDEX], ""))hrModels.get(employeeIndex).setBirthDate(LocalDate.parse(updatedEmployee[BIRTH_DATE_TABLE_INDEX]));
        if(!Objects.equals(updatedEmployee[DEPARTMENT_TABLE_INDEX], ""))hrModels.get(employeeIndex).setDepartment(updatedEmployee[DEPARTMENT_TABLE_INDEX]);
        if(!Objects.equals(updatedEmployee[CLEARANCE_TABLE_INDEX], ""))hrModels.get(employeeIndex).setClearance(Integer.parseInt(updatedEmployee[CLEARANCE_TABLE_INDEX]));
    }

    /**Check if the selected ID is contained in the list
     *
     * @param employeeID ID to check
     * @return contains
     */
    public static boolean checkIfSelectedIDValid(String employeeID){
        return Arrays.asList(getEmployeesIDs()).contains(employeeID);
    }

    /**Delete an employee by id
     *
     * @param employeeID to delete
     */
    public static void deleteEmployee(String employeeID){
        int employeeIndex;
        if(Arrays.asList(getEmployeesIDs()).contains(employeeID)) {
            employeeIndex=Arrays.asList(getEmployeesIDs()).indexOf(employeeID);
            hrModels.remove(employeeIndex);
        }else {
            throw new NoSuchElementException("Employee not found by the ID: "+employeeID);
        }
    }

    /**Create new employee and pass to writeEmployeeToFile
     *
     * @param employeeWannabe String array to convert HRModel
     */

    public static void createEmployee(String[] employeeWannabe){
        try{
            HRModel newEmployee= new HRModel(
                    employeeWannabe[ID_TABLE_INDEX],
                    employeeWannabe[NAME_TABLE_INDEX],
                    LocalDate.parse(employeeWannabe[BIRTH_DATE_TABLE_INDEX]),
                    employeeWannabe[DEPARTMENT_TABLE_INDEX],
                    Integer.parseInt(employeeWannabe[CLEARANCE_TABLE_INDEX])
            );
            hrModels.add(newEmployee);
        }catch (Exception e){
            TerminalView.printErrorMessage("\n Recording new employee was UNSUCCESSFUL. Please enter the date in this format: 1990-01-01 \n");
        }
    }

    public static String[][] oldestYoungestEmployees(){
        int indexOfYoungest=0;
        int indexOfOldest=0;
        for (int i = 1; i < hrModels.size(); i++) {
            if(hrModels.get(i).getBirthDate().isBefore(hrModels.get(indexOfOldest).getBirthDate())){
                indexOfOldest=i;
            }
            if(hrModels.get(i).getBirthDate().isAfter(hrModels.get(indexOfYoungest).getBirthDate())){
                indexOfYoungest=i;
            }
        }
        return new String[][] {headers,hrModels.get(indexOfYoungest).toTableRow(),hrModels.get(indexOfOldest).toTableRow()};
    }

    public static int getAverageAge(){
        int averageAge=0;
        LocalDate currentDate = LocalDate.now();
        Period period;
        for (HRModel hrModel : hrModels) {
            period= Period.between(hrModel.getBirthDate(),currentDate);
            averageAge+=Math.abs(period.getYears());
        }
        return averageAge/hrModels.size();
    }

    public static int[] clearanceOfEmployee(){
        int[] clearances = new int[hrModels.size()];
        for (int i = 0; i < clearances.length; i++) {
            clearances[i]= hrModels.get(i).getClearance();
        }
        return clearances;
    }

    public static Map employeesPerDepartment(){
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < hrModels.size(); i++) {
            map.put(hrModels.get(i).getDepartment(), map.getOrDefault(hrModels.get(i).getDepartment(), 0) + 1);
        }
        return map;
    }
    public static String nextBirthday() {
        LocalDate todayDate = LocalDate.now();
        long todayDateInLong = todayDate.toEpochDay();
        LocalDate todayPlusTwoWeeks = todayDate.plusWeeks(2);
        long todayPlusTwoWeeksInLong = todayPlusTwoWeeks.toEpochDay();
        int thisYear = todayDate.getYear();
        String thisYearString = String.valueOf(thisYear);
        for (int i = 0; i < hrModels.size(); i++) {
            String employeeName = hrModels.get(i).getName();
            LocalDate birthdayOfEmployee = hrModels.get(i).getBirthDate();
            String birthDayString = String.valueOf(birthdayOfEmployee);
            String [] birthDayStringArray = birthDayString.split("-");
            birthDayStringArray[0] = thisYearString;
            String newBirthDay = String.join("-", birthDayStringArray);
            LocalDate newBirthDayInLDate = LocalDate.parse(newBirthDay);
            String message = "Birthday of " + employeeName + " is: " + birthdayOfEmployee;
            long birthdayOfEmployeeInLong = newBirthDayInLDate.toEpochDay();
            if (todayDateInLong <= birthdayOfEmployeeInLong && birthdayOfEmployeeInLong <= todayPlusTwoWeeksInLong) {
                return message;
            }
        }

        return "There is no birthday in the next two weeks period!";
    }
}