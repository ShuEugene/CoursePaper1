import java.io.IOException;

class EmployeeBook {

    private int id;
    private String title;
    private final Employee[] employees = new Employee[25];
    private int emplsNumber = 0;
    private int nullSalariesNumber = 0;

    EmployeeBook(String title) {
        if (!DataService.paramIsCorrect(title)) {
            System.out.println(DataService.INCORRECT_INITIAL_PARAMS_MESS);
            return;
        }
        id = ++booksCount;
        this.title = title;
        String[] departments = {"1", "2", "3", "4", "5"};
        Departments.declare(departments);

        Departments.firstDep = Departments.getDepartments()[0];
        Departments.secondDep = Departments.getDepartments()[1];
        Departments.thirdDep = Departments.getDepartments()[2];
        Departments.fourthDep = Departments.getDepartments()[3];
        Departments.fifthDep = Departments.getDepartments()[4];

        employees[0] = new Employee("Директоров", "Важан", "Капиталюгович", Departments.firstDep, 125_000);
        employees[1] = new Employee("Директорова", "Светлана", "Инициативовна", Departments.secondDep, 75_000);
        employees[2] = new Employee("Бухгалтерова", "Илана", "Счетоводовна", Departments.secondDep, 75_000);
        employees[3] = new Employee("Новичкова", "Смазлюшка", "Смекалковна", Departments.thirdDep, 0);
        employees[4] = new Employee("Бухпомощникова", "Неумейка", "Научуськовна", Departments.secondDep, 75_000);
        employees[5] = new Employee("Мастеров", "Златомысл", "Идейкович", Departments.fifthDep, 100_000);
        employees[6] = new Employee("Бригадиров", "Расслабон", "Халявович", Departments.fifthDep, 75_000);
        employees[7] = new Employee("Златорук", "Трудогол", "Ответственникович", Departments.fifthDep, 100_000);
        employees[8] = new Employee("Новичкова", "Смазлюшка", "Смекалковна", Departments.thirdDep, 0);
        employees[9] = new Employee("Рукожоп", "Принесиподай", "Растыкович", Departments.fifthDep, 50_000);

        emplsNumber = emplsCount;
    }

    enum RequestModes {
        ALL_INFO_RM, PERS_CARDS_RM, FIO_MATCH_FOUND_RM, ADD_EMPL_RM, DISM_EMPL_RM, SELECT_EMPL_RM, DEPS_EMPLS_RM,
        SALS_SUM_RM, AVER_SAL_RM, SALARIES_RM, CORRECT_SALS_RM, MIN_SALS_RM, MAX_SALS_RM, LESS_SALS_RM, BIG_SALS_RM;
    }

    enum ListTypes {ALL_INFO_LT, FULL_NAMES_LT, NAMES_N_ID_LT, PERSONAL_CARDS_LT, SALARIES_LT, SALARIES_DEP_LT, DEP_NAME_EXCLUDE_LT}

    static final String currencyType = "руб.";

    static private final String rmTitleMark = "~~~";
    static private String rmTitle;

    static final String SALARIES_SUM_PT = "\nОбъём ежемесячных выплат заработной платы";
    static final String AVERAGE_SALARY_PT = "\nСредняя заработная плата";

    static final String ALL_EMPLS_INFO_PT = "\nПолный перечень сведений о сотрудниках";
    static final String DEPS_EMPLS_INFO_PT = "\nСведения о составе Подразделений:";
    static final String PERS_CARDS_PT = "\nЛичные карточки сотрудников (Ф.И.О., id, ...)";
    static final String SALARIES_PT = "\nРазмеры заработных плат сотрудников";
    static final String CORRECT_SALS_PT = "\nПоправленные размеры заработных плат сотрудников";
    static final String MIN_SALS_PT = "\nСотрудники, получающие наименьшую заработную плату";
    static final String MAX_SALS_PT = "\nСотрудники, получающие наибольшую заработную плату";
    static final String LESS_SALS_PT = "\nСотрудники, получающие заработную плату меньше";
    static final String BIG_SALS_PT = "\nСотрудники, получающие заработную плату больше";

    static final String NO_MATCH_FOUNDS_DEP_MESS = "\nЗапрошенные по данному Подразделению данные отсутствуют.";
    static final String NO_ID_MATCHES_MESS = "\nВведённое значение не соответствует ни одному ID перечисленных сотрудников.";
    static final String EMPL_MATCH_FOUND_MESS = "\nЗапись по сотруднику с указанными данными уже существует:";
    static final String EQUAL_SALARY_MESS = "\nВсе сотрудники Компании получают одинаковую заработную плату.";
    static final String EQUAL_SALARIES_IN_THIS_DEP_MESS = "\nВсе сотрудники Подразделения получают одинаковую заработную плату.";
    static final String NULL_SALARIES_MESS = "\nРазмеры заработных плат сотрудников не указаны.";
    static final String NULL_SALARIES_DEP_MESS = "\nРазмеры заработных плат сотрудников данного Подразделения не указаны.";
    static final String NULL_SALS_FOUNDS_MESS = "\nНайдены записи c нулевыми зарплатами:";
    static final String RESULT_EXCLUDES_NULL_SALS_MESS = "\nРезультат получен, предварительно исключив записи сотрудников с нулевыми зарплатами:";
    static final String LEFT_OVER_FUNDS_MESS = "\nОстаток по операции:";
    static final String EMPLS_BOOK_IS_FULL_MESS = "\nВ Книге учёта отсутствуют свободные строки для внесения записи.";
    static final String NO_RECORDS_LEFT_MESS = "\nВ Книге учёта не осталось ни одной записи.";
    static final String SELECT_EMPL_ID_MESS = "\nВведите ID сотрудника, запись которого следует изменить," +
            "\nлибо «0»/«Enter» - для завершения операции:";
    static final String NO_SELECTED_EMPLS_MESS = "\nНе выбран ни один из сотрудников.";

    static private String isDoneMess;
    static private String reportMess;

    static private short booksCount = 0;
    static private int emplsCount = 0;
    static private float targetSalary = 0.00f;
    static private int nullSalariesCount = 0;
    static private int correctedSalariesCount = 0;
    static private Employee[] nullSalaryEmpls = null;
    static private float minSalary = Float.MAX_VALUE;
    static private float maxSalary = Float.MIN_VALUE;
    static private Employee operEmpl;
    static private Employee[] operGroupRecords = null;
    static private boolean byDepartment = false;
    static private Department requestedDepartment;


    static boolean isByDepartment() {
        return byDepartment;
    }

    public static float getTargetSalary() {
        return targetSalary;
    }

    public static Employee getOperEmpl() {
        return operEmpl;
    }

    static Department getRequestedDepartment() {
        return requestedDepartment;
    }

    static int getCorrectedSalariesCount() {
        return correctedSalariesCount;
    }

    static void resetCorrectedSalsCount() {
        correctedSalariesCount = 0;
    }

    static void setCorrectedSalariesCount(DataService.ServiceModes serviceModes) {
        switch (serviceModes) {
            case INCREASE:
                ++correctedSalariesCount;
                break;
            case DECREASE:
                --correctedSalariesCount;
                break;
            default:
                throw new IllegalArgumentException(DataService.INCORRECT_SELECTED_MODE_MESS);
        }
    }

//  *********

    static String getIsDoneMess() {
        return isDoneMess;
    }

    static int getEmplsCount(DataService.ServiceModes mode) {
        switch (mode) {
            case INCREASE:
                return ++emplsCount;
            case DECREASE:
                return --emplsCount;
            default:
                throw new IllegalArgumentException(DataService.INCORRECT_SELECTED_MODE_MESS);
        }
    }

    static float getMinSalary() {
        return EmployeeBook.minSalary;
    }

    static void setMinSalary(float value) {
        EmployeeBook.minSalary = value;
    }

    static float getMaxSalary() {
        return EmployeeBook.maxSalary;
    }

    static void setMaxSalary(float value) {
        EmployeeBook.maxSalary = value;
    }

    static void setIsDoneMess(String isDoneMess) {
        EmployeeBook.isDoneMess = isDoneMess;
    }

    static void setNullSalariesCount(DataService.ServiceModes serviceMode) {
        switch (serviceMode) {
            case INCREASE:
                ++nullSalariesCount;
                break;
            case DECREASE:
                --nullSalariesCount;
                break;
            default:
                throw new IllegalArgumentException(DataService.INCORRECT_SELECTED_MODE_MESS);
        }
    }

    public static void setReportMess(String reportMess) {
        EmployeeBook.reportMess = reportMess;
    }

//  *********

