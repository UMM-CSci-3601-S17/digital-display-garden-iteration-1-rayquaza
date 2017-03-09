package umm3601.parsing;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Sheet;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC;


import java.io.FileInputStream;
import java.util.Iterator;

/**
 * Thanks to UVM!
 * http://stackoverflow.com/questions/11343931/converting-excel-to-json
 */
public class ParseExcel{

    private static int sheetNumber = 0;
    private static int numHeaderRows = 4;

    public static void testParsing(){


        try {
            FileInputStream inp = new FileInputStream("/tmp/digital-display-garden/Accession list 2016 for Steves Design.xls");
            Workbook workbook = WorkbookFactory.create(inp);

            // Get the first Sheet.
            Sheet sheet = workbook.getSheetAt(sheetNumber);

            // Start constructing JSON.
//            JSONObject json = new JSONObject();

            // Iterate through the rows.
//            JSONArray rows = new JSONArray();
            boolean foundEmptyNumberCell = false;

            for (Iterator<Row> rowsIT = sheet.rowIterator(); rowsIT.hasNext() && !foundEmptyNumberCell; ) {

                Row row = rowsIT.next();
                Iterator<Cell> cellsIT = row.cellIterator();

                if ( row.getRowNum() > numHeaderRows - 1) {

                    for (int i = 0; i < 7; i++) {
                        Cell cell = cellsIT.next();
                        if (cell.getColumnIndex() == 0 && cell.getNumericCellValue() == 0) {
                            foundEmptyNumberCell = true;
                            break;
                        }
                        else if (cell.getCellType() == CELL_TYPE_NUMERIC ) {
                            System.out.print(cell.getNumericCellValue() + ", ");
                        } else {
                            System.out.print(cell.getStringCellValue()+ ", ");
                        }

                    }
                    System.out.println();
//                    for (Iterator<Cell> cellsIT = row.cellIterator(); cellsIT.hasNext(); ) {
//                        Cell cell = cellsIT.next();
//                        System.out.println(cell.getRowIndex());
//                        if (cell.getCellType() == CELL_TYPE_NUMERIC) {
//                            System.out.println(cell.getNumericCellValue());
//                        } else {
//                            System.out.println(cell.getStringCellValue());
//                        }
////                    cells.put(cell.getStringCellValue());
//                    }
                }

//                jRow.put("cell", cells);
//                rows.put(jRow);
            }

            // Create the JSON.
//            json.put("rows", rows);n json.toString();
//            json.toString();

            // Get the JSON text.
//        return json.toString();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
//cell.getColumnIndex() == 0 && cell.getRowIndex() > 3






