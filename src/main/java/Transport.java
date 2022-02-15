/**
 * Энум, хранящий доступные значения Transport
 * @autor Svetlana Berelekhis
 * @version 1.0
 */

public enum Transport{
    FEW(3),
    NONE(0),
    LITTLE(1),
    ENOUGH(2);

    /** Int условное значение для сравнения*/
    private final int count;

    /**
     * Конструктор - создание нового объекта
     * @param count - условное значение
     */
    Transport(int count) {
        this.count = count;
    }

    /**
     * геттер условного значения
     * @return count - условное значение
     */
    public int getCount(){
        return this.count;
    }
}