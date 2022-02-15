import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;

/**
 * Класс для первичного разбора команд, написанных в скрипте.
 * @autor Svetlana Berelekhis
 * @version 1.0
 */
public class ScriptExecuter {
    /** имя файла, из которого читаем скрипт*/
    String fileName;
    /** база, с которой работаем*/
    Word word;
    /** имя файла, в который серилизуем*/
    String fileToSave;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param fileName - String, имя файла, из которого читаем скрипт
     * @param word - Word, база, с которой работаем
     * @param fileToSave - String, имя файла, в который серилизуем
     */
    public ScriptExecuter(String fileName, Word word, String fileToSave) {
        this.fileName = fileName;
        this.word = word;
        this.fileToSave = fileToSave;
    }

    /**
     * Функция, выполняющая первичный разбор команд
     */
    public void execute(){
        try {
            Scanner scanner = new Scanner(new File(this.fileName));
            String com = scanner.next();
            while (!"exit".equals(com)){
                if ("help".equals(com)) {
                    word.execute(Command.HELP);
                }else if ("info".equals(com)){
                    word.execute(Command.INFO);
                }else if ("show".equals(com)){
                    word.execute(Command.SHOW);
                }else if ("add".equals(com)){
                    try {
                        word.execute(Command.ADD, readFlatDTO(scanner));
                    } catch (WrongFormAtScriptException e) {
                        Printer.print("команда add пропущена из-за неверного формата данных");
                    } catch (ToManyIndexesException e) {
                        Printer.print("команда add пропущена из-за переполнения базы");
                    } catch (NumberFormatException e){
                        Printer.print("команда add пропущена из-за неверного ввода числа");
                    }
                }else if("update id".equals(com)){
                    try {
                        word.execute(Command.UPDATE_ID, readId(scanner), readFlatDTO(scanner));
                    } catch (IdNotFoundException e) {
                        Printer.print("команда update id пропущена из-за того, что индекс не найден");
                    } catch (WrongFormAtScriptException e) {
                        Printer.print("команда update id пропущена из-за неверного формата данных");
                    } catch (NumberFormatException e){
                        Printer.print("команда update id пропущена из-за неверного ввода числа");
                    }
                } else if ("remove_by_id".equals(com)){
                    try {
                        word.execute(Command.REMOVE_BY_ID, readId(scanner));
                    } catch (IdNotFoundException e) {
                        Printer.print("команда remove_by_id пропущена из-за того, что индекс не найден");
                    } catch (NumberFormatException e){
                        Printer.print("команда remove_by_id пропущена из-за неверного ввода числа");
                    }
                }else if ("clear".equals(com)){
                    word.execute(Command.CLEAR);
                } else if ("save".equals(com)){
                    saveToFile(new File(fileToSave), word);
                }else if ("execute_script".equals(com)){
                    ScriptExecuter scriptExecuter = new ScriptExecuter(scanner.next(), word, fileToSave);
                    scriptExecuter.execute();
                } else if ("remove_at".equals(com)){
                    try {
                        int index = (int) readId(scanner);
                        word.execute(Command.REMOVE_AT, index);
                    }catch (NumberFormatException e){
                        Printer.print("команда remove_at пропущена из-за неверного ввода числа");
                    }
                } else if ("add_if_min".equals(com)){
                    try {
                        word.execute(Command.ADD_IF_MIN, readFlatDTO(scanner));
                    } catch (ToManyIndexesException e) {
                        Printer.print("команда add_if_min пропущена из-за того, вектор заполнен полностью");
                    } catch (WrongFormAtScriptException e) {
                        Printer.print("команда add_if_min пропущена из-за неверного ввода");
                    }
                } else if ("reorder".equals(com)) {
                    word.execute(Command.REORDER);
                }  else if ("print_descending".equals(com)) {
                    word.execute(Command.PRINT_DESCENDIND);
                } else if ("count_less_than_number_of_rooms".equals(com)) {
                    try {
                        int numbOfRooms = (int) readId(scanner);
                        word.execute(Command.COUNT_LESS_THEN_NUMBER_OF_ROOMS, numbOfRooms);
                    }catch (NumberFormatException e){
                        Printer.print("команда count_less_than_number_of_rooms пропущена из-за неверного ввода числа");
                    }
                }else if ("filter_greater_than_transport".equals(com)){
                    try {
                        word.execute(Command.FILTER_GREATER_THAN_TRANSPORT, readTransport(scanner));
                    } catch (WrongFormAtScriptException e) {
                        Printer.print("команда filter_greater_than_transport пропущена из-за неверного ввода transport");
                    }
                }
                com = scanner.next();
            }
        } catch (FileNotFoundException e) {
            Printer.print("Такго файла нет");
        }

    }

    /**
     * Функция, считывающая Transport
     * @return Transport transport
     */
    Transport readTransport(Scanner scanner) throws WrongFormAtScriptException {
        Transport transport;
        String str = scanner.next();
        if ("FEW".equals(str)) {
            transport = Transport.FEW;
        } else if ("NONE".equals(str)) {
            transport = Transport.NONE;
        } else if ("LITTLE".equals(str)) {
            transport = Transport.LITTLE;
        } else if ("ENOUGH".equals(str)) {
            transport = Transport.ENOUGH;
        } else {
            throw new WrongFormAtScriptException();
        }
        return transport;
    }

    /**
     * Функция, считывающая long
     * @return long
     */
    long readId(Scanner scanner) throws NumberFormatException {
        return Long.parseLong(scanner.next());
    }

    /**
     * Функция, считывающая flatDTO
     * @return FlatDTO flatDTO
     */
    FlatDTO readFlatDTO(Scanner scanner) throws WrongFormAtScriptException, NumberFormatException {
        scanner.nextLine();
        String name = scanner.nextLine();
        if ("".equals(name)) {
            throw new WrongFormAtScriptException();
        }
        long coordX = Long.parseLong(scanner.next());
        int coordY = Integer.parseInt(scanner.next());
        Coordinates coordinates = new Coordinates(coordX, coordY);
        long area = Long.parseLong(scanner.next());
        if (area <= 0) {
            throw new WrongFormAtScriptException();
        }
        long numberOfRooms = Long.parseLong(scanner.next());
        if (numberOfRooms <= 0){
            throw new WrongFormAtScriptException();
        }
        scanner.nextLine();
        String str = scanner.nextLine();
        Furnish furnish;
        if ("DESIGNER".equals(str)) {
            furnish = Furnish.DESIGNER;
        } else if ("FINE".equals(str)) {
            furnish = Furnish.FINE;
        } else if ("BAD".equals(str)) {
            furnish = Furnish.BAD;
        } else if ("LITTLE".equals(str)) {
            furnish = Furnish.LITTLE;
        } else {
                throw new WrongFormAtScriptException();
        }
        View view = null;
        str = scanner.nextLine();
        if("PARK".equals(str)){
            view = View.PARK;
        }else if ("GOOD".equals(str)){
            view = View.GOOD;
        }else if ("TERRIBLE".equals(str)){
            view = View.TERRIBLE;
        } else if (!"".equals(str)){
            throw new WrongFormAtScriptException();
        }
        Transport transport = null;
        str = scanner.nextLine();
        if ("FEW".equals(str)){
            transport = Transport.FEW;
        }else if ("NONE".equals(str)){
            transport = Transport.NONE;
        }else if ("LITTLE".equals(str)){
            transport = Transport.LITTLE;
        }else if ("ENOUGH".equals(str)){
            transport = Transport.ENOUGH;
        }else {
            throw new WrongFormAtScriptException();
            }
        str = scanner.nextLine();
        House house = null;
        if (!"".equals(str)){
            int nowYear = Integer.parseInt(scanner.next());
            if (nowYear <= 0) {
                throw new WrongFormAtScriptException();
            }
            int nowNumberOfFloors = Integer.parseInt(scanner.next());
            if (nowNumberOfFloors <= 0){
                throw new WrongFormAtScriptException();
            }
            house = new House(str, nowYear, nowNumberOfFloors);
            }
        Date nowDate = new Date();
        return new FlatDTO(name, coordinates, nowDate, area, numberOfRooms, furnish, view, transport, house);
    }
    /**
     * Функция, передающая базе команду на серилизацию
     */
    static void saveToFile(File file, Word word){
        word.execute(Command.SAVE, file);
    }
}