    static boolean isEmployees(Object[] objects) {
        return objects instanceof Employee[];
    }

    static void resetNullSalsCount() {
        nullSalariesCount = 0;
    }

    int calculateNullSalariesNumber(Employee[] employees) {
        if (!DataService.paramIsCorrect(employees)) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        resetNullSalsCount();
        for (Employee curEmpl : employees) {
            if (curEmpl != null && curEmpl.getSalary() == 0) {
                setNullSalariesCount(DataService.ServiceModes.INCREASE);
            }
        }
        return nullSalariesCount;
    }

    void setNullSalariesNumber(RequestModes requestMode) {
        switch (requestMode) {
            case SALARIES_RM:
            case CORRECT_SALS_RM:
            case MIN_SALS_RM:
            case MAX_SALS_RM:
                nullSalariesNumber = nullSalariesCount;
                break;
            case LESS_SALS_RM:
            case BIG_SALS_RM:
                nullSalariesNumber = calculateNullSalariesNumber(operGroupRecords);
        }
    }

    void nullSalaryReport() {
        if (operGroupRecords != null) {
            nullSalaryEmpls = getNullSalaryEmpls(operGroupRecords);
            if (nullSalaryEmpls != null) {
                printNullSalaryEmplsList();
            }
        }
        nullSalaryEmpls = null;
        nullSalariesNumber = 0;
    }

    void nullSalaryReport(RequestModes requestMode) {
        switch (requestMode) {
            case SALARIES_RM:
            case CORRECT_SALS_RM:
            case MIN_SALS_RM:
            case MAX_SALS_RM:
            case LESS_SALS_RM:
            case BIG_SALS_RM:
                nullSalaryReport();
        }
    }

    void changeSalaries(Department department, float value, DataService.ServiceModes serviceMode) {
        correctedSalariesCount = 0;
        if (value == 0) return;
        if (!DataService.paramIsCorrect(requestByDepartment(department))) {
            System.out.println(NO_MATCH_FOUNDS_DEP_MESS);
            return;
        }
        resetNullSalsCount();
        for (Employee curEmpl :
                operGroupRecords) {
            if (curEmpl != null) {
                curEmpl.changeSalary(serviceMode, value);
            }
        }
        nullSalariesNumber = nullSalariesCount;
    }

    void changeSalaries(float value, DataService.ServiceModes serviceMode) {
        changeSalaries(null, value, serviceMode);
    }

    float calculateSalariesSum(Employee[] employees) {
        if (!DataService.paramIsCorrect(employees)) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        resetNullSalsCount();
        float salariesSum = 0, curSalary = 0;
        for (Employee curEmpl :
                employees) {
            if (curEmpl != null) {
                curSalary = curEmpl.getSalary();
                salariesSum += curSalary;
                if (curSalary == 0) {
                    setNullSalariesCount(DataService.ServiceModes.INCREASE);
                }
            }
        }
        nullSalariesNumber = nullSalariesCount;
        return salariesSum;
    }

    float getSalariesSum() {
        return calculateSalariesSum(employees);
    }

    float getAverageSalary(Employee[] employees) {
        if (!DataService.paramIsCorrect(employees)) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        float salsSum = calculateSalariesSum(employees);
        if (salsSum != 0) {
            return salsSum / (getValidRecordsNumber(employees) - calculateNullSalariesNumber(employees));
        } else {
            isDoneMess = NULL_SALARIES_MESS;
            return 0;
        }
    }

    float getAverageSalary() {
        return getAverageSalary(employees);
    }

    static float getMinMaxSalary(Employee[] employees, RequestModes requestMode) {
        if (!DataService.paramIsCorrect(employees)) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        resetNullSalsCount();
        minSalary = Float.MAX_VALUE;
        maxSalary = Float.MIN_VALUE;
        float requestedSalary = 0.00f;
        for (Employee curEmpl : employees) {
            if (curEmpl != null) {
                requestedSalary = curEmpl.getRequestedSalary(requestMode);
            }
        }
        return targetSalary = requestedSalary;
    }

    static void getMatchFoundMess(Employee[] relEmpls) {
        if (relEmpls.length < 1) {
            isDoneMess = DataService.NO_MATCHES_FOUND_MESS;
        } else if (relEmpls.length == 1) {
            isDoneMess = DataService.ONE_MATCH_FOUND_MESS;
        } else isDoneMess = DataService.MULTIPLE_MATCH_FOUND_MESS;
    }

