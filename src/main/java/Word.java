import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Класс-база данных. Самый выжный класс в программе
 * @autor Svetlana Berelekhis
 * @version 1.0
 */
public class Word {
    /** вектор, в котором хранятся данные*/
    Vector<Flat> flats;
    /** дата создания*/
    private final Date date;
    /** set из шв, которые уже присвоены*/
    private Set<Long> ides;
    /** рандом для выбора следующего id*/
    Random random;
    /** порядок в базе*/
    boolean isAscending;

    /**
     * Констуктор - создание нового объекта класса
     * @param fla - массив квартир, которые уже
     */
    public Word(Flat[] fla){
        flats = new Vector<>();
        flats.addAll(Arrays.asList(fla));
        date = new Date();
        ides = new HashSet<>();
        random = new Random();
        isAscending = true;
        for(Flat flat : flats){
            ides.add(flat.getId());
        }
        sortedThis();
    }


    /**
     * Функция для передачи команды на исполнение
     * @param command - команды
     */
    public void execute(Command command){
        if (command == Command.HELP){
            helpCom();
        }else if (command == Command.INFO){
            infoCom();
        }else if (command == Command.SHOW){
            showCom();
        } else if (command == Command.CLEAR){
            clearCom();
        } else if (command == Command.REORDER){
            reorderCom();
        }else if (command == Command.PRINT_DESCENDIND){
            prDesCom();
        }
    }

    /**
     * Функция для передачи команды на исполнение
     * @param command - команды
     * @param id - long id, пользовательские данные
     */
    public void execute(Command command, long id) throws IdNotFoundException {
        if (command == Command.REMOVE_BY_ID){
            removeByIdCom(id);
        }

    }

    /**
     * Функция для передачи команды на исполнение
     * @param command - команды
     * @param id - long id
     * @param flatDTO - пользовательские данные
     */
    public void execute(Command command, Long id,  FlatDTO flatDTO) throws IdNotFoundException {
        if (command == Command.UPDATE_ID){
            updateIdComm(id, flatDTO);
        }

    }

    /**
     * Функция для передачи команды на исполнение
     * @param command - команды
     * @param flatDTO - пользовательские данные
     */
    public void execute(Command command, FlatDTO flatDTO) throws ToManyIndexesException {
        if (command == Command.ADD){
            addCom(flatDTO);
        }else if (command == Command.ADD_IF_MIN){
            addIfMINCom(flatDTO);
        }
    }

    /**
     * Функция для передачи команды на исполнение
     * @param command - команды
     * @param i - int, пользовательские данные
     */
    public void execute(Command command, int i){
        if (command == Command.COUNT_LESS_THEN_NUMBER_OF_ROOMS){
            countLessCom(i);
        } else if (command == Command.REMOVE_AT){
            removeAtCom(i);
        }
    }

    /**
     * Функция для передачи команды на исполнение
     * @param command - команды
     * @param transport - Transport, пользоватеьские данные
     */
    public void execute(Command command, Transport transport){
        if (command == Command.FILTER_GREATER_THAN_TRANSPORT){
            filterGreaterTr(transport);
        }
    }
    /**
     * Функция для передачи команды на исполнение
     * @param command - команды
     * @param file - файл для серилизации
     */
    public void execute(Command command, File file){
        if (command == Command.SAVE){
            saveCom(file);
        }

    }

    /**
     * Функция для выполнения команды PRINT_DESCENDIND
     */
    private void prDesCom(){
        int i = flats.size() - 1;
        while (i >= 0){
            Printer.print(flats.get(i).toString());
            i--;
        }
    }

    /**
     * Функция для выполнения команды REMOVE_AT
     * @param i - int, пользовательские данные
     */
    private void removeAtCom(int i){
        if (i >= 0 && i < flats.size()) {
            Flat f = flats.get(i);
            ides.remove(f.getId());
            flats.remove(i);
            Printer.print("квартира удалена");
        }else {
            Printer.print("Такого индекса нет");
        }
    }

