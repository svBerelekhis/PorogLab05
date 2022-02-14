import java.util.Date;

public class FlatDTO{
    String name; //Поле не может быть null, Строка не может быть пустой
    Coordinates coordinates; //Поле не может быть null
    Long area; //Поле может быть null, Значение поля должно быть больше 0
    Long numberOfRooms; //Значение поля должно быть больше 0
    Furnish furnish; //Поле не может быть null
    View view; //Поле не может быть null
    Transport transport; //Поле может быть null
    House house; //Поле может быть null
    Date creationDate;

    public FlatDTO(String name, Coordinates coordinates, Date creationDate, Long area, Long numberOfRooms, Furnish furnish, View view, Transport transport, House house) {
        this.name = name;
        this.coordinates = coordinates;
        this.numberOfRooms = numberOfRooms;
        this.furnish = furnish;
        this.view = view;
        this.creationDate = creationDate;
        this.transport = transport;
        this.house = house;
        this.area = area;
    }
}