    int getValidRecordsNumber(Employee[] employees) {
        return DataService.getValidObjectsNumber(employees);
    }

    Employee[] getValidRecords(Employee[] employees) {
        return DataService.getValidObjects(employees);
    }

    Employee[] requestByDepartment(Department department) {
        if (department == null) {
            byDepartment = false;
            requestedDepartment = null;
            operGroupRecords = employees;
        } else {
            byDepartment = true;
            requestedDepartment = department;
            operGroupRecords = extractRelevantEmpls(department);
        }
        return operGroupRecords;
    }

    Employee[] extractRelevantEmpls(Department department) {
        if (department == null) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        operGroupRecords = new Employee[getValidRecordsNumber(employees)];
        int relEmplIndex = -1;
        for (Employee curEmpl : employees) {
            if (curEmpl != null && curEmpl.inThisDepartment(department)) {
                operGroupRecords[++relEmplIndex] = curEmpl;
            }
        }
        operGroupRecords = getValidRecords(operGroupRecords);
        if (!DataService.paramIsCorrect(operGroupRecords)) return null;
        else return operGroupRecords;
    }

    Employee[] extractRelevantEmpls(RequestModes requestMode, float extractParameter) {
        if (!DataService.paramIsCorrect(operGroupRecords)) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        if (extractParameter < 0) throw new IllegalArgumentException(DataService.INCORRECT_COMPARE_PARAMS_MESS);
        Employee[] relEmpls = new Employee[getValidRecordsNumber(operGroupRecords)];
        int relEmplIndex = -1;
        if (extractParameter == 0) {
            switch (requestMode) {
                case MIN_SALS_RM:
                case MAX_SALS_RM:
                    extractParameter = getMinMaxSalary(operGroupRecords, requestMode);
            }
        }
        for (Employee curEmpl : operGroupRecords) {
            if (curEmpl != null && curEmpl.canExtract(requestMode, extractParameter)) {
                relEmpls[++relEmplIndex] = curEmpl;
            }
        }
        relEmpls = getValidRecords(relEmpls);
        if (relEmpls == null) {
            isDoneMess = DataService.NO_MATCHES_FOUND_MESS;
        } else if ((requestMode == RequestModes.MIN_SALS_RM || requestMode == RequestModes.MAX_SALS_RM) && relEmpls.length == operGroupRecords.length) {
            isDoneMess = byDepartment ? EQUAL_SALARIES_IN_THIS_DEP_MESS : EQUAL_SALARY_MESS;
        } else {
            isDoneMess = DataService.WELL_DONE_MESS;
        }
        setNullSalariesNumber(requestMode);
        if (nullSalariesNumber > 0) {
            if (nullSalariesNumber >= operGroupRecords.length) {
                isDoneMess = byDepartment ? NULL_SALARIES_DEP_MESS : NULL_SALARIES_MESS;
            } else isDoneMess = NULL_SALS_FOUNDS_MESS;
        }
        return relEmpls;
    }

    Employee[] extractRelevantEmpls(String lastName, String firstName, String middleName) {
        Employee[] relEmpls = new Employee[getValidRecordsNumber(employees)];
        int relEmplIndex = -1;
        String fullName = lastName + " " + firstName + " " + middleName;
        for (Employee curEmpl : employees) {
            if (curEmpl != null && curEmpl.getFullName().equals(fullName)) {
                relEmpls[++relEmplIndex] = curEmpl;
            }
        }
        relEmpls = getValidRecords(relEmpls);
        getMatchFoundMess(relEmpls);
        return relEmpls;
    }

