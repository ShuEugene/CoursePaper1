//  Курсовая работа по 1-му курсу «Введение в профессию и синтаксис языка»
public class Main {

    static Employee[] employees = new Employee[10];

//    static int nullFounds(Object[] o, int startIndex) {
/*
        int numberOfNullObjects = 0;
        if (startIndex >= 0 && startIndex < o.length) {
            for (int i = startIndex; i < o.length; i++) {
                if (o[i] == null) {
                    numberOfNullObjects++;
                }
            }
            return numberOfNullObjects;
        } else
            throw new IllegalArgumentException("Начальный индекс должен быть меньше длинны рассматриваемого массива.");
    }
*/

    static int nullFounds(Object[] o) {
        int numberOfNullObjects = 0;
        for (Object current : o) {
            if (current == null) {
                numberOfNullObjects++;
            }
        }
        return numberOfNullObjects;
    }

    static int indexLastNotNull(Object[] o) {
        int lastNotNull = -1;
        for (int i = 0; i < o.length; i++) {
            if (o[i] != null) {
                lastNotNull = i;
            }
        }
        if (lastNotNull >= 0) {
            return lastNotNull;
        } else throw new RuntimeException("В массиве нет ненулевых объектов.");
    }

    static void printEmployeesList() {
        String printSeparator = ";";
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] != null) {
                if (i == indexLastNotNull(employees)) {
                    printSeparator = ".";
                }
                System.out.println(employees[i] + printSeparator);
            }
        }
    }

    static int calculateSalarySum() {
        int salarySum = 0;
        for (Employee employee :
                employees) {
            if (employee != null) {
                salarySum += employee.getSalary();
            }
        }
        return salarySum;
    }

    static Employee[] getSomeMatchingEmployees(int matchIndicator) {
        Employee[] targetEmployees = new Employee[employees.length - nullFounds(employees)];
        int employeeIndex = -1;
        for (Employee currentEmployee :
                employees) {
            if (currentEmployee != null) {
                if (currentEmployee.getSalary() == matchIndicator) {
                    targetEmployees[(++employeeIndex)] = currentEmployee;
                }
            }
        }
        return targetEmployees;
    }

    static Employee[] findMinSalEmployees() {
        int minSalary = 0;
        for (Employee currentEmployee : employees) {
            if (currentEmployee != null) {
                minSalary = currentEmployee.getSalary();
                break;
            }
        }
        if (minSalary == 0) {
            throw new RuntimeException("Заплаты сотрудников не указаны.");
        }
        for (Employee currentEmployee :
                employees) {
            if (currentEmployee != null) {
                if (currentEmployee.getSalary() < minSalary) {
                    minSalary = currentEmployee.getSalary();
                }
            }
        }
        return getSomeMatchingEmployees(minSalary);
    }

    static Employee[] findMaxSalEmployees() {
        int maxSalary = 0;
        for (Employee currentEmployee : employees) {
            if (currentEmployee != null) {
                maxSalary = currentEmployee.getSalary();
                break;
            }
        }
        if (maxSalary == 0) {
            throw new RuntimeException("Зарплаты сотрудников не указаны.");
        }
        for (Employee currentEmployee :
                employees) {
            if (currentEmployee != null) {
                if (currentEmployee.getSalary() > maxSalary) {
                    maxSalary = currentEmployee.getSalary();
                }
            }
        }
        return getSomeMatchingEmployees(maxSalary);
    }

    static float getAverageSalary() {
        return (float) (calculateSalarySum()) / (float) ((employees.length - nullFounds(employees)));
    }

    public static void main(String[] args) {

        byte numberOfDepartments = 5;
        String[] departments = new String[numberOfDepartments];
        for (byte depIndex = 0; depIndex < numberOfDepartments; depIndex++) {
            departments[depIndex] = Integer.toString(depIndex + 1);
        }

        employees[2] = new Employee("Иванов", "Иван", "Иванович", departments[0], 125_000);
        employees[4] = new Employee("Петров", "Пётр", "Петрович", departments[4], 75_000);
        employees[5] = new Employee("Сидоров", "Сидор", "Сидорович", departments[4], 75_000);

        System.out.println();
        printEmployeesList();

        System.out.println();
        System.out.println(calculateSalarySum());

        System.out.println();
        Employee[] minSalEmployees = findMinSalEmployees();
        if (minSalEmployees.length == employees.length) {
            System.out.println("Все сотрудники получают одинаковую заработную плату.");
        } else {
            System.out.println("Сотрудники, получающие наименьшую заработную плату:");
            for (Employee currentEmployee :
                    minSalEmployees) {
                if (currentEmployee != null) {
                    System.out.println(currentEmployee.getFullName());
                }
            }
        }

        System.out.println();
        Employee[] maxSalEmployees = findMaxSalEmployees();
        if (maxSalEmployees.length == employees.length) {
            System.out.println("Все сотрудники получают одинаковую заработную плату.");
        } else {
            System.out.println("Сотрудники, получающие наибольшую заработную плату:");
            for (Employee currentEmployee :
                    maxSalEmployees) {
                if (currentEmployee != null) {
                    System.out.println(currentEmployee.getFullName());
                }
            }
        }

        System.out.println();
        System.out.printf("Средняя заработная плата: %.2f.\n", getAverageSalary());

        System.out.println();
        for (Employee currentEmployee :
                employees) {
            if (currentEmployee != null) {
                System.out.println(currentEmployee.getFullName());
            }
        }
    }
}