class EmployeeBook {

    private static int count = 0;
    private int id;
    private String title;

    static final String EQUAL_SALARY_MESS = "\nВсе сотрудники получают одинаковую заработную плату.";
    static final String EQUAL_SALARIES_IN_THIS_DEP_MESS = "\nВсе сотрудники данного Подразделения получают одинаковую заработную плату.";
    static final String NULL_SALARIES_MESS = "\nРазмеры заработных плат сотрудников не указаны.";

    static private float minSalary = Float.MAX_VALUE;
    static private float maxSalary = Float.MIN_VALUE;

//  *********

    EmployeeBook(String title) {
        if (DataService.parameterIsCorrect(title)) {
            this.id = ++count;
            this.title = title;
        }
    }

//  *********

    private final Employee[] employees = new Employee[10];
    private int employeesCount = 0;

    Employee[] emplsArray = employees;

    Employee[] getEmployees() {
        return this.employees;
    }

//  *********

//  Убить, если не использую !«
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    int getEmployeesCount() {
        return this.employeesCount;
    }

    void setEmployeesCount(int value)
    {
        this.employeesCount = value;
    }

    int getNumberOfEmployees(Department department) {
        if (!DataService.parameterIsCorrect(this.getEmployees()) || DataService.parameterIsCorrect(department)) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        Employee[] relEmpls = this.extractRelevantEmployees(DataService.RequestMode.FULL_NAME_LIST, department);
        return relEmpls.length - DataService.nullFounds(relEmpls);
    }
//  »!

    int getEmployeesCount(DataService.ServiceMode mode) {
        switch (mode) {
            case INCREASE:
                return ++this.employeesCount;
            case DECREASE:
                return --this.employeesCount;
            default:
                throw new IllegalArgumentException(DataService.INCORRECT_SELECTED_MODE_MESS);
        }
    }

    public static float getMinSalary() {
        return EmployeeBook.minSalary;
    }

    public static void setMinSalary(float value) {
        EmployeeBook.minSalary = value;
    }

    public static float getMaxSalary() {
        return EmployeeBook.maxSalary;
    }

    public static void setMaxSalary(float value) {
        EmployeeBook.maxSalary = value;
    }

//  *********

    static boolean isEmployees(Object[] objects) {
        return objects instanceof Employee[];
    }

    void changeSalaries(float percent) {
        if (!DataService.parameterIsCorrect(this.getEmployees())) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        for (Employee curEmpl :
                this.getEmployees()) {
            if (DataService.parameterIsCorrect(curEmpl)) {
                curEmpl.changeSalary(DataService.ServiceMode.PERCENTAGE, percent);
            }
        }
    }

    void changeSalaries(Department department, float percent) {
        if (!DataService.parameterIsCorrect(this.getEmployees()) || !DataService.parameterIsCorrect(department)) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        for (Employee curEmpl :
                this.getEmployees()) {
            if (DataService.parameterIsCorrect(curEmpl) && curEmpl.inThisDepartment(department)) {
                curEmpl.changeSalary(DataService.ServiceMode.PERCENTAGE, percent);
            }
        }
    }

    float calculateSalarySum() {
        if (!DataService.parameterIsCorrect(this.getEmployees())) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        int salarySum = 0;
        for (Employee curEmpl :
                this.getEmployees()) {
            if (DataService.parameterIsCorrect(curEmpl)) {
                salarySum += curEmpl.getSalary();
            }
        }
        return salarySum;
    }

    float calculateSalarySum(Department department) {
        if (!DataService.parameterIsCorrect(this.getEmployees()) || !DataService.parameterIsCorrect(department)) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        int salarySum = 0;
        for (Employee curEmpl :
                this.getEmployees()) {
            if (DataService.parameterIsCorrect(curEmpl) && curEmpl.inThisDepartment(department)) {
                salarySum += curEmpl.getSalary();
            }
        }
        return salarySum;
    }

    float getAverageSalary() {
        if (!DataService.parameterIsCorrect(this.getEmployees())) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        return this.calculateSalarySum() / (this.getEmployees().length - DataService.nullFounds(this.getEmployees()));
    }

    float getAverageSalary(Department department) {
        if (!DataService.parameterIsCorrect(this.getEmployees()) || !DataService.parameterIsCorrect(department)) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        return this.calculateSalarySum(department) / (this.getEmployees().length - DataService.nullFounds(this.getEmployees()));
    }

    static float getMinMaxSalary(Employee[] employees, DataService.RequestMode requestMode) {
        if (!DataService.parameterIsCorrect(employees)) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        minSalary = Float.MAX_VALUE;
        maxSalary = Float.MIN_VALUE;
        float requestedSalary = 0.00f;
        for (Employee curEmpl : employees) {
            if (DataService.parameterIsCorrect(curEmpl)) {
                requestedSalary = curEmpl.isRequestedSalary(requestMode);
            }
        }
        return requestedSalary;
    }

    Employee[] extractRelevantEmployees(DataService.RequestMode requestMode) {
        if (!DataService.parameterIsCorrect(this.getEmployees())) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        Employee[] relEmpls = new Employee[this.getEmployees().length - DataService.nullFounds(this.getEmployees())];
        int relEmplIndex = -1;
        for (Employee curEmpl : this.getEmployees()) {
            if (DataService.parameterIsCorrect(curEmpl) && curEmpl.canExtract(this.getEmployees(), requestMode)) {
                relEmpls[++relEmplIndex] = curEmpl;
            }
        }
        if (DataService.arrayIsEmpty(relEmpls)) {
            DataService.setPrintTitle(DataService.NO_MATCH_FOUNDS_MESS);
        }
        return relEmpls;
    }

