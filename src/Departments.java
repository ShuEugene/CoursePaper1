public class Departments {

    static private Department[] departments;
    static Department firstDep;
    static Department secondDep;
    static Department thirdDep;
    static Department fourthDep;
    static Department fifthDep;

    static private int departmentsCount = 0;

    static final String DEPS_ALREADY_EXIST_MESS = "\nПодразделения были объявлены ранее.";
    static final String DEP_NOT_EXIST_MESS = "\nУказанное Подразделение отсутствует в БД.";
    static final String DEPS_IS_DECLARE = "\nПодразделения определены.";


    static Department[] getDepartments() {
        return departments;
    }

    static int getDepartmentsCount(DataService.ServiceModes mode) {
        switch (mode) {
            case INCREASE:
                return ++departmentsCount;
            case DECREASE:
                return --departmentsCount;
            default:
                throw new IllegalArgumentException(DataService.INCORRECT_SELECTED_MODE_MESS);
        }
    }

    static void declare(String[] depsNames) {
        if (DataService.paramIsCorrect(departments)) {
            System.out.println(DEPS_ALREADY_EXIST_MESS);
            return;
        }
        if (depsNames.length < 1) return;
        int depIndex = -1;
        Department[] depsArray = new Department[DataService.getValidObjectsNumber(depsNames)];
        for (String curName :
                depsNames) {
            if (DataService.paramIsCorrect(curName)) depsArray[++depIndex] = new Department(curName);
        }
        departments = depsArray;
        System.out.println(DEPS_IS_DECLARE);
    }

    static boolean isInvalidDepartment(Department department) {
        if (department == null) return true;
        for (Department curDep :
                departments) {
            if (department == curDep) return false;
        }
        return true;
    }
}