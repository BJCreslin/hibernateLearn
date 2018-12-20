package Utils.files;

import entities.ItemtableEntity;
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
    // Что бы 1С понимала нормально . Формат utf-16 !!!!

    public static List<ItemtableEntity> getItemList() {
        List<ItemtableEntity> itemtableEntityList = new ArrayList<>();
        try (FileInputStream inputStreamFile = new FileInputStream(new File
                (PATHTOFILES + NAMEXLSFILEWITHNEEDEDOSTATKIBAZA8))) {
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStreamFile);
            HSSFSheet sheet = hssfWorkbook.getSheetAt(0);

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
                    ItemtableEntity item;
                    try {
                        item = new ItemtableEntity(code,number, sheet.getRow(iPos + shiftV).getCell(stolbecName).getStringCellValue());
                    } catch (java.lang.IllegalStateException Excx) {
                        item = new ItemtableEntity(code, number, String.valueOf(sheet.getRow(iPos + shiftV).getCell(stolbecName).getNumericCellValue()));
                    }

                    /*Записываем группу в item*/
                    String groupe;
                    try {
                        groupe = sheet.getRow(iPos + shiftV).getCell(stolbecGroup).getStringCellValue();
                    } catch (Exception ex) {
                        groupe = "";
                    }
                    item.(new Groups(groupe));

                    /*Записывааем в мапу данные*/
                    itemHashMap.put(item, number);
                }
                iPos++;
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return itemtableEntityList;
    }


    private static int centralStolbecNameFind() {
        return 2;
    }

    private static int centralStolbecGroupFind() {
        return 0;
    }

    private static int centralStolbecNumberFind() {
        return 6;
    }

    private static int centralStolbecCodeFind() {
        return 1;
    }
}
