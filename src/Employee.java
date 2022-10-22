public class Employee {

    private int id;
    private String lastName, firstName, middleName;
    private Department department;
    private float salary;

//  *********

    Employee(EmployeeBook employeeBook, String lastName, String firstName, String middleName, Department department, int salary) {
        if (DataService.parameterIsCorrect(lastName) && DataService.parameterIsCorrect(firstName) && DataService.parameterIsCorrect(middleName)
                && DataService.parameterIsCorrect(department) && DataService.parameterIsCorrect(salary)) {
            this.id = employeeBook.getEmployeesCount(DataService.ServiceMode.INCREASE);
            this.lastName = lastName;
            this.firstName = firstName;
            this.middleName = middleName;
            this.department = department;
            this.salary = salary;
        } else throw new IllegalArgumentException(DataService.INCORRECT_INITIAL_PARAMS_MESS);
    }

//  *********

    public String toString() {
        return String.format("%s; %s %s %s (ID: %d); з/п: %.2f руб.",
                department, lastName, firstName, middleName, id, salary);
    }

    String getFullName() {
        return String.format("%s %s %s (ID: %s)", lastName, firstName, middleName, id);
    }

    //  Убить, если не использую !«
    int getId() {
        return id;
    }

    String getLastName() {
        return lastName;
    }

    String getFirstName() {
        return firstName;
    }

    String getMiddleName() {
        return middleName;
    }

    Department getDepartment() {
        return department;
    }

    void setDepartment(Department department) {
        this.department = department;
    }

    void setSalary(float salary) {
        this.salary = salary;
    }
//  »!

    float getSalary() {
        return salary;
    }

//  *********

    float isRequestedSalary(DataService.RequestMode requestMode) {
        switch (requestMode) {
            case MIN_SALARY:
                if (this.getSalary() < EmployeeBook.getMinSalary()) {
                    EmployeeBook.setMinSalary(this.getSalary());
                }
                return EmployeeBook.getMinSalary();
            case MAX_SALARY:
                if (this.getSalary() > EmployeeBook.getMaxSalary()) {
                    EmployeeBook.setMaxSalary(this.getSalary());
                }
                return EmployeeBook.getMaxSalary();
            default:
                throw new IllegalArgumentException(DataService.INCORRECT_SELECTED_MODE_MESS);
        }
    }

    boolean inThisDepartment(Department department) {
        if (DataService.parameterIsCorrect(department)) {
            return this.department.equals(department);
        } else throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
    }

    void changeSalary(DataService.ServiceMode serviceMode, float value) {
        switch (serviceMode) {
            case PERCENTAGE:
                salary += salary / (100 / value);
                break;
            case CORRECT:
                salary += value;
                break;
            default:
                throw new IllegalArgumentException(DataService.INCORRECT_SELECTED_MODE_MESS);
        }
    }

    boolean canExtract(Employee[] employees, DataService.RequestMode requestMode) {
        switch (requestMode) {
            case FULL_NAME_LIST:
            case SALARY_LIST:
            case CORRECT_SALARY_LIST:
                return true;
            case MIN_SALARY:
            case MAX_SALARY:
                var extractParameter = EmployeeBook.getMinMaxSalary(employees, requestMode);
                if (extractParameter <= 0) {
                    DataService.setPrintTitle(EmployeeBook.NULL_SALARIES_MESS);
                    return false;
                }
                return this.getSalary() == extractParameter;
            default:
                DataService.setPrintTitle(DataService.NO_MATCH_FOUNDS_MESS);
                return false;
        }
    }

    boolean canExtract(DataService.RequestMode requestMode, float extractParam) {
        if (extractParam <= 0) return false;
        switch (requestMode) {
            case LESS_SALARY:
                return this.getSalary() < extractParam;
            case BIG_SALARY:
                return this.getSalary() > extractParam;
            default:
                throw new IllegalArgumentException(DataService.INCORRECT_SELECTED_MODE_MESS);
        }
    }
}