    /**
     * Функция для выполнения команды SAVE
     * @param file - файл для серилизации
     */
    private void saveCom(File file){
        ObjectMapper objectMapper = new ObjectMapper();
            try {
                ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
                writer.writeValue(file, flats);
                Printer.print("Сохранено в файл");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    /**
     * Функция для выполнения команды FILTER_GREATER_THAN_TRANSPORT
     * @param transport - Transport, пользовательские данные
     */
    private void filterGreaterTr(Transport transport){
        for (Flat flat : flats){
            if (flat.getTransport() != null && flat.getTransport().getCount() > transport.getCount()){
                Printer.print(flat.toString());
            }
        }
    }

    /**
     * Функция для выполнения команды COUNT_LESS_THEN_NUMBER_OF_ROOMS
     * @param i - int, пользовательские данные
     */
    private void countLessCom(int i){
        int counter = 0;
        for (Flat flat : flats){
            if (flat.getNumberOfRooms() < i){
                counter += 1;
            }
        }
        Printer.print(counter);
    }

    /**
     * Функция для генерации следующего id
     * @return  id - long, новый id
     */
    private long generateId() throws ToManyIndexesException {
        if (ides.size() >= Integer.MAX_VALUE){
            throw new ToManyIndexesException();
        }
        long nowRand = random.nextLong();
        while (ides.contains(nowRand)){
            nowRand = random.nextLong();
            ides.add(nowRand);
        }
        return nowRand;
    }

    /**
     * Функция для выполнения команды HELP
     */
    private void helpCom(){
        for(Command command : Command.values()){
            Printer.print(command.getDescription());
        }
    }

    /**
     * Функция для выполнения команды INFO
     */
    private void infoCom(){
        Printer.print("Коллекция реализованная на " + flats.getClass().getName());
        Printer.print("Дата создания коллекции: " + date);
        Printer.print("В коллеции " + flats.size() +" элементов");
    }

    /**
     * Функция для выполнения команды SHOW
     */
    private void showCom(){
        Printer.print("В коллеции лежат следующие элементы:");
        for(Flat flat : flats){
            Printer.print(flat.toString());
        }
    }

    /**
     * Функция для выполнения команды ADD
     * @param flatDTO - FlatDTO, пользовательские данные
     */
    private void addCom(FlatDTO flatDTO) throws ToManyIndexesException {
        long id = generateId();
        addFlatToFlats(id, flatDTO);
        ides.add(id);
        if (isAscending){
            Collections.sort(flats);
        }
        sortedThis();
    }

    /**
     * Функция для выполнения команды UPDATE_ID
     * @param id - id квартиры, в которой меняются данные
     * @param flatDTO - FlatDTO, пользовательские данные
     */
    private void updateIdComm(long id, FlatDTO flatDTO) throws IdNotFoundException{
        removeByIdCom(id);
        addFlatToFlats(id, flatDTO);
        ides.add(id);
        sortedThis();

    }

    /**
     * Функция для выполнения команды REMOVE_BY_ID
     * @param id - id квартиры, которую удаляем
     */
    private void removeByIdCom(long id) throws IdNotFoundException {
        if (!ides.contains(id)){
            throw new IdNotFoundException();
        }
        int num = 0;
        Flat flat = flats.get(0);
        while (flat.getId() != id) {
            num += 1;
            flat = flats.get(num);
        }
        flats.remove(num);
        ides.remove(id);
        sortedThis();
    }

    /**
     * Функция для выполнения команды CLEAR
     */
    private void clearCom(){
        flats = new Vector<>();
        ides = new HashSet<>();
    }

    /**
     * Функция для добавления новой квартиры в вектор
     * @param id - сгенерированный id
     * @param flatDTO - FlatDTO, пользовательские данные
     */
    private void addFlatToFlats(Long id, FlatDTO flatDTO) {
        Date date = new Date();
        Flat flat = new Flat(id, date);
        flat.fromDTO(flatDTO);
        flats.add(flat);
        ides.add(id);
        sortedThis();
    }

    /**
     * Функция для выполнения команды ADD_IF_MIN
     * @param flatDTO - FlatDTO, пользовательские данные
     */
    private void addIfMINCom(FlatDTO flatDTO) throws ToManyIndexesException {
        if (flats.size() <= 0) {
            addCom(flatDTO);
        } else if (isAscending && flatDTO.name.length() < flats.get(0).getName().length()){
            addCom(flatDTO);
        } else if (!isAscending && flatDTO.name.length() < flats.get(flats.size() - 1).getName().length()){
            addCom(flatDTO);
        }
        sortedThis();
    }

    /**
     * Функция для выполнения команды REORDER
     */
    private void reorderCom(){
        isAscending = !isAscending;
        sortedThis();
    }

    /**
     * Функция, сортирующая вектор flats в зависимости от isAscending
     */
    private void sortedThis(){
        if (isAscending){
            Collections.sort(flats);
        }else {
            flats.sort(Collections.reverseOrder());
        }
    }

}
