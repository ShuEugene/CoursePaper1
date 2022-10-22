public class Departments {

    static private int departmentsCount = 0;

    static Department firstDep = new Department("1");
    static Department secondDep = new Department("2");
    static Department thirdDep = new Department("3");
    static Department fourthDep = new Department("4");
    static Department fifthDep = new Department("5");

//  Убить, если не использую !«
    static final String DEP_NOT_EXIST_MESS = "\nЗапрашиваемое Подразделение отсутствует в БД.";

    static int getDepartmentsCount() {
        return departmentsCount;
    }

    static void setDepartmentsCount(int value)
    {
        Departments.departmentsCount = value;
    }
//  »!

    static int getDepartmentsCount(DataService.ServiceMode mode) {
        switch (mode) {
            case INCREASE:
                return ++departmentsCount;
            case DECREASE:
                return --departmentsCount;
            default:
                throw new IllegalArgumentException(DataService.INCORRECT_SELECTED_MODE_MESS);
        }
    }

}