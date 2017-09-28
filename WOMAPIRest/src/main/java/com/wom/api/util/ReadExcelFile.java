package com.wom.api.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelFile 
{
	@SuppressWarnings("unused")
	public static void main(String[] args) 
	{
		try
		{
			FileInputStream file = new FileInputStream(new File("test.xlsx"));

			//Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			//Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			//Iterate through each rows one by one
			Iterator<Cell> rowIterator = sheet.getRow(5).iterator();
			
			StringBuffer sqlValues = new StringBuffer();
			
			String word = null;
			String brand = null;
			String product = null;
			String weight = null;
			String mass = null;
			String quantity = null;
			String price = null;
			String store = null;
			String photocode = null;
			
			
			while (rowIterator.hasNext()) 
			{
				Row row = (Row) rowIterator.next();
				//For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
				
				if (row.getRowNum() >= 7){
					while (cellIterator.hasNext()) 
					{
						Cell cell = cellIterator.next();
						
						if (cell.getColumnIndex()!=0){
							sqlValues.append("'");
							switch (cell.getCellType()) 
							{
								case Cell.CELL_TYPE_NUMERIC:
									System.out.print(cell.getNumericCellValue() + "\t");
									break;
								case Cell.CELL_TYPE_STRING:
									word = cell.getStringCellValue();
									if ((word.startsWith("Ê")) || (word.startsWith(" ")))
										word = word.substring(1, word.length());
									//System.out.print("http://shop.coles.com.au/wcsstore/Coles-CAS/images/"+word.substring(0, 1)+"/"+word.substring(1, 2)+"/" +word.substring(2, 3)+"/" +word.substring(0, word.length()-1)+".jpg");
									break;
							}
							if (cell.getColumnIndex() == 1){}
							
							sqlValues.append(word);
							sqlValues.append("',");
						}
					}
				}
				
			}
			file.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
