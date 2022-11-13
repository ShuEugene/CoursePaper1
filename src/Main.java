import java.io.IOException;

//  Курсовая работа по 1-му курсу «Введение в профессию и синтаксис языка»
public class Main {

    static final String taskSeparator = "=".repeat(55);

    public static void main(String[] args) throws IOException {

        EmployeeBook emplsBook = new EmployeeBook("Книга учёта сотрудников");

        emplsBook.printEmplsList();
        System.out.println(taskSeparator);

        emplsBook.printSalaryIndicator(EmployeeBook.RequestModes.SALS_SUM_RM);
        System.out.println(taskSeparator);
        emplsBook.printSalaryIndicator(EmployeeBook.RequestModes.SALS_SUM_RM, Departments.fifthDep);
        System.out.println(taskSeparator);

        emplsBook.printEmplsList(EmployeeBook.RequestModes.MIN_SALS_RM);
        System.out.println(taskSeparator);

        emplsBook.printEmplsList(EmployeeBook.RequestModes.MAX_SALS_RM);
        System.out.println(taskSeparator);

        emplsBook.printSalaryIndicator(EmployeeBook.RequestModes.AVER_SAL_RM);
        System.out.println(taskSeparator);

        emplsBook.printEmplsList(EmployeeBook.RequestModes.PERS_CARDS_RM);
        System.out.println(taskSeparator);

        float salaryChangePercent = -10.00f;
        emplsBook.changeSalaries(salaryChangePercent, DataService.ServiceModes.PERCENTAGE);
        emplsBook.printEmplsList(EmployeeBook.RequestModes.CORRECT_SALS_RM);
        System.out.println(taskSeparator);

        emplsBook.printEmplsList(EmployeeBook.RequestModes.MIN_SALS_RM, Departments.thirdDep);
        System.out.println(taskSeparator);

        emplsBook.printEmplsList(EmployeeBook.RequestModes.MAX_SALS_RM, Departments.secondDep);
        System.out.println(taskSeparator);

        emplsBook.printSalaryIndicator(EmployeeBook.RequestModes.AVER_SAL_RM, Departments.secondDep);
        System.out.println(taskSeparator);

        emplsBook.printSalaryIndicator(EmployeeBook.RequestModes.AVER_SAL_RM, Departments.thirdDep);
        System.out.println(taskSeparator);

        float changeSalaryValue = 100;
        emplsBook.changeSalaries(Departments.thirdDep, changeSalaryValue, DataService.ServiceModes.INCREASE);
        emplsBook.printEmplsList(EmployeeBook.RequestModes.CORRECT_SALS_RM, Departments.thirdDep);
        System.out.println(taskSeparator);

        emplsBook.printEmplsList(EmployeeBook.RequestModes.PERS_CARDS_RM, Departments.thirdDep);
        System.out.println(taskSeparator);

        float comparedSalary = 75_000;
        emplsBook.printEmplsList(EmployeeBook.RequestModes.LESS_SALS_RM, comparedSalary);
        System.out.println(taskSeparator);
        emplsBook.printEmplsList(EmployeeBook.RequestModes.BIG_SALS_RM, comparedSalary, Departments.secondDep);
        System.out.println(taskSeparator);


        emplsBook.addEmployee("Новичкова", "Смазлюшка", "Смекалковна", Departments.thirdDep, 0);
        System.out.println(taskSeparator);

        emplsBook.printEmplsList();
        System.out.println(taskSeparator);

        emplsBook.dismissEmployee("Новичкова", "Смазлюшка", "Смекалковна");
        System.out.println(taskSeparator);

        emplsBook.printEmplsList();
        System.out.println(taskSeparator);

        emplsBook.selectAnEmployee("Новичкова", "Смазлюшка", "Смекалковна");
        if (EmployeeBook.getOperEmpl() != null) {
            EmployeeBook.resetCorrectedSalsCount();
            EmployeeBook.getOperEmpl().changeSalary(DataService.ServiceModes.SET_VALUE, 500);
            if (EmployeeBook.getCorrectedSalariesCount() > 0) {
                if (EmployeeBook.getCorrectedSalariesCount() == 1) {
                    System.out.println("\nРазмер заработной платы успешно изменён.");
                } else {
                    System.out.println("\nОшибка изменения размера заработной платы.");
                }
            } else System.out.println("\nРазмер заработной платы оставлен без изменений.");
            EmployeeBook.getOperEmpl().setDepartment(Departments.secondDep);
        }
        System.out.println(taskSeparator);

        emplsBook.printEmplsList(EmployeeBook.RequestModes.DEPS_EMPLS_RM);
        System.out.println(taskSeparator);
    }
}