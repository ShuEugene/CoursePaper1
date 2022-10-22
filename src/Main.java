//  Курсовая работа по 1-му курсу «Введение в профессию и синтаксис языка»
public class Main {
    public static void main(String[] args) {

        EmployeeBook emplsBook = new EmployeeBook("Книга учёта сотрудников");

        emplsBook.emplsArray[0] = new Employee(emplsBook,"Директоров", "Важан", "Капиталюгович", Departments.firstDep, 125_000);
        emplsBook.emplsArray[1] = new Employee(emplsBook,"Директорова", "Светлана", "Инициативовна", Departments.secondDep, 100_000);
        emplsBook.emplsArray[2] = new Employee(emplsBook,"Бухгалтерова", "Илана", "Счетоводовна", Departments.secondDep, 75_000);
        emplsBook.emplsArray[4] = new Employee(emplsBook,"Бухпомощникова", "Неумейка", "Научуськовна", Departments.secondDep, 50_000);
        emplsBook.emplsArray[5] = new Employee(emplsBook,"Мастеров", "Златомысл", "Идейкович", Departments.thirdDep, 100_000);
        emplsBook.emplsArray[6] = new Employee(emplsBook,"Бригадиров", "Расслабон", "Халявович", Departments.thirdDep, 75_000);
        emplsBook.emplsArray[7] = new Employee(emplsBook,"Златорук", "Трудогол", "Ответственникович", Departments.thirdDep, 100_000);
        emplsBook.emplsArray[9] = new Employee(emplsBook,"Рукожоп", "Принесиподай", "Растыкович", Departments.thirdDep, 50_000);

        emplsBook.printEmployeesList();

        System.out.printf("\nСумма выплат по заработным платам: %.2f руб..\n", emplsBook.calculateSalarySum());

        emplsBook.printEmployeesList(DataService.RequestMode.MIN_SALARY);

        emplsBook.printEmployeesList(DataService.RequestMode.MAX_SALARY);

        System.out.printf("\n\"Средняя\" заработная плата: %.2f.\n", emplsBook.getAverageSalary());

        emplsBook.printEmployeesList(DataService.RequestMode.FULL_NAME_LIST);

        float salaryChangePercent = -10.00f;
        emplsBook.changeSalaries(salaryChangePercent);
        emplsBook.printEmployeesList(DataService.RequestMode.CORRECT_SALARY_LIST);

        emplsBook.printEmployeesList(DataService.RequestMode.MIN_SALARY, Departments.thirdDep);

        emplsBook.printEmployeesList(DataService.RequestMode.MAX_SALARY, Departments.fourthDep);

        System.out.printf("\nСумма зарплат по Подразделению «%s»: %.2f руб..\n",
                Departments.secondDep.getName(), emplsBook.calculateSalarySum(Departments.secondDep));

        System.out.printf("\n\"Средняя\" заработная плата по Подразделению «%s»: %.2f.\n",
                Departments.thirdDep.getName(), emplsBook.getAverageSalary(Departments.thirdDep));

        emplsBook.changeSalaries(Departments.thirdDep, salaryChangePercent);
        emplsBook.printEmployeesList(DataService.RequestMode.CORRECT_SALARY_LIST, Departments.thirdDep);

        emplsBook.printEmployeesList(DataService.RequestMode.FULL_NAME_LIST, Departments.thirdDep);

        float comparedSalary = 75_000;
        emplsBook.printEmployeesList(DataService.RequestMode.LESS_SALARY, comparedSalary);
        emplsBook.printEmployeesList(DataService.RequestMode.BIG_SALARY, comparedSalary, Departments.secondDep);
    }
}