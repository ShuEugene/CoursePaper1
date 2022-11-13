public class Employee {

    private int id;
    private String lastName, firstName, middleName;
    private Department department;
    private float salary;


    Employee(String lastName, String firstName, String middleName, Department department, float salary) {
        if (!DataService.paramIsCorrect(lastName) || !DataService.paramIsCorrect(firstName) || !DataService.paramIsCorrect(middleName)
                || department == null) {
            System.out.println(DataService.INCORRECT_INITIAL_PARAMS_MESS);
            return;
        }
        this.id = EmployeeBook.getEmplsCount(DataService.ServiceModes.INCREASE);
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.department = department;
        this.salary = salary;
    }


    String getFullName() {
        return String.format("%s %s %s", lastName, firstName, middleName);
    }

    int getId() {
        return id;
    }

    String getIDCard() {
        return String.format("%s (ID: %d)", getFullName(), id);
    }

    String getPersonalCard() {
        return String.format("%s %s %s (ID: %s; Подразделение: %s)", lastName, firstName, middleName, id, department);
    }

    public String toString() {
        return String.format("%s; з/п: %s",
                getPersonalCard(), getStrSalary());
    }

    float getSalary() {
        return salary;
    }

    String getStrSalary() {
        return String.format("%.2f %s", salary, EmployeeBook.currencyType);
    }

    void setSalary(float newValue) {
        if (newValue < 0) {
//                  здесь можно собирать остаток (задолженности сотрудников перед Компанией)
//                    EmployeeBook.setIsDoneMess(DataService.NEGATIVE_VALUE_MESS);
            System.out.printf("%s %.2f руб..\n", EmployeeBook.LEFT_OVER_FUNDS_MESS, salary + newValue);
            salary = 0;
        } else salary = newValue;
        if (salary == 0) EmployeeBook.setNullSalariesCount(DataService.ServiceModes.INCREASE);
        else EmployeeBook.setNullSalariesCount(DataService.ServiceModes.DECREASE);
    }

    void setDepartment(Department department) {
        if (Departments.isInvalidDepartment(department)) {
            System.out.println(Departments.DEP_NOT_EXIST_MESS);
            return;
        }
        this.department = department;
        System.out.printf("\nСотрудник (%s) переведён в Подразделение %s.\n",
                EmployeeBook.getOperEmpl().getIDCard(), department);
    }


    float getRequestedSalary(EmployeeBook.RequestModes requestMode) {
        if (salary == 0) {
            EmployeeBook.setNullSalariesCount(DataService.ServiceModes.INCREASE);
            return 0;
        }
        switch (requestMode) {
            case MIN_SALS_RM:
                if (salary < EmployeeBook.getMinSalary()) {
                    EmployeeBook.setMinSalary(salary);
                }
                return EmployeeBook.getMinSalary();
            case MAX_SALS_RM:
                if (salary > EmployeeBook.getMaxSalary()) {
                    EmployeeBook.setMaxSalary(salary);
                }
                return EmployeeBook.getMaxSalary();
            default:
                throw new IllegalArgumentException(DataService.INCORRECT_SELECTED_MODE_MESS);
        }
    }

    boolean inThisDepartment(Department department) {
        if (department != null) {
            return this.department.equals(department);
        } else throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
    }

    void changeSalary(DataService.ServiceModes serviceMode, float value) {
        float oldValue = salary;
        float newValue = salary;
        float changeValue = 0;
        switch (serviceMode) {
            case SET_VALUE:
                changeValue = value - oldValue;
                break;
            case PERCENTAGE:
                if (salary > 0) {
                    changeValue = salary / (100 / value);
                } else if (salary < 0) {
                    changeValue = -oldValue;
                }
                break;
            case CORRECT:
                changeValue = value;
                break;
            case INCREASE:
                if (value < 0) {
                    System.out.println("\nУвеличение на отрицательную величину исключено.");
                    break;
                }
                changeValue = value;
                break;
            case DECREASE:
                if (value > 0) {
                    System.out.println("Уменьшение на положительную величину исключено.");
                    break;
                }
                changeValue = value;
            default:
                throw new IllegalArgumentException(DataService.INCORRECT_SELECTED_MODE_MESS);
        }
        newValue += changeValue;
        if (newValue != oldValue) {
            EmployeeBook.setCorrectedSalariesCount(DataService.ServiceModes.INCREASE);
            setSalary(newValue);
        }
    }

    boolean canExtract(EmployeeBook.RequestModes requestMode, float extractParam) {
        switch (requestMode) {
            case PERS_CARDS_RM:
                return true;
            case SALARIES_RM:
            case CORRECT_SALS_RM:
                if (salary == 0) {
                    EmployeeBook.setNullSalariesCount(DataService.ServiceModes.INCREASE);
                }
                return true;
            case MIN_SALS_RM:
            case MAX_SALS_RM:
                if (salary == 0 || extractParam == 0) return false;
                return salary == extractParam;
            case LESS_SALS_RM:
                if (extractParam <= 0 || salary < 0) {
                    return false;
                } else if (salary == 0) {
                    EmployeeBook.setNullSalariesCount(DataService.ServiceModes.INCREASE);
                    return false;
                }
                return salary < extractParam;
            case BIG_SALS_RM:
                if (extractParam <= 0 || salary < 0) {
                    return false;
                } else if (salary == 0) {
                    EmployeeBook.setNullSalariesCount(DataService.ServiceModes.INCREASE);
                    return false;
                }
                return salary > extractParam;
            default:
                DataService.setPrintTitle(DataService.INCORRECT_SELECTED_MODE_MESS);
                return false;
        }
    }
}
