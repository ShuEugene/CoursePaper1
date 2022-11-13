import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataService {

    static private String printTitle;
    static BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));

    enum ServiceModes {SET_VALUE, INCREASE, DECREASE, PERCENTAGE, CORRECT;}

    enum PrintModes {SIMPLE_LIST_PM, NUMBERED_LIST_PM;}

    static final String[] DEL_RECS_ACTIONS = {"Удалить все найденные записи", "Удалить все записи, кроме одной", "Удалить одну запись"};

    static final String TYPE_MISMATCH_MESS = "Тип не соответствует требуемому.";
    static final String NULL_STRING_MESS = "Полученная строка пуста.";
    static final String NULL_OBJECT_REQUEST_MESS = "Запрос по нулевому объекту.";
    static final String NULL_PRINT_LIST_MESS = "\nСписок печати пуст.";
    static final String INCORRECT_INITIAL_PARAMS_MESS = "Неприемлемые параметры инициализации.";
    static final String INCORRECT_ACTION_MESS = "Выбранного действия нет среди приемлемых.";
    static final String INCORRECT_COMPARE_PARAMS_MESS = "Неприемлемые параметры сопоставления.";
    static final String INCORRECT_SELECTED_MODE_MESS = "Выбранный режим не подходит.";
    static final String NO_MATCHES_FOUND_MESS = "\nИскомые совпадения отсутствуют.";
    static final String ONE_MATCH_FOUND_MESS = "\nНайдено одно совпадение.";
    static final String MULTIPLE_MATCH_FOUND_MESS = "\nНайдено несколько совпадений.";
    static final String NO_RECS_EDITED_MESS = "\nНе обработано ни одной записи.";
    static final String REC_EDITED_MESS = "\nЗапись обработана.";
    static final String SEVERAL_RECS_EDITED_MESS = "\nОбработано несколько записей.";
    static final String WELL_DONE_MESS = "\nОперация выполнена успешно.";


    static String getPrintTitle() {
        return printTitle;
    }

    static void setPrintTitle(String printTitle) {
        DataService.printTitle = printTitle;
    }

    static void setPrintTitle(EmployeeBook.RequestModes requestMode) {
        switch (requestMode) {
            case ALL_INFO_RM:
                printTitle = EmployeeBook.ALL_EMPLS_INFO_PT;
                break;
            case PERS_CARDS_RM:
                printTitle = EmployeeBook.PERS_CARDS_PT;
                break;
            case FIO_MATCH_FOUND_RM:
                printTitle = EmployeeBook.EMPL_MATCH_FOUND_MESS;
                printTitle = printTitle.replace(" с указанными данными ",
                        String.format(" %s ", EmployeeBook.getOperEmpl().getFullName()));
                return;
            case DEPS_EMPLS_RM:
                printTitle = EmployeeBook.DEPS_EMPLS_INFO_PT;
                return;
            case SALS_SUM_RM:
                printTitle = EmployeeBook.SALARIES_SUM_PT;
                break;
            case AVER_SAL_RM:
                printTitle = EmployeeBook.AVERAGE_SALARY_PT;
                break;
            case SALARIES_RM:
                printTitle = EmployeeBook.SALARIES_PT;
                break;
            case CORRECT_SALS_RM:
                printTitle = EmployeeBook.CORRECT_SALS_PT;
                break;
            case MIN_SALS_RM:
                printTitle = EmployeeBook.MIN_SALS_PT;
                break;
            case MAX_SALS_RM:
                printTitle = EmployeeBook.MAX_SALS_PT;
                break;
            case LESS_SALS_RM:
                printTitle = String.format("%s %.2f %s", EmployeeBook.LESS_SALS_PT, EmployeeBook.getTargetSalary(), EmployeeBook.currencyType);
                break;
            case BIG_SALS_RM:
                printTitle = String.format("%s %.2f %s", EmployeeBook.BIG_SALS_PT, EmployeeBook.getTargetSalary(), EmployeeBook.currencyType);
                break;
            default:
                throw new IllegalArgumentException(DataService.INCORRECT_SELECTED_MODE_MESS);
        }
        printTitle += EmployeeBook.isByDepartment() ? String.format(" в Подразделении (%s)", EmployeeBook.getRequestedDepartment())
                : " в Компании";
        switch (requestMode) {
            case ALL_INFO_RM:
            case PERS_CARDS_RM:
            case CORRECT_SALS_RM:
                if (EmployeeBook.isByDepartment()) {
                    printTitle = printTitle.replace(" в Подразделении", " Подразделения");
                } else {
                    printTitle = printTitle.replace(" в", "");
                }
                break;
            case LESS_SALS_RM:
            case BIG_SALS_RM:
                if (EmployeeBook.isByDepartment()) {
                    printTitle = printTitle.replace(" в Подразделении ", ", по Подразделению ");
                } else {
                    printTitle = printTitle.replace(" в Компании", "");
                }
                break;
        }
        printTitle += ':';
    }


    static boolean paramIsCorrect(String parameter) {
        return (parameter != null && !parameter.isEmpty() && !parameter.isBlank());
    }

    static boolean paramIsCorrect(Object[] array) {
        return array != null && array.length > 0;
    }

    static int getFirstNullRecIndex(Object[] objects) {
        if (!paramIsCorrect(objects)) {
            throw new IllegalArgumentException(NULL_OBJECT_REQUEST_MESS);
        }
        int foundStatus = -1;
        for (int curElIndex = 0; curElIndex < objects.length; curElIndex++) {
            if (objects[curElIndex] == null) return curElIndex;
        }
        return foundStatus;
    }

    static int nullRecordsFound(Object[] array) {
        int numberOfNullObjects = 0;
        if (paramIsCorrect(array)) {
            for (Object currEl : array) {
                if (currEl == null) {
                    numberOfNullObjects++;
                }
            }
        }
        return numberOfNullObjects;
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

    static int getValidObjectsNumber(Object[] objects) {
        if (objects == null) {
            throw new IllegalArgumentException(NULL_OBJECT_REQUEST_MESS);
        }
        int validObjectsNumber = objects.length;
        for (Object curObj :
                objects) {
            if (curObj == null) --validObjectsNumber;
        }
        return validObjectsNumber;
    }

    static String[] getValidObjects(String[] records) {
        int validObjectsNumber = getValidObjectsNumber(records);
        if (validObjectsNumber <= 0) return null;
        String[] validObjects = new String[validObjectsNumber];
        int validObjectIndex = -1;
        for (String curObj : records) {
            if (curObj != null) validObjects[++validObjectIndex] = curObj;
        }
        return validObjects;
    }

    static Employee[] getValidObjects(Employee[] employees) {
        int validObjectsNumber = getValidObjectsNumber(employees);
        if (validObjectsNumber <= 0) return null;
        Employee[] validObjects = new Employee[validObjectsNumber];
        int validObjectIndex = -1;
        for (Employee curObj : employees) {
            if (curObj != null) validObjects[++validObjectIndex] = curObj;
        }
        return validObjects;
    }

    static boolean isOutOfRange(int number, int minValue, int maxValue) {
        return (number <= minValue - 1 || number >= maxValue + 1);
    }

    static int getUserChoose() throws IOException {
        String userChooseStr = bufferReader.readLine();
        if (userChooseStr.equals("")) return 0;
        int userChoose;
        try {
            userChoose = Integer.parseInt(userChooseStr);
        } catch (NumberFormatException e) {
            return -1;
        }
        return userChoose;
    }

    static byte whatShouldBeDone() throws IOException {
        System.out.printf("\nЧто следует предпринять?" +
                "\n(\"1\"...\"%d\" - для выбора действия либо \"Enter\" - для прекращения операции)\n", DEL_RECS_ACTIONS.length);
        printList(DEL_RECS_ACTIONS, PrintModes.NUMBERED_LIST_PM);
        String actNumberStr;
        byte actNumber = 0;
        do {
            actNumberStr = bufferReader.readLine();
            if (actNumberStr.equals("")) return 0;
            try {
                actNumber = Byte.parseByte(actNumberStr);
            } catch (NumberFormatException e) {
                System.out.println(INCORRECT_ACTION_MESS);
                continue;
            }
            if (isOutOfRange(actNumber, 0, DEL_RECS_ACTIONS.length)) {
                System.out.println(INCORRECT_ACTION_MESS);
            }
        } while (isOutOfRange(actNumber, 0, DEL_RECS_ACTIONS.length));
        return actNumber;
    }

    static int resetRecords(Object[] editableArray, Object requiredRecord) {
        if (!paramIsCorrect(editableArray) || requiredRecord == null) {
            throw new IllegalArgumentException(NULL_OBJECT_REQUEST_MESS);
        }
        int editedRecCount = 0;
        for (int recIndex = 0; recIndex < editableArray.length; ++recIndex) {
            if (editableArray[recIndex] == requiredRecord) {
                editableArray[recIndex] = null;
                ++editedRecCount;
            }
        }
        getEditRecsDoneReport(editedRecCount);
        return editedRecCount;
    }

    static int resetRecords(Object[] editableArray, int pointer, ServiceModes serviceMode) {
        if (!paramIsCorrect(editableArray)) {
            throw new IllegalArgumentException(NULL_OBJECT_REQUEST_MESS);
        }
        int editedRecCount = 0;
        if (editableArray instanceof Employee[]) {
            Employee[] employees = (Employee[]) editableArray;
            for (int emplIndex = 0; emplIndex < employees.length; ++emplIndex) {
                switch (serviceMode) {
                    case CORRECT:
                        if (employees[emplIndex].getId() != pointer) {
                            employees[emplIndex] = null;
                            ++editedRecCount;
                        }
                        break;
                    case DECREASE:
                        if (employees[emplIndex].getId() == pointer) {
                            employees[emplIndex] = null;
                            ++editedRecCount;
                        }
                        break;
                    default:
                        throw new IllegalArgumentException(INCORRECT_SELECTED_MODE_MESS);
                }
                if (serviceMode == ServiceModes.DECREASE && editedRecCount > 0) break;
            }
        } else throw new IllegalArgumentException(TYPE_MISMATCH_MESS);
        getEditRecsDoneReport(editedRecCount);
        return editedRecCount;
    }

    static int resetRecords(Object[] editableArray, Object[] requiredRecords) {
        if (!paramIsCorrect(editableArray) || !paramIsCorrect(requiredRecords)) {
            throw new IllegalArgumentException(NULL_OBJECT_REQUEST_MESS);
        }
        int editedRecCount = 0;
        for (Object reqRec : requiredRecords) {
            for (int elIndex = 0; elIndex < editableArray.length; elIndex++) {
                if (editableArray[elIndex] == reqRec) {
                    editableArray[elIndex] = null;
                    ++editedRecCount;
                }
            }
        }
        getEditRecsDoneReport(editedRecCount);
        return editedRecCount;
    }

    static void noMatchFoundsReport(Object[] objects) {
        if (!paramIsCorrect(objects) || objects.length < 1) {
            System.out.println(NO_MATCHES_FOUND_MESS);
        }
    }

    static void exceptionalCaseReport() {
        String isDoneMess = EmployeeBook.getIsDoneMess();
        if (!paramIsCorrect(isDoneMess)) {
            throw new IllegalArgumentException(DataService.NULL_STRING_MESS);
        }
        switch (isDoneMess) {
            case EmployeeBook.EQUAL_SALARY_MESS:
            case EmployeeBook.EQUAL_SALARIES_IN_THIS_DEP_MESS:
                String message = isDoneMess.replace(".", String.format(" - %s %s.", EmployeeBook.getTargetSalary(), EmployeeBook.currencyType));
                System.out.println(message);
                return;
            case EmployeeBook.NULL_SALARIES_MESS:
            case EmployeeBook.NULL_SALARIES_DEP_MESS:
                System.out.println(isDoneMess);
        }
    }

    static void getEditRecsDoneReport(int editedRecCount) {
        String isDoneMess;
        if (editedRecCount < 1) {
            isDoneMess = NO_RECS_EDITED_MESS.replace("обработано", "обнулено");
            EmployeeBook.setIsDoneMess(NO_RECS_EDITED_MESS);
        } else if (editedRecCount > 1) {
            isDoneMess = SEVERAL_RECS_EDITED_MESS.replace("Обработано", "Обнулено").
                    replace("несколько", "несколько (" + editedRecCount + ")");
            EmployeeBook.setIsDoneMess(SEVERAL_RECS_EDITED_MESS);
        } else {
            isDoneMess = REC_EDITED_MESS.replace("обработана", "обнулена");
            EmployeeBook.setIsDoneMess(WELL_DONE_MESS);
        }
        System.out.println(isDoneMess);
    }

    static void printList(Object[] objects, PrintModes printMode) {
        if (!DataService.paramIsCorrect(objects)) {
            throw new IllegalArgumentException(DataService.NULL_OBJECT_REQUEST_MESS);
        }
        String listItem = "", itemSeparator = ";";
        for (int objectIndex = 0; objectIndex < objects.length; objectIndex++) {
            if (objects[objectIndex] != null) {
                if (objectIndex == DataService.lastNotNullObjectIndex(objects)) {
                    itemSeparator = ".";
                }
                switch (printMode) {
                    case SIMPLE_LIST_PM:
                        listItem = String.format("%s%s", objects[objectIndex], itemSeparator);
                        break;
                    case NUMBERED_LIST_PM:
                        listItem = String.format("%d. %s%s", objectIndex + 1, objects[objectIndex], itemSeparator);
                        break;
                }
                System.out.println(listItem);
            }
        }
    }

    static void printList(Object[] objects) {
        printList(objects, PrintModes.SIMPLE_LIST_PM);
    }
}