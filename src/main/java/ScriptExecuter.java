import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;

public class ScriptExecuter {
    String fileName;
    Word word;
    File file;

    public ScriptExecuter(String fileName, Word word) {
        this.fileName = fileName;
        this.word = word;
    }
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
                    saveToFile(file);
                }
                com = scanner.next();
            }
        } catch (FileNotFoundException e) {
            Printer.print("Такго файла нет");
        }

    }

    static long readId(Scanner scanner) throws NumberFormatException {
        Printer.print("Введите id");
        return Long.parseLong(scanner.next());
    }
    static FlatDTO readFlatDTO(Scanner scanner) throws WrongFormAtScriptException, NumberFormatException {
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
        Furnish furnish = null;
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
        } else {
            throw new WrongFormAtScriptException();
        }
        Transport transport = null;
        str = scanner.nextLine();
        if ("".equals(str)){
        }else if ("FEW".equals(str)){
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
            String nowName = str;
            int nowYear = Integer.parseInt(scanner.next());
            if (nowYear <= 0) {
                throw new WrongFormAtScriptException();
            }
            int nowNumberOfFloors = Integer.parseInt(scanner.next());
            if (nowNumberOfFloors <= 0){
                throw new WrongFormAtScriptException();
            }
            house = new House(nowName, nowYear, nowNumberOfFloors);
            }
        Date nowDate = new Date();
        return new FlatDTO(name, coordinates, nowDate, area, numberOfRooms, furnish, view, transport, house);
    }
    static void saveToFile(File file){

    }
}

