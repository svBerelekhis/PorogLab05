import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ToManyIndexesException, IdNotFoundException {
        String filename = "test.json";
        ObjectMapper objectMapper = new ObjectMapper();
        Flat[] masOfFlats = new Flat[0];
        try {
            masOfFlats = objectMapper.readValue(new File(filename), Flat[].class);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        Word word = new Word(masOfFlats);
        Scanner scanner = new Scanner(System.in);
        String com = scanner.next();
        while (!"exit".equals(com)){
            if ("help".equals(com)) {
                word.execute(Command.HELP);
            }else if ("info".equals(com)){
                word.execute(Command.INFO);
            }else if ("show".equals(com)){
                word.execute(Command.SHOW);
            }else if ("add".equals(com)){
                word.execute(Command.ADD, readFlatDTO(scanner));
                Printer.print("Квартира загружена");
            }else if("update".equals(com)){
                word.execute(Command.UPDATE_ID, scanner.nextLong(), readFlatDTO(scanner));
            } else if ("remove_by_id".equals(com)){
                word.execute(Command.REMOVE_BY_ID, scanner.nextLong());
            }else if ("clear".equals(com)){
                word.execute(Command.CLEAR);
            } else if ("save".equals(com)){
                word.execute(Command.SAVE, new File("test.json"));
            } else if ("execute_script".equals(com)){
                ScriptExecuter scriptExecuter = new ScriptExecuter(scanner.next(), word);
                scriptExecuter.execute();
            } else if ("remove_at".equals(com)){
                int index = -1;
                while (index < 0){
                    try {
                        String str = scanner.next();
                        index = Integer.parseInt(str);
                        if (index < 0){
                            Printer.print("Это число должно быть не меньше 0");
                        }
                    }catch (NumberFormatException e){
                        Printer.print("введите index (число не меньше 0)");
                    }
                }
                word.execute(Command.REMOVE_AT, index);
            } else if ("add_if_min".equals(com)){
                word.execute(Command.ADD_IF_MIN, readFlatDTO(scanner));
            } else if ("reorder".equals(com)) {
                word.execute(Command.REORDER);
            }  else if ("print_descending".equals(com)) {
                word.execute(Command.PRINT_DESCENDIND);
            } else if ("count_less_than_number_of_rooms".equals(com)) {
                int numbOfRooms = -1;
                while (numbOfRooms < 0){
                    try {
                        String str = scanner.next();
                        numbOfRooms = Integer.parseInt(str);
                        if (numbOfRooms < 0){
                            Printer.print("Это число должно быть больше 0");
                        }
                    }catch (NumberFormatException e){
                        Printer.print("введите количество комнат (число больше 0)");
                    }
                }
                word.execute(Command.COUNT_LESS_THEN_NUMBER_OF_ROOMS, numbOfRooms);
            }else if ("filter_greater_than_transport".equals(com)){
                Transport transport = null;
                boolean isNorm = false;
                while (!isNorm) {
                    String str = scanner.next();
                    if ("FEW".equals(str)) {
                        transport = Transport.FEW;
                        isNorm = true;
                    } else if ("NONE".equals(str)) {
                        transport = Transport.NONE;
                        isNorm = true;
                    } else if ("LITTLE".equals(str)) {
                        transport = Transport.LITTLE;
                        isNorm = true;
                    } else if ("ENOUGH".equals(str)) {
                        transport = Transport.ENOUGH;
                        isNorm = true;
                    } else {
                        Printer.print("Введите transport. Эначения могут быть FEW, NONE, LITTLE и ENOUGH");
                    }
                }
                word.execute(Command.FILTER_GREATER_THAN_TRANSPORT, transport);

            } else {
                Printer.print("Такой команды нет");
            }
            com = scanner.next();
        }
        scanner.close();
    }

    static FlatDTO readFlatDTO(Scanner scanner){
        scanner.nextLine();
        Printer.print("Введите имя. Оно не может быть пустой строкой");
        String name = scanner.nextLine();
        Printer.print("Координаты. Сначала введите x");
        boolean isRightNum = false;
        long coordX = 0;
        while (!isRightNum){
            try {
                coordX = Long.parseLong(scanner.next());
                isRightNum = true;
            }catch (NumberFormatException e){
                Printer.print("Это должно быть число");
            }

        }
        Printer.print("Теперь введите y. Это число должно быть не больше 942");
        isRightNum = false;
        int coordY = 0;
        while (!isRightNum){
            try {
                coordY = Integer.parseInt(scanner.next());
                if (coordY <= 942){
                    isRightNum = true;
                } else {
                    Printer.print("Это число должно быть не больше 942");
                }
            }catch (NumberFormatException e){
                Printer.print("y - это число");
            }
        }
        Coordinates coordinates = new Coordinates(coordX, coordY);
        Printer.print("Теперь введите aria. Должно быть больше 0");
        isRightNum = false;
        long area = -1;
        while (!isRightNum){
            try {
                area = Long.parseLong(scanner.next());
                if (area > 0){
                    isRightNum = true;
                } else {
                    Printer.print("area должно быть больше 0");
                }
            }catch (NumberFormatException e){
                Printer.print("area - это число");
            }
        }
        Printer.print("Введите количество комнат. Должно быть больше 0");
        long numberOfRooms = -1;
        isRightNum = false;
        while (!isRightNum){
            try {
                numberOfRooms = Long.parseLong(scanner.next());
                if (numberOfRooms > 0){
                    isRightNum = true;
                } else {
                    Printer.print("numberOfRooms должно быть больше 0");
                }
            }catch (NumberFormatException e){
                Printer.print("numberOfRooms - это число");
            }
        }
        scanner.nextLine();
        Printer.print("Введите furnish. Значения могут быть DESIGNER, FINE, BAD, LITTLE");
        String str = scanner.nextLine();
        Furnish furnish = null;
        while (furnish == null) {
            if ("DESIGNER".equals(str)) {
                furnish = Furnish.DESIGNER;
            } else if ("FINE".equals(str)) {
                furnish = Furnish.FINE;
            } else if ("BAD".equals(str)) {
                furnish = Furnish.BAD;
            } else if ("LITTLE".equals(str)) {
                furnish = Furnish.LITTLE;
            } else {
                Printer.print("Такого значения нет. Попробуте ещ раз");
                str = scanner.nextLine();
            }
        }
        View view = null;
        Printer.print("Введите view. Эначения могут быть PARK, GOOD и TERRIBLE");
        str = scanner.nextLine();
        while (view == null){
            if("PARK".equals(str)){
                view = View.PARK;
            }else if ("GOOD".equals(str)){
                view = View.GOOD;
            }else if ("TERRIBLE".equals(str)){
                view = View.TERRIBLE;
            } else {
                Printer.print("Такого значения нет. Попробуте ещ раз");
                str = scanner.nextLine();
            }
        }
        Transport transport = null;
        Printer.print("Введите transport. Эначения могут быть FEW, NONE, LITTLE и ENOUGH. Если вы не хотите вводить значение  - введите пустую строку");
        str = scanner.nextLine();
        boolean isNorm = false;
        while (!isNorm){
            if ("".equals(str)){
                isNorm = true;
            }else if ("FEW".equals(str)){
                transport = Transport.FEW;
                isNorm = true;
            }else if ("NONE".equals(str)){
                transport = Transport.NONE;
                isNorm = true;
            }else if ("LITTLE".equals(str)){
                transport = Transport.LITTLE;
                isNorm = true;
            }else if ("ENOUGH".equals(str)){
                transport = Transport.ENOUGH;
                isNorm = true;
            }else {
                Printer.print("Так нелюзя попробуйте ещ раз");
                str = scanner.nextLine();
            }
        }
        isNorm = false;
        Printer.print("Введите house. Сначала введите name (не может быть пустой строкой). Если вы не хотите вводить house  - введите пустую строку");
        str = scanner.nextLine();
        House house = null;
        while (!isNorm){
            if (!"".equals(str)) {
                Printer.print("Теперь введите год. Это должно быть число больше 0");
                int nowYear = readIntMoreThenNull(scanner);
                Printer.print("Теперь введите количество этажей. Это должно быть число больше 0");
                int nowNumberOfFloors = readIntMoreThenNull(scanner);
                house = new House(str, nowYear, nowNumberOfFloors);
            }
            isNorm = true;
        }
        Date nowDate = new Date();
        return new FlatDTO(name, coordinates, nowDate, area, numberOfRooms, furnish, view, transport, house);
    }

    private static int readIntMoreThenNull(Scanner scanner){
        int myInt = -1;
        while (myInt <= 0){
            try{
                myInt = Integer.parseInt(scanner.next());
                if (myInt <= 0) {
                    Printer.print("Такое число не подходит");
                }
            }catch (NumberFormatException e){
                Printer.print("Это не число. Попробуйте ещё раз");
            }
        }
        return myInt;
    }
}
