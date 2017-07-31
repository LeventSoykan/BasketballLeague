package com.fbasketball;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import info.Player;
import info.Team;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelFileOperator {

    public List<String> mListFirstNames = new ArrayList<String>();
    public List<String> mListLastNames = new ArrayList<String>();
    public List<String> mListTeamNames = new ArrayList<String>();
    public List<Team> mImportedTeams = new ArrayList<Team>();
    private List<Player> mImportedPlayerList = new ArrayList<Player>();
    private Player mImportPlayer;
    private RandomParam mRandomParam = new RandomParam();

    public ExcelFileOperator()  {
    }
    // https://www.mkyong.com/java/apache-poi-reading-and-writing-excel-file-in-java/

    public void setListsFromPlayerFile(String excelFilePath) throws IOException {
        FileInputStream fis = new FileInputStream(excelFilePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        Sheet playerSheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = playerSheet.iterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.iterator();
            while (cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                Cell cell1 = cellIterator.next();
                mListLastNames.add(cell.getStringCellValue());
                mListFirstNames.add(cell1.getStringCellValue());
        }}
    }

    public List<String> getListTeamNames() {
        return mListTeamNames;
    }

    public void setListsFromTeamFile(String excelFilePath) throws IOException {
        FileInputStream fis = new FileInputStream(excelFilePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        Sheet playerSheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = playerSheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator <Cell> cellIterator = row.iterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            mListTeamNames.add(cell.getStringCellValue());
        } }

    }

    public List<String> getListFirstNames() {

        return mListFirstNames;
    }

    public List<String> getListLastNames() {
        return mListLastNames;
    }

    public List<Team> importTeams () throws IOException {
       setListsFromTeamFile("C:\\Users\\Karate Kid\\IdeaProjects\\BasketballLeague\\src\\Team_WorkBook.xlsx");
        for (int i=1; i<mListTeamNames.size()+1; i++) {
            Team team = new Team(getListTeamNames().get(i-1));
            mImportedTeams.add(team);
        }
        return mImportedTeams;
    }

    public List<Player> importPlayers () throws IOException {
        setListsFromPlayerFile("C:\\Users\\Karate Kid\\IdeaProjects\\BasketballLeague\\src\\Player2_Workbook.xlsx");
        for (int i=1; i<mListFirstNames.size()+1; i++) {
            mImportPlayer = new Player(mRandomParam.randomNumber(), getListFirstNames().get(i-1),
                    getListLastNames().get(i-1));
            mImportedPlayerList.add(mImportPlayer);
        }
        return mImportedPlayerList;
    }

    public void exportStatsToExcel (List<String> strings) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Basketball Stat");

        int rowNum = 0;

        for (String string: strings) {
            Row row = sheet.createRow(rowNum++);
            Cell cell = row.createCell(0);
            cell.setCellValue(string);
        }

        FileOutputStream fos = new FileOutputStream(new File("savedStat.xls"));
        workbook.write(fos);
        workbook.close();
    }

    public void exportTabletoExcel (List<Player> players) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("PlayerStats");

        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);
        Cell label = row.createCell(0);
        label.setCellValue("Player Number");
        Cell label2 = row.createCell(1);
        label2.setCellValue("Player Name");
        Cell label3 = row.createCell(2);
        label3.setCellValue("Point Per Game");
        Cell label4 = row.createCell(3);
        label4.setCellValue("Team Name");

        for (Player player : players) {
            Row dataRow = sheet.createRow(rowNum++);
            Cell cell1 = dataRow.createCell(0);
            cell1.setCellValue(player.getPlayerNo());
            Cell cell2 = dataRow.createCell(1);
            cell2.setCellValue(player.getPlayerName());
            Cell cell3 = dataRow.createCell(2);
            cell3.setCellValue(player.getPointsPerGame());
            Cell cell4 = dataRow.createCell(3);
            cell4.setCellValue(player.getTeamName());
        }
        FileOutputStream fileOutputStream = new FileOutputStream(new File("playerData.xls"));
        workbook.write(fileOutputStream);
        workbook.close();
    }

}