    String[] getEmplsList(Employee[] employees, ListTypes listType) {
        String[] emplsListItems = new String[getValidRecordsNumber(employees)];
        int itemIndex = -1;
        for (Employee curEmpl : employees) {
            if (curEmpl != null) {
                switch (listType) {
                    case ALL_INFO_LT:
                        emplsListItems[++itemIndex] = curEmpl.toString();
                        break;
                    case FULL_NAMES_LT:
                        emplsListItems[++itemIndex] = curEmpl.getFullName();
                        break;
                    case NAMES_N_ID_LT:
                        emplsListItems[++itemIndex] = String.format("%s (ID: %d)", curEmpl.getFullName(), curEmpl.getId());
                        break;
                    case PERSONAL_CARDS_LT:
                        emplsListItems[++itemIndex] = curEmpl.getPersonalCard();
                        break;
                    case SALARIES_LT:
                        emplsListItems[++itemIndex] = String.format("%s - %s", curEmpl.getPersonalCard(), curEmpl.getStrSalary());
                        break;
                    case SALARIES_DEP_LT:
                        emplsListItems[++itemIndex] = String.format("%s - %s", curEmpl.getFullName(), curEmpl.getStrSalary());
                        break;
                    case DEP_NAME_EXCLUDE_LT:
                        emplsListItems[++itemIndex] = String.format("%s (ID: %d), з/п: %s", curEmpl.getFullName(), curEmpl.getId(), curEmpl.getStrSalary());
                        break;
                    default:
                        throw new IllegalArgumentException(DataService.INCORRECT_SELECTED_MODE_MESS);
                }
            }
        }
        return DataService.getValidObjects(emplsListItems);
    }

    String[] getEmplsList(Employee[] employees) {
        return getEmplsList(employees, ListTypes.ALL_INFO_LT);
    }

    Employee[] getNullSalaryEmpls(Employee[] employees) {
        if (employees == null) return null;
        nullSalaryEmpls = new Employee[getValidRecordsNumber(employees)];
        int emplIndex = -1;
        for (Employee curEmpl :
                employees) {
            if (curEmpl != null && curEmpl.getSalary() == 0) {
                nullSalaryEmpls[++emplIndex] = curEmpl;
            }
        }
        nullSalaryEmpls = getValidRecords(nullSalaryEmpls);
        return nullSalaryEmpls;
    }

    boolean bookIsEmptyReport() {
        if (emplsNumber == 0) {
            System.out.println(NO_RECORDS_LEFT_MESS);
            return true;
        } else if (emplsNumber < 0) {
            throw new RuntimeException("Превышение допустимого обнуления на " + emplsNumber + " записей.");
        }
        return false;
    }

    static boolean noPrintRMTitle(RequestModes requestModes) {
        switch (requestModes) {
            case ALL_INFO_RM:
            case PERS_CARDS_RM:
                rmTitle = String.format("\n%s Вывод сведений о сотрудниках %s", rmTitleMark, rmTitleMark);
                break;
            case CORRECT_SALS_RM:
                rmTitle = String.format("\n%s Правка размеров заработных плат %s", rmTitleMark, rmTitleMark);
                break;
            case DEPS_EMPLS_RM:
                rmTitle = String.format("\n%s Сведения о составе Подразделений %s", rmTitleMark, rmTitleMark);
                break;
            case SALARIES_RM:
                rmTitle = String.format("\n%s Сведения о заработной плате сотрудников %s", rmTitleMark, rmTitleMark);
                break;
            case SALS_SUM_RM:
                rmTitle = String.format("\n%s Вычисление размера выплат по заработным платам %s", rmTitleMark, rmTitleMark);
                break;
            case AVER_SAL_RM:
                rmTitle = String.format("\n%s Вычисление размера средней заработной платы %s", rmTitleMark, rmTitleMark);
                break;
            case MIN_SALS_RM:
            case MAX_SALS_RM:
                rmTitle = String.format("\n%s Поиск минимальной заработной платы %s", rmTitleMark, rmTitleMark);
                if (requestModes == RequestModes.MAX_SALS_RM) {
                    rmTitle = rmTitle.replace(" минимальной ", " максимальной ");
                }
                break;
            case LESS_SALS_RM:
            case BIG_SALS_RM:
                rmTitle = String.format("\n%s Поиск сотрудников с заработной платой, меньше указанной %s", rmTitleMark, rmTitleMark);
                if (requestModes == RequestModes.BIG_SALS_RM) {
                    rmTitle = rmTitle.replace(" меньше", " больше");
                }
                break;
            case SELECT_EMPL_RM:
                rmTitle = String.format("\n%s Поиск записи по сотруднику %s", rmTitleMark, rmTitleMark);
                break;
            case ADD_EMPL_RM:
                rmTitle = String.format("\n%s Добавление записи о новом сотруднике %s", rmTitleMark, rmTitleMark);
                break;
            case DISM_EMPL_RM:
                rmTitle = String.format("\n%s Обнуление записи сотрудника %s", rmTitleMark, rmTitleMark);
                break;
            default:
                System.out.println(DataService.INCORRECT_SELECTED_MODE_MESS);
                return true;
        }
        System.out.println("\n" + rmTitle);
        rmTitle = null;
        return false;
    }

