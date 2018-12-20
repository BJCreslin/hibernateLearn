package Utils;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class ReaderXLSFile {
    public static String PATHTOFILES = "\\\\Post\\креслин владимир\\перемещение\\";
    public static String NAMEXLSFILEWITHNEEDEDOSTATKIBAZA8 = "Копия ПЕРЕМЕЩЕНИЯ.xls";  // Файл с данными по вместимости ячеек
    public static String NAMEXLSFILEWITHOSTATKICENTRALNY = "центральный.xls";   //Файл с осттаками на складе Центральный
    public static String NAMEXLSFILEWITHOSTATKIBAZA8 = "выставкасовп.xls";   //Файл с остатками на выставке
    public static String NAMEOUTFILE = "out.txt";   //файл для вывода результата. Что бы 1С понимала нормально . Формат utf-16 !!!!
    String makeItemsFile = "makeItem.txt";   //файл для вывода списка позиций, которые надо сделать.
    // Что бы 1С понимала нормально . Формат utf-16 !!!!

    public static HashMap<Integer, String> mapCodeName = new HashMap<>();

    public static HashMap<Integer, Integer> getOstatkiVystavka() {
        HashMap<Integer, Integer> resultMap = new HashMap<>();
        resultMap = getIntegerIntegerHashMap(resultMap, NAMEXLSFILEWITHOSTATKIBAZA8);
        return resultMap;
    }

    private static HashMap<Integer, Integer> fillMap(HSSFSheet sheet) {
        HashMap<Integer, Integer> itemHashMap = new HashMap<>();
        boolean flagToStop = false;
        int iPos = 0;
        int shiftV = 7;
        int stolbecCode = centralStolbecCodeFind();
        int stolbecNumber = centralStolbecNumberFind();


        while (!flagToStop) {
            int code = 0;
            boolean propuskaem = false;
            try {
                code = (int) sheet.getRow(iPos + shiftV).getCell(stolbecCode).getNumericCellValue();
            } catch (IllegalStateException Illex) {
                propuskaem = true;
                flagToStop = true;
            } catch (NullPointerException npe) {
                propuskaem = true;
                flagToStop = true;
            }

            int number = 0;
            try {
                number = (int) sheet.getRow(iPos + shiftV).getCell(stolbecNumber).getNumericCellValue();
            } catch (NullPointerException npe) {
                propuskaem = true;
                flagToStop = true;
            }

            if ((number > 0) && (!flagToStop) && (!propuskaem)) {
                itemHashMap.put(code, number);
            }
            iPos++;
        }
        return itemHashMap;
    }

    private static int centralStolbecNumberFind() {
        return 6;
    }

    private static int centralStolbecCodeFind() {
        return 1;
    }

    public static HashMap<Integer, Integer> getOstatkiCentral() {
        HashMap<Integer, Integer> resultMap = new HashMap<>();
        resultMap = getIntegerIntegerHashMap(resultMap, NAMEXLSFILEWITHOSTATKICENTRALNY);
        return resultMap;
    }

    public static HashMap<Item, Integer> getOstatkiNeeded() {
        HashMap<Item, Integer> resultMap = new HashMap<>();
        resultMap = getItemIntegerHashMapNeeded(resultMap, NAMEXLSFILEWITHNEEDEDOSTATKIBAZA8);
        //  WriteTXTFiles.saveTransferFile(resultMap, "test.txt");
        return resultMap;
    }


    private static HashMap<Item, Integer> getItemIntegerHashMapNeeded(HashMap<Item, Integer> resultMap, String nameFile) {
        try (FileInputStream inputStreamFile = new FileInputStream(new File(PATHTOFILES + nameFile))) {
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStreamFile);
            HSSFSheet sheet = hssfWorkbook.getSheetAt(0);

            resultMap = fillMapNeededItem(sheet);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    private static HashMap<Item, Integer> fillMapNeededItem(HSSFSheet sheet) {
        HashMap<Item, Integer> itemHashMap = new HashMap<>();
        boolean flagToStop = false;
        int iPos = 0;
        int shiftV = 7;
        int stolbecCode = centralStolbecCodeFind();
        int stolbecNumber = centralStolbecNumberFind();
        int stolbecName = centralStolbecNameFind();
        int stolbecGroup = centralStolbecGroupFind();


        while (!flagToStop) {
            int code = 0;
            boolean propuskaem = false;
            try {
                code = (int) sheet.getRow(iPos + shiftV).getCell(stolbecCode).getNumericCellValue();
            } catch (IllegalStateException Illex) {
                try {
                    String str = sheet.getRow(iPos + shiftV).getCell(stolbecCode).getStringCellValue().replaceAll(" ", "");
                    try {
                        code = Integer.parseInt(str);

                    } catch (Exception ex2) {
                        if (sheet.getRow(iPos + shiftV).getCell(stolbecCode).getStringCellValue().equals("THEEND")) {
                            flagToStop = true;
                            propuskaem = true;
                        }
                    }
                } catch (Exception ex) {
                    propuskaem = true;
                }
            } catch (NullPointerException npe) {
                propuskaem = true;
            }

            int number = 0;
            try {
                number = (int) sheet.getRow(iPos + shiftV).getCell(stolbecNumber).getNumericCellValue();
            } catch (IllegalStateException ill) {
                propuskaem = true;
            } catch (NullPointerException npe) {
                propuskaem = true;
            }

            if ((number > 0) && (!flagToStop) && (!propuskaem)) {
                Item item;
                try {
                    item = new Item(code, sheet.getRow(iPos + shiftV).getCell(stolbecName).getStringCellValue());
                } catch (java.lang.IllegalStateException Excx) {
                    item = new Item(code, String.valueOf(sheet.getRow(iPos + shiftV).getCell(stolbecName).getNumericCellValue()));
                }

                /*Записываем группу в item*/
                String groupe;
                try {
                    groupe = sheet.getRow(iPos + shiftV).getCell(stolbecGroup).getStringCellValue();
                } catch (Exception ex) {
                    groupe = "";
                }
                item.setGroupItem(new Groups(groupe));

                /*Записывааем в мапу данные*/
                itemHashMap.put(item, number);
            }
            iPos++;
        }
        return itemHashMap;
    }

    private static int centralStolbecNameFind() {
        return 2;

    }

    private static int centralStolbecGroupFind() {
        return 0;

    }


    private static HashMap<Integer, Integer> getIntegerIntegerHashMapNeeded(HashMap<Integer, Integer> resultMap, String nameFile) {
        try (FileInputStream inputStreamFile = new FileInputStream(new File(PATHTOFILES + nameFile))) {
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStreamFile);
            HSSFSheet sheet = hssfWorkbook.getSheetAt(0);

            resultMap = fillMapNeeded(sheet);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
    }


    private static HashMap<Integer, Integer> getIntegerIntegerHashMap(HashMap<Integer, Integer> resultMap, String nameFile) {
        try (FileInputStream inputStreamFile = new FileInputStream(new File(PATHTOFILES + nameFile))) {
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStreamFile);
            HSSFSheet sheet = hssfWorkbook.getSheetAt(0);

            resultMap = fillMap(sheet);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    private static HashMap<Integer, Integer> fillMapNeeded(HSSFSheet sheet) {
        HashMap<Integer, Integer> itemHashMap = new HashMap<>();
        boolean flagToStop = false;
        int iPos = 0;
        int shiftV = 7;
        int stolbecCode = centralStolbecCodeFind();
        int stolbecNumber = centralStolbecNumberFind();


        while (!flagToStop) {
            int code = 0;
            boolean propuskaem = false;
            try {
                code = (int) sheet.getRow(iPos + shiftV).getCell(stolbecCode).getNumericCellValue();
            } catch (IllegalStateException Illex) {
                try {
                    String str = sheet.getRow(iPos + shiftV).getCell(stolbecCode).getStringCellValue().replaceAll(" ", "");
                    try {
                        code = Integer.parseInt(str);

                    } catch (Exception ex2) {
                        if (sheet.getRow(iPos + shiftV).getCell(stolbecCode).getStringCellValue().equals("THEEND")) {
                            flagToStop = true;
                            propuskaem = true;
                        }
                    }
                } catch (Exception ex) {
                    propuskaem = true;
                }
            } catch (NullPointerException npe) {
                propuskaem = true;
            }

            int number = 0;
            try {
                number = (int) sheet.getRow(iPos + shiftV).getCell(stolbecNumber).getNumericCellValue();
            } catch (IllegalStateException ill) {
                propuskaem = true;
            } catch (NullPointerException npe) {
                propuskaem = true;
            }

            if ((number > 0) && (!flagToStop) && (!propuskaem)) {
                itemHashMap.put(code, number);
            }
            iPos++;
        }
        return itemHashMap;
    }


}