    Employee[] extractRelevantEmployees(DataService.RequestMode requestMode, Department department) {
        if (!DataService.parameterIsCorrect(this.getEmployees()) || !DataService.parameterIsCorrect(department)) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        Employee[] relEmpls = new Employee[this.getEmployees().length - DataService.nullFounds(this.getEmployees())];
        int relEmplIndex = -1;
        for (Employee curEmpl : this.getEmployees()) {
            if (DataService.parameterIsCorrect(curEmpl) && curEmpl.inThisDepartment(department)
                    && curEmpl.canExtract(this.getEmployees(), requestMode)) {
                relEmpls[++relEmplIndex] = curEmpl;
            }
        }
        if (DataService.arrayIsEmpty(relEmpls)) {
            DataService.setPrintTitle(DataService.NO_MATCH_FOUNDS_MESS);
        }
        return relEmpls;
    }

    Employee[] extractRelevantEmployees(DataService.RequestMode requestMode, float extractParameter) {
        if (!DataService.parameterIsCorrect(this.getEmployees())) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        if (extractParameter < 0) throw new IllegalArgumentException(DataService.INCORRECT_COMPARE_PARAMS_MESS);
        Employee[] relEmpls = new Employee[this.getEmployees().length - DataService.nullFounds(this.getEmployees())];
        int relEmplIndex = -1;
        for (Employee curEmpl : this.getEmployees()) {
            if (DataService.parameterIsCorrect(curEmpl) && curEmpl.canExtract(requestMode, extractParameter)) {
                relEmpls[++relEmplIndex] = curEmpl;
            }
        }
        if (DataService.arrayIsEmpty(relEmpls)) {
            DataService.setPrintTitle(DataService.NO_MATCH_FOUNDS_MESS);
        }
        return relEmpls;
    }

    Employee[] extractRelevantEmployees(DataService.RequestMode requestMode, float extractParameter, Department department) {
        if (!DataService.parameterIsCorrect(this.getEmployees()) || !DataService.parameterIsCorrect(department)) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        if (extractParameter < 0) throw new IllegalArgumentException(DataService.INCORRECT_COMPARE_PARAMS_MESS);
        Employee[] relEmpls = new Employee[this.getEmployees().length - DataService.nullFounds(this.getEmployees())];
        int relEmplIndex = -1;
        for (Employee curEmpl : this.getEmployees()) {
            if (DataService.parameterIsCorrect(curEmpl) && curEmpl.inThisDepartment(department)
                    && curEmpl.canExtract(requestMode, extractParameter)) {
                relEmpls[++relEmplIndex] = curEmpl;
            }
        }
        if (DataService.arrayIsEmpty(relEmpls)) {
            DataService.setPrintTitle(DataService.NO_MATCH_FOUNDS_MESS);
        }
        return relEmpls;
    }

    void printEmployeesList() {
        if (!DataService.parameterIsCorrect(this.getEmployees())) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        DataService.setPrintTitle("\nПолный перечень сведений по сотрудникам:");
        System.out.println(DataService.getPrintTitle());
        DataService.printList(this.getEmployees());
    }

    void printEmployeesList(DataService.RequestMode requestMode) {
        if (!DataService.parameterIsCorrect(this.getEmployees())) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        DataService.getPrintTitle(requestMode);
        Employee[] relEmpls = this.extractRelevantEmployees(requestMode);
        if (DataService.noMatchFoundsReport(relEmpls) || DataService.exceptionalCaseReport()) return;
        System.out.println(DataService.getPrintTitle());
        DataService.printList(DataService.getItemsList(relEmpls, requestMode));
    }

    void printEmployeesList(DataService.RequestMode requestMode, Department department) {
        if (!DataService.parameterIsCorrect(this.getEmployees()) || !DataService.parameterIsCorrect(department)) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        DataService.getPrintTitle(requestMode, department);
        Employee[] relEmpls = this.extractRelevantEmployees(requestMode, department);
        if (DataService.noMatchFoundsReport(relEmpls) || DataService.exceptionalCaseReport()) return;
        System.out.println(DataService.getPrintTitle());
        DataService.printList(DataService.getItemsList(relEmpls, requestMode));
    }

    void printEmployeesList(DataService.RequestMode requestMode, float comparedSalary) {
        if (!DataService.parameterIsCorrect(this.getEmployees())) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        if (comparedSalary < 0) {
            throw new IllegalArgumentException(DataService.INCORRECT_COMPARE_PARAMS_MESS);
        }
        DataService.getPrintTitle(requestMode);
        Employee[] relEmpls = this.extractRelevantEmployees(requestMode, comparedSalary);
        if (DataService.noMatchFoundsReport(relEmpls) || DataService.exceptionalCaseReport()) return;
        System.out.println(DataService.getPrintTitle());
        DataService.printList(DataService.getItemsList(relEmpls, requestMode));
    }

    void printEmployeesList(DataService.RequestMode requestMode, float comparedSalary, Department department) {
        if (!DataService.parameterIsCorrect(this.getEmployees()) || !DataService.parameterIsCorrect(department)) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        if (comparedSalary < 0) {
            throw new IllegalArgumentException(DataService.INCORRECT_COMPARE_PARAMS_MESS);
        }
        DataService.getPrintTitle(requestMode, department);
        Employee[] relEmpls = this.extractRelevantEmployees(requestMode, comparedSalary, department);
        if (DataService.noMatchFoundsReport(relEmpls) || DataService.exceptionalCaseReport()) return;
        System.out.println(DataService.getPrintTitle());
        DataService.printList(DataService.getItemsList(relEmpls, requestMode));
    }
}
