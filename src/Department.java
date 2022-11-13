import java.util.Objects;

public class Department {

    private int id;
    private String name;


    Department(String name) {
        if (DataService.paramIsCorrect(name)) {
            this.id = Departments.getDepartmentsCount(DataService.ServiceModes.INCREASE);
            this.name = name;
        } else throw new IllegalArgumentException(DataService.NULL_STRING_MESS);
    }


    public String getName() {
        return "«" + name + "»";
    }

    @Override
    public String toString() {
        return String.format("%s (ID: %d)", getName(), id);
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
