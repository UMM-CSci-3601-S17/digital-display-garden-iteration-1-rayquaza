package umm3601.parsing;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Sheet;
import org.bson.Document;

import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC;


import java.io.FileInputStream;
import java.util.*;


public class ParseExcel{

    private static int sheetNumber = 0;
    private static int numHeaderRows = 4;

    public static void testParsing(String fileName){


        try {
            FileInputStream inp = new FileInputStream(fileName);
            Workbook workbook = WorkbookFactory.create(inp);

            Sheet sheet = workbook.getSheetAt(sheetNumber);

            List<Document> documentList = new LinkedList<>();

            boolean foundEmptyNumberCell = false;

            for (Iterator<Row> rowsIT = sheet.rowIterator(); rowsIT.hasNext() && !foundEmptyNumberCell; ) {

                Row row = rowsIT.next();
                Iterator<Cell> cellsIT = row.cellIterator();

                if ( row.getRowNum() > numHeaderRows - 1) {
                    Document plant = new Document();

                    for (int i = 0; i < 7; i++) {

                        Cell cell = cellsIT.next();
                        switch (i) {
                            case 0:
                                // The # column
                                plant.append("plantId", (int)cell.getNumericCellValue());
                                break;
                            case 1:
                                // Common Name
                                plant.append("commonName", cell.getStringCellValue());
                                break;
                            case 2:
                                // Cultivar
                                plant.append("cultivar", cell.getStringCellValue());
                                break;
                            case 3:
                                // Source
                                plant.append("source", cell.getStringCellValue());
                                break;
                            case 4:
                                // ???
                                 break;
                            case 5:
                                // ????
                                break;
                            case 6:
                                // Garden Location
                                if (cell.getCellType() == CELL_TYPE_NUMERIC ) {
                                    plant.append("gardenLocation", new Double(cell.getNumericCellValue()).toString());
                                } else {
                                    plant.append("gardenLocation", cell.getStringCellValue());
                                }
                                break;
                        }

                        if (cell.getColumnIndex() == 0 && cell.getNumericCellValue() == 0) {
                            foundEmptyNumberCell = true;
                            break;
                        }
//                        else if (cell.getCellType() == CELL_TYPE_NUMERIC ) {
//                            System.out.print(cell.getNumericCellValue() + ", ");
//                        } else {
//                            System.out.print(cell.getStringCellValue()+ ", ");
//                        }

                    }
                    System.out.println(plant);
                }

            }
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(new Date());
//            System.out.println(cal.get(cal.YEAR));
//            System.out.println(cal.get(cal.MONTH) + 1);
//            System.out.println(cal.get(cal.DAY_OF_MONTH));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}






