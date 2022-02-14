public class Coordinates {
    private Long x; //Поле не может быть null
    private Integer y; //Максимальное значение поля: 942, Поле не может быть null

    public Coordinates(Long x, Integer y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "x=" + x +
                ", y=" + y;
    }

    public Long getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Coordinates() {
    }
}

