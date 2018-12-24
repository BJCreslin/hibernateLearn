package Utils.files;

import Models.ItemTable;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class readerXLS {
    public static String PATHTOFILES = "\\\\Post\\креслин владимир\\перемещение\\";
    public static String NAMEXLSFILEWITHNEEDEDOSTATKIBAZA8 = "Копия ПЕРЕМЕЩЕНИЯ.xls";  // Файл с данными по вместимости ячеек
    public static String NAMEXLSFILEWITHOSTATKICENTRALNY = "центральный.xls";   //Файл с осттаками на складе Центральный
    public static String NAMEXLSFILEWITHOSTATKIBAZA8 = "выставкасовп.xls";   //Файл с остатками на выставке
    public static String NAMEXLSFILEWITHOSTATKI = "остатки.xls";
    // Что бы 1С понимала нормально . Формат utf-16 !!!!


    public static List<ItemTable> getBaza8List() {
        List<ItemTable> itemtableList = new ArrayList<>();
        try (FileInputStream inputStreamFile = new FileInputStream(new File(PATHTOFILES +
                NAMEXLSFILEWITHOSTATKI))) {
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStreamFile);
            HSSFSheet sheet = hssfWorkbook.getSheetAt(0);

            boolean flagToStop = false;
            int iPos = 0;
            int shiftV = 7;
            int stolbecCode = centralStolbecCodeFind();
            int stolbecNumber = centralStolbecNumberFind();
            int stolbecBaza8 = stolbecBaza8Find();
            int stolbecName = 5;


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

                int remnantsCentral = 0;
                int remnantsBaza8 = 0;
                try {
                    remnantsCentral = (int) sheet.getRow(iPos + shiftV).getCell(stolbecNumber).getNumericCellValue();
                } catch (NullPointerException npe) {
                    propuskaem = true;
                    flagToStop = true;
                }
                try {
                    remnantsBaza8 = (int) sheet.getRow(iPos + shiftV).getCell(stolbecBaza8).getNumericCellValue();
                } catch (NullPointerException npe) {
                    propuskaem = true;
                    flagToStop = true;
                }


                if ((!flagToStop) && (!propuskaem)) {
                    ItemTable itemTable = new ItemTable();
                    try {
                        itemTable.setName(sheet.getRow(iPos + shiftV).getCell(stolbecName).getStringCellValue());

                    } catch (java.lang.IllegalStateException Excx) {
                        itemTable.setName(String.valueOf(sheet.getRow(iPos + shiftV).getCell(stolbecName).getNumericCellValue()));
                    }
                    itemTable.setCode(code);
                    itemTable.setCentral(remnantsCentral);
                    itemTable.setVystavka(remnantsBaza8);

                    itemtableList.add(itemTable);
                    iPos++;
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return itemtableList;
    }


    public static List<ItemTable> getItemList() {
        List<ItemTable> itemtableList = new ArrayList<>();
        try (FileInputStream inputStreamFile = new FileInputStream(new File
                (PATHTOFILES + NAMEXLSFILEWITHNEEDEDOSTATKIBAZA8))) {
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStreamFile);
            HSSFSheet sheet = hssfWorkbook.getSheetAt(0);

            boolean flagToStop = false;
            int iPos = 0;
            int shiftV = 7;
            int stolbecCode = centralStolbecCodeFind();
            int stolbecNumber = 6;
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
                    ItemTable item;
                    try {
                        item = new ItemTable(code, number, sheet.getRow(iPos + shiftV).getCell(stolbecName).getStringCellValue());
                    } catch (java.lang.IllegalStateException Excx) {
                        item = new ItemTable(code, number, String.valueOf(sheet.getRow(iPos + shiftV).getCell(stolbecName).getNumericCellValue()));
                    }

                    /*Записываем группу в item*/
                    String groupe;
                    try {
                        groupe = sheet.getRow(iPos + shiftV).getCell(stolbecGroup).getStringCellValue();
                    } catch (Exception ex) {
                        groupe = "";
                    }
                    item.setGroupname(groupe);

                    itemtableList.add(item);
                }
                iPos++;
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return itemtableList;
    }


    private static int centralStolbecNameFind() {
        return 2;
    }

    private static int centralStolbecGroupFind() {
        return 0;
    }

    private static int centralStolbecNumberFind() {
        return 7;
    }


    private static int stolbecBaza8Find() {
        return 6;
    }

    private static int centralStolbecCodeFind() {
        return 1;
    }
}