    void printNullSalaryEmplsList() {
        if (!DataService.paramIsCorrect(nullSalaryEmpls)) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        String[] nullSalaryEmplsList = getEmplsList(nullSalaryEmpls, ListTypes.PERSONAL_CARDS_LT);
        if (DataService.paramIsCorrect(nullSalaryEmplsList)) {
            System.out.println(reportMess);
            DataService.printList(nullSalaryEmplsList, DataService.PrintModes.NUMBERED_LIST_PM);
        }
    }

    void printListIfValid(Employee[] relEmpls, RequestModes requestMode) {
        DataService.noMatchFoundsReport(relEmpls);
        DataService.exceptionalCaseReport();
        if (!isDoneMess.equals(DataService.WELL_DONE_MESS) && !isDoneMess.equals(NULL_SALS_FOUNDS_MESS)) return;
        printEmplsList(relEmpls, requestMode);
    }

    void printSalaryIndicator(RequestModes requestMode, Department department) {
        if (noPrintRMTitle(requestMode)) return;
        if (!DataService.paramIsCorrect(requestByDepartment(department))) {
            System.out.println(NO_MATCH_FOUNDS_DEP_MESS);
            return;
        }
        float salaryIndicator = 0;
        DataService.setPrintTitle(requestMode);
        switch (requestMode) {
            case SALS_SUM_RM:
                salaryIndicator = calculateSalariesSum(operGroupRecords);
                break;
            case AVER_SAL_RM:
                salaryIndicator = getAverageSalary(operGroupRecords);
                break;
        }
        if (salaryIndicator <= 0) {
            System.out.println(NULL_SALARIES_MESS);
            return;
        }
        System.out.printf("%s %.2f %s.%n", DataService.getPrintTitle(), salaryIndicator, currencyType);
        reportMess = requestMode == RequestModes.AVER_SAL_RM ? RESULT_EXCLUDES_NULL_SALS_MESS : NULL_SALS_FOUNDS_MESS;
        nullSalaryReport();
        byDepartment = false;
    }

    void printSalaryIndicator(RequestModes requestMode) {
        printSalaryIndicator(requestMode, null);
    }

    void printEmplsList(Employee[] employees, RequestModes requestMode) {
        if (!DataService.paramIsCorrect(employees)) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        String[] emplsList = null;
        DataService.setPrintTitle(requestMode);
        if (requestMode == RequestModes.DEPS_EMPLS_RM) { // ???
            printDepsEmplsList();
            return;
        }
        switch (requestMode) {
            case ALL_INFO_RM:
            case MIN_SALS_RM:
            case MAX_SALS_RM:
            case LESS_SALS_RM:
            case BIG_SALS_RM:
            case FIO_MATCH_FOUND_RM:
                emplsList = byDepartment ? getEmplsList(employees, ListTypes.DEP_NAME_EXCLUDE_LT) : getEmplsList(employees);
                break;
            case SALARIES_RM:
            case CORRECT_SALS_RM:
                emplsList = byDepartment ? getEmplsList(employees, ListTypes.SALARIES_DEP_LT) : getEmplsList(employees, ListTypes.SALARIES_LT);
                break;
            case PERS_CARDS_RM:
                emplsList = byDepartment ? getEmplsList(employees, ListTypes.NAMES_N_ID_LT) : getEmplsList(employees, ListTypes.PERSONAL_CARDS_LT);
                break;
        }
        if (!DataService.paramIsCorrect(emplsList)) {
            System.out.println(DataService.NULL_PRINT_LIST_MESS);
            return;
        }
        System.out.println(DataService.getPrintTitle());
        if (correctedSalariesCount > 0) {
            System.out.println("\nРазмеры заработных плат успешно изменены.");
            correctedSalariesCount = 0;
        }
        DataService.printList(emplsList, DataService.PrintModes.NUMBERED_LIST_PM);
        switch (requestMode) {
            case SALARIES_RM:
            case CORRECT_SALS_RM:
            case MIN_SALS_RM:
            case MAX_SALS_RM:
            case LESS_SALS_RM:
            case BIG_SALS_RM:
                setReportMess(NULL_SALS_FOUNDS_MESS);
                nullSalaryReport(requestMode);
        }
    }

