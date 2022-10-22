public class DataService {

    static final String TYPE_MISMATCH_MESS = "Тип не соответствует требуемому.";
    static final String NULL_STRING_MESS = "Полученная строка пуста.";
    static final String NULL_OBJECT_REQUEST_MESS = "Запрос по нулевому объекту.";
    static final String INCORRECT_INITIAL_PARAMS_MESS = "Неприемлемые параметры инициализации.";
    static final String INCORRECT_COMPARE_PARAMS_MESS = "Неприемлемые параметр сопоставления.";
    static final String INCORRECT_SELECTED_MODE_MESS = "Выбранный режим не подходит.";
    static final String NO_MATCH_FOUNDS_MESS = "\nСовпадения отсутствуют.";

    enum RequestMode {MIN_SALARY, MAX_SALARY, LESS_SALARY, BIG_SALARY, FULL_NAME_LIST, SALARY_LIST, CORRECT_SALARY_LIST}

    enum ServiceMode {INCREASE, DECREASE, PERCENTAGE, CORRECT}

    static private String printTitle;

//  *********

    static String getPrintTitle() {
        return printTitle;
    }

    static void setPrintTitle(String printTitle) {
        DataService.printTitle = printTitle;
    }

    static String getPrintTitle(RequestMode requestMode) {
        setPrintTitle(requestMode);
        return printTitle;
    }

    static String getPrintTitle(RequestMode requestMode, Department department) {
        setPrintTitle(requestMode, department);
        return printTitle;
    }

    static void setPrintTitle(RequestMode requestMode) {
        switch (requestMode) {
            case FULL_NAME_LIST:
                printTitle = "\nПолные имена сотрудников:";
                break;
            case SALARY_LIST:
                printTitle = "\nПеречень заработных плат сотрудников:";
                break;
            case CORRECT_SALARY_LIST:
                printTitle = "\nРазмеры заработных плат сотрудников после внесения изменений:";
                break;
            case MIN_SALARY:
                printTitle = "\nСотрудники, получающие наименьшую заработную плату:";
                break;
            case MAX_SALARY:
                printTitle = "\nСотрудники, получающие наибольшую заработную плату:";
                break;
            case LESS_SALARY:
                printTitle = "\nСотрудники, получающие заработную плату меньше заданной:";
                break;
            case BIG_SALARY:
                printTitle = "\nСотрудники, получающие наибольшую заработную плату больше заданной:";
                break;
            default:
                throw new IllegalArgumentException(DataService.INCORRECT_SELECTED_MODE_MESS);
        }
    }

    static void setPrintTitle(RequestMode requestMode, Department department) {
        switch (requestMode) {
            case FULL_NAME_LIST:
                printTitle = String.format("\nПолные имена сотрудников Подразделения «%s»:", department.getName());
                break;
            case SALARY_LIST:
                printTitle = String.format("\nПеречень заработных плат сотрудников Подразделения «%s»:", department.getName());
                break;
            case CORRECT_SALARY_LIST:
                printTitle = String.format("\nРазмеры заработных плат сотрудников Подразделения «%s» после внесения изменений:", department.getName());
                break;
            case MIN_SALARY:
                printTitle = String.format("\nСотрудники Подразделения «%s», получающие наименьшую заработную плату:", department.getName());
                break;
            case MAX_SALARY:
                printTitle = String.format("\nСотрудники Подразделения «%s», получающие наибольшую заработную плату:", department.getName());
                break;
            case LESS_SALARY:
                printTitle = String.format("\nСотрудники Подразделения «%s», получающие заработную плату меньше заданной:", department.getName());
                break;
            case BIG_SALARY:
                printTitle = String.format("\nСотрудники Подразделения «%s», получающие заработную плату больше заданной:", department.getName());
                break;
            default:
                throw new IllegalArgumentException(DataService.INCORRECT_SELECTED_MODE_MESS);
        }
    }

//  *********

    static String[] getItemsList(Object[] o, RequestMode requestMode) {
        if (parameterIsCorrect(o)) {
            String[] itemsList = new String[o.length];
            switch (requestMode) {
                case FULL_NAME_LIST:
                case MIN_SALARY:
                case MAX_SALARY:
                case LESS_SALARY:
                case BIG_SALARY:
                    if (EmployeeBook.isEmployees(o)) {
                        Employee[] employees = (Employee[]) o;
                        int itemsCount = -1;
                        for (Employee curEmpl :
                                employees) {
                            if (parameterIsCorrect(curEmpl)) {
                                itemsList[++itemsCount] = curEmpl.getFullName();
                            }
                        }
                    } else throw new IllegalArgumentException(TYPE_MISMATCH_MESS);
                    break;
                case SALARY_LIST:
                case CORRECT_SALARY_LIST:
                    if (EmployeeBook.isEmployees(o)) {
                        Employee[] employees = (Employee[]) o;
                        int itemsCount = -1;
                        for (Employee curEmpl :
                                employees) {
                            if (parameterIsCorrect(curEmpl)) {
                                itemsList[++itemsCount] = String.format("%s - %.2f",
                                        curEmpl.getFullName(), curEmpl.getSalary());
                            }
                        }
                    } else throw new IllegalArgumentException(TYPE_MISMATCH_MESS);
                    break;
                default:
                    throw new IllegalArgumentException(INCORRECT_SELECTED_MODE_MESS);
            }
            return itemsList;
        } else throw new IllegalArgumentException(NULL_OBJECT_REQUEST_MESS);
    }

    //  Убить, если не использую !«
    static String[] getItemsList(Object[] o, RequestMode requestMode, Department department) {
        if (parameterIsCorrect(o)) {
            String[] itemsList = new String[o.length - nullFounds(o)];
            if (EmployeeBook.isEmployees(o)) {
                Employee[] employees = (Employee[]) o;
                int itemsCount = -1;
                for (Employee curEmpl :
                        employees) {
                    if (parameterIsCorrect(curEmpl) && curEmpl.inThisDepartment(department)) {
                        switch (requestMode) {
                            case FULL_NAME_LIST:
                            case MIN_SALARY:
                            case MAX_SALARY:
                                itemsList[++itemsCount] = String.format("%s (ID: %d)",
                                        curEmpl.getFullName(), curEmpl.getId());
                                break;
                            case SALARY_LIST:
                            case CORRECT_SALARY_LIST:
                                itemsList[++itemsCount] = String.format("%s (ID: %d) - %.2f",
                                        curEmpl.getFullName(), curEmpl.getId(), curEmpl.getSalary());
                                break;
                        }
                    }
                }
            } else throw new IllegalArgumentException(TYPE_MISMATCH_MESS);
            return itemsList;
        } else throw new IllegalArgumentException(NULL_OBJECT_REQUEST_MESS);
    }

    static int nullFounds(Object[] array, int startIndex) {
        if (parameterIsCorrect(array)) {
            int numberOfNullObjects = 0;
            if (startIndex >= 0 && startIndex < array.length) {
                for (int i = startIndex; i < array.length; i++) {
                    if (array[i] == null) {
                        numberOfNullObjects++;
                    }
                }
                return numberOfNullObjects;
            } else
                throw new IllegalArgumentException("Начальный индекс должен быть меньше длинны рассматриваемого массива.");
        } else throw new IllegalArgumentException(NULL_OBJECT_REQUEST_MESS);
    }
//  »!

    static boolean parameterIsCorrect(float parameter) {
        return parameter > 0;
    }

    static boolean parameterIsCorrect(String parameter) {
        return (parameter != null && !parameter.isEmpty() && !parameter.isBlank());
    }

    static boolean parameterIsCorrect(Object parameter) {
        return parameter != null;
    }

    static int lastNotNullObjectIndex(Object[] o) {
        int lastNotNullObjectIndex = -1;
        for (int i = 0; i < o.length; i++) {
            if (o[i] != null) {
                lastNotNullObjectIndex = i;
            }
        }
        return lastNotNullObjectIndex;
    }

    static int nullFounds(Object[] array) {
        int numberOfNullObjects = 0;
        if (parameterIsCorrect(array)) {
            for (Object currEl : array) {
                if (currEl == null) {
                    numberOfNullObjects++;
                }
            }
        }
        return numberOfNullObjects;
    }

    static boolean arrayIsEmpty(Object[] objects) {
        if (!parameterIsCorrect(objects)) {
            throw new IllegalArgumentException(NULL_OBJECT_REQUEST_MESS);
        }
        return objects.length - nullFounds(objects) < 1;
    }

    static boolean noMatchFoundsReport(Employee[] employees) {
        if (arrayIsEmpty(employees)) {
            printTitle = NO_MATCH_FOUNDS_MESS;
            System.out.println(DataService.getPrintTitle());
            return true;
        }
        return false;
    }

    static boolean exceptionalCaseReport() {
        if (!parameterIsCorrect(printTitle)) {
            throw new IllegalArgumentException(DataService.NULL_STRING_MESS);
        }
        switch (printTitle) {
            case EmployeeBook.EQUAL_SALARY_MESS:
            case EmployeeBook.EQUAL_SALARIES_IN_THIS_DEP_MESS:
            case EmployeeBook.NULL_SALARIES_MESS:
                System.out.println(DataService.getPrintTitle());
                return true;
        }
        return false;
    }

    static void printList(Object[] objects) {
        if (DataService.parameterIsCorrect(objects)) {
            String itemSeparator = ";";
            for (int i = 0; i < objects.length; i++) {
                if (DataService.parameterIsCorrect(objects[i])) {
                    if (i == DataService.lastNotNullObjectIndex(objects)) {
                        itemSeparator = ".";
                    }
                    System.out.println(objects[i] + itemSeparator);
                }
            }
        } else throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
    }
}