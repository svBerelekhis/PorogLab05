public class House {
    private String name; //Поле не может быть null
    private int year; //Значение поля должно быть больше 0
    private Integer numberOfFloors; //Значение поля должно быть больше 0

    public House(String name, int year, Integer numberOfFloors) {
        this.name = name;
        this.year = year;
        this.numberOfFloors = numberOfFloors;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public Integer getNumberOfFloors() {
        return numberOfFloors;
    }

    public House() {
    }

    @Override
    public String toString() {
        return "House{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", numberOfFloors=" + numberOfFloors +
                '}';
    }
}

