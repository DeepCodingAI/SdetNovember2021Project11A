package database;

public class Employee {
    private String empName;
    private String empID;
    private String empDOB;
    private String empSalary;

    public Employee(){}
    public Employee(String empName, String empID, String empDOB, String empSalary) {
        this.empName = empName;
        this.empID = empID;
        this.empDOB = empDOB;
        this.empSalary = empSalary;
    }


    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getEmpDOB() {
        return empDOB;
    }

    public void setEmpDOB(String empDOB) {
        this.empDOB = empDOB;
    }

    public String getEmpSalary() {
        return empSalary;
    }

    public void setEmpSalary(String empSalary) {
        this.empSalary = empSalary;
    }



}
