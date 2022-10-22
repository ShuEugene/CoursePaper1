import java.util.Objects;

public class Department {

    private int id;
    private String name;

//  *********

    Department(String name) {
        if (DataService.parameterIsCorrect(name)) {
            this.id = Departments.getDepartmentsCount(DataService.ServiceMode.INCREASE);
            this.name = name;
        } else throw new IllegalArgumentException(DataService.NULL_STRING_MESS);
    }

//  *********

//  Убить, если не использую !«
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
//  »!

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("Подразделение: %s (ID: %d)", name, id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return id == that.id && name.equals(that.name);
    }
}
