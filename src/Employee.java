public class Employee {
    private static int employeesCount = 0;

    private int id;
    private String lastName, firstName, middleName;
    private String department;
    private int salary;

    public Employee(String lastName, String firstName, String middleName, String department, int salary) {
        this.id = ++employeesCount;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.department = department;
        this.salary = salary;
    }

    public String toString() {
        return String.format("Подразделение: %s; %s %s %s (ID: %d); з/п: %d",
                department, lastName, firstName, middleName, id, salary);
    }

    public String getFullName() {
        return String.format("%s %s %s (ID: %s)", lastName, firstName, middleName, id);
    }

    public static int getEmployeesCount() {
        return employeesCount;
    }

    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getDepartment() {
        return department;
    }

    public int getSalary() {
        return salary;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