    void printEmplsList(Employee[] employees) {
        printEmplsList(employees, RequestModes.ALL_INFO_RM);
    }

    void printEmplsList(Department department) {
        if (noPrintRMTitle(RequestModes.ALL_INFO_RM)) return;
        if (!DataService.paramIsCorrect(requestByDepartment(department))) {
            System.out.println(NO_MATCH_FOUNDS_DEP_MESS);
            return;
        }
        printEmplsList(RequestModes.ALL_INFO_RM, 0, department);
    }

    void printEmplsList(RequestModes requestMode, float comparedSalary, Department department) {
        if (noPrintRMTitle(requestMode)) return;
        if (!DataService.paramIsCorrect(requestByDepartment(department))) {
            System.out.println(NO_MATCH_FOUNDS_DEP_MESS);
            return;
        }
        if (comparedSalary < 0) {
            throw new IllegalArgumentException(DataService.INCORRECT_COMPARE_PARAMS_MESS);
        } else targetSalary = comparedSalary;
        Employee[] relEmpls;
        switch (requestMode) {
            case ALL_INFO_RM:
            case PERS_CARDS_RM:
                relEmpls = operGroupRecords;
                isDoneMess = DataService.WELL_DONE_MESS;
                break;
            case DEPS_EMPLS_RM:
                relEmpls = extractRelevantEmpls(department);
                break;
            default:
                relEmpls = extractRelevantEmpls(requestMode, comparedSalary);
        }
        printListIfValid(relEmpls, requestMode);
        byDepartment = false;
        targetSalary = 0;
    }

    void printEmplsList(RequestModes requestMode, Department department) {
        printEmplsList(requestMode, 0, department);
    }

    void printEmplsList(RequestModes requestMode, float comparedSalary) {
        printEmplsList(requestMode, comparedSalary, null);
    }

    void printEmplsList(RequestModes requestMode) {
        if (requestMode == RequestModes.DEPS_EMPLS_RM) {
        printDepsEmplsList();
        return;
        }
        printEmplsList(requestMode, 0, null);
    }

    void printEmplsList() {
        printEmplsList(RequestModes.ALL_INFO_RM, 0, null);
    }

    void printDepsEmplsList() {
        if (!DataService.paramIsCorrect(employees)) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        if (noPrintRMTitle(RequestModes.DEPS_EMPLS_RM)) return;
        for (Department curDep :
                Departments.getDepartments()) {
            if (curDep != null) {
                Employee[] relEmpls = requestByDepartment(curDep);
                System.out.println("\n" + curDep + ":");
                if (DataService.paramIsCorrect(relEmpls)) {
                    DataService.printList(relEmpls, DataService.PrintModes.NUMBERED_LIST_PM);
                } else System.out.println("\nНа данный момент записей о сотрудниках этого Подразделения нет.");
            }
        }
    }

    boolean isRelevantID(int id, Employee[] employees) {
        int idMatches = 0;
        for (Employee curEmpl : employees) {
            if (curEmpl.getId() == id) {
                ++idMatches;
                break;
            }
        }
        if (idMatches > 0) return true;
        System.out.println(NO_ID_MATCHES_MESS);
        return false;
    }

    void selectEmplByID(Employee[] employees, int id) {
        operEmpl = null;
        for (Employee curEmpl :
                employees) {
            if (curEmpl.getId() == id) operEmpl = curEmpl;
        }
    }

    void selectAnEmployee(String lastName, String firstName, String middleName) throws IOException {
        if (noPrintRMTitle(RequestModes.SELECT_EMPL_RM)) return;
        Employee[] relEmpls = extractRelevantEmpls(lastName, firstName, middleName);
        switch (isDoneMess) {
            case DataService.ONE_MATCH_FOUND_MESS:
                operEmpl = relEmpls[0];
                System.out.println("\nСотрудник " + operEmpl + " выбран.");
                break;
            case DataService.MULTIPLE_MATCH_FOUND_MESS:
                System.out.println(isDoneMess);
                printEmplsList(relEmpls);
                int selectedID;
                do {
                    System.out.println(SELECT_EMPL_ID_MESS);
                    selectedID = DataService.getUserChoose();
                    if (selectedID == 0) {
                        System.out.println(NO_SELECTED_EMPLS_MESS);
                        return;
                    }
                    if (selectedID < 0) continue;
                    selectEmplByID(relEmpls, selectedID);
                } while (!isRelevantID(selectedID, relEmpls));
                break;
            default:
                System.out.println(isDoneMess);
        }
    }

