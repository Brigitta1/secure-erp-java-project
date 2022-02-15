package secureerp.model;

import java.time.LocalDate;

public class HRModel {
    private String id;
    private String name;
    private LocalDate birthDate;
    private String department;
    private int clearance;

    public String[] toTableRow() {
        return new String[]{
                id, name, String.valueOf(birthDate), department, String.valueOf(clearance)
        };
    }

    public HRModel(String id, String name, LocalDate birthDate, String department, int clearance) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.department = department;
        this.clearance = clearance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getClearance() { return clearance;     }

    public void setClearance(int clearance) {
        this.clearance = clearance;
    }
}
