import java.util.Date;

public class Flat implements Comparable<Flat>{
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long area; //Поле может быть null, Значение поля должно быть больше 0
    private Long numberOfRooms; //Значение поля должно быть больше 0
    private Furnish furnish; //Поле не может быть null
    private View view; //Поле не может быть null
    private Transport transport; //Поле может быть null
    private House house; //Поле может быть null

    public Long getNumberOfRooms() {
        return numberOfRooms;
    }

    public Transport getTransport() {
        return transport;
    }

    public String getName() {
        return name;
    }

    public Flat(long id, Date creationDate) {
        this.id = id;
        this.creationDate = creationDate;

    }

    public Flat() {
    }

    public Flat(long id, String name, Coordinates coordinates, Date creationDate, Long area, Long numberOfRooms, Furnish furnish, View view, Transport transport, House house) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.numberOfRooms = numberOfRooms;
        this.furnish = furnish;
        this.view = view;
        this.transport = transport;
        this.house = house;
    }

    public void fromDTO(FlatDTO flatDTO){
        this.name = flatDTO.name;
        this.coordinates = flatDTO.coordinates;
        this.area = flatDTO.area;
        this.numberOfRooms = flatDTO.numberOfRooms;
        this.furnish = flatDTO.furnish;
        this.view = flatDTO.view;
        this.transport = flatDTO.transport;
        this.house = flatDTO.house;
    }

    @Override
    public String toString() {
        return "Flat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates.toString() +
                ", creationDate=" + creationDate +
                ", area=" + area +
                ", numberOfRooms=" + numberOfRooms +
                ", furnish=" + furnish +
                ", view=" + view +
                ", transport=" + transport +
                ", house=" + house +
                '}';
    }

    public long getId() {
        return id;
    }

    @Override
    public int compareTo(Flat o) {
        return this.name.length() - o.name.length();
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Long getArea() {
        return area;
    }

    public Furnish getFurnish() {
        return furnish;
    }

    public View getView() {
        return view;
    }

    public House getHouse() {
        return house;
    }
}