    void addEmployee(String lastName, String firstName, String middleName, Department department, float salary) throws IOException {
        if (noPrintRMTitle(RequestModes.ADD_EMPL_RM)) return;
        if (Departments.isInvalidDepartment(department)) {
            System.out.println(Departments.DEP_NOT_EXIST_MESS);
            return;
        }
        Employee[] relEmpls = extractRelevantEmpls(lastName, firstName, middleName);
        if (DataService.paramIsCorrect(relEmpls)) {
            operEmpl = relEmpls[0];
            printEmplsList(relEmpls, RequestModes.FIO_MATCH_FOUND_RM);
            dismissMultipleEmplsMatches(relEmpls);
            return;
        }
        int nullRecordIndex = DataService.getFirstNullRecIndex(employees);
        if (nullRecordIndex < 0) {
            isDoneMess = EMPLS_BOOK_IS_FULL_MESS;
        } else {
            employees[nullRecordIndex] = new Employee(lastName, firstName, middleName, department, salary);
            isDoneMess = String.format("\nСотрудник %s успешно добавлен в штат.\n", employees[nullRecordIndex].getPersonalCard());
        }
        System.out.println(isDoneMess);
    }

    void dismissEmployee(String lastName, String firstName, String middleName) throws IOException {
        if (noPrintRMTitle(RequestModes.DISM_EMPL_RM)) return;
        if (!DataService.paramIsCorrect(lastName) || !DataService.paramIsCorrect(firstName) || !DataService.paramIsCorrect(middleName)) {
            throw new IllegalArgumentException(DataService.INCORRECT_COMPARE_PARAMS_MESS);
        }
        Employee[] relEmpls = extractRelevantEmpls(lastName, firstName, middleName);
        switch (isDoneMess) {
            case DataService.ONE_MATCH_FOUND_MESS:
                if ((emplsNumber -= DataService.resetRecords(employees, relEmpls[0])) < 1) {
                    System.out.println(NO_RECORDS_LEFT_MESS);
                    return;
                }
                break;
            case DataService.MULTIPLE_MATCH_FOUND_MESS:
                System.out.println(isDoneMess);
                printEmplsList(relEmpls, RequestModes.PERS_CARDS_RM);
                dismissMultipleEmplsMatches(relEmpls);
                break;
            default:
                System.out.println(isDoneMess);
        }

    }

    void dismissMultipleEmplsMatches(Employee[] employees) throws IOException {
        if (noPrintRMTitle(RequestModes.DISM_EMPL_RM)) return;
        String emplIDStr;
        int emplID = 0;
        switch (DataService.whatShouldBeDone()) {
            case 1:
                emplsNumber -= DataService.resetRecords(this.employees, employees);
                if (bookIsEmptyReport()) return;
                break;
            case 2:
                do {
                    System.out.println("Введите ID сотрудника, запись которого следует оставить:");
                    emplIDStr = DataService.bufferReader.readLine();
                    if (emplIDStr.equals("")) return;
                    emplID = Integer.parseInt(emplIDStr);
                    if (emplID != 0) {
                        emplsNumber -= DataService.resetRecords(this.employees, emplID, DataService.ServiceModes.CORRECT);
                        if (bookIsEmptyReport()) return;
                    }
                } while (isRelevantID(emplID, employees));
                break;
            case 3:
                String titleStr = SELECT_EMPL_ID_MESS.replace(" изменить", "удалить");
                int relEmplsRecsCount = employees.length;
                do {
                    System.out.print(titleStr);
                    emplID = DataService.getUserChoose();
                    if (emplID < 0) {
                        System.out.println(NO_ID_MATCHES_MESS);
                        continue;
                    }
                    if (isRelevantID(emplID, employees)) continue;
                    if (emplID != 0) {
                        emplsNumber -= DataService.resetRecords(this.employees, emplID, DataService.ServiceModes.DECREASE);
                        if (--relEmplsRecsCount == 0) {
                            System.out.println("\nВсе записи, соответствующие запросу, удалены.");
                            return;
                        }
                        if (bookIsEmptyReport()) return;
                    }
                } while (emplID != 0);
                break;
        }

    }
}
