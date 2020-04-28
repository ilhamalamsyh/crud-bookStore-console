package CRUD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Year;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Utility {
     static boolean checkBookOnDatabase(String[] keywords, boolean isDisplay)throws IOException {

        FileReader fileInput = new FileReader("database.txt");
        BufferedReader buffRead = new BufferedReader(fileInput);

        String data = buffRead.readLine();
        boolean isExist = false;
        int jumlahData = 0;

        if (isDisplay) {
            System.out.println("\n| No |\tTahun |\tPenulis              |\tPenerbit             |\tJudul");
            System.out.println("-----------------------------------------------------------------------------------------");
        }

        while (data != null){

            //Cek Keyword dalam baris
            isExist = true;

            for (String keyword:keywords) {
                isExist = isExist && data.toLowerCase().contains(keyword.toLowerCase());
            }

            //Menampilkan Keyword yang cocok
            if (isExist){
                if (isDisplay) {
                    jumlahData++;
                    StringTokenizer stringToken = new StringTokenizer(data, ",");

                    stringToken.nextToken();
                    System.out.printf("|%2d  ", jumlahData);
                    System.out.printf("|\t%5s ", stringToken.nextToken());
                    System.out.printf("|\t%-20s ", stringToken.nextToken());
                    System.out.printf("|\t%-20s ", stringToken.nextToken());
                    System.out.printf("|\t%s", stringToken.nextToken());
                    System.out.print("\n");
                }else {
                    break;
                }
            }
            data = buffRead.readLine();
        }

        if (isDisplay){
            System.out.println("-----------------------------------------------------------------------------------------");
        }

        return isExist;
    }

    //Default Access Modifier
    static long getEntryPerYear(String writter, String year)throws IOException{
        FileReader fileData = new FileReader("database.txt");
        BufferedReader buffRead = new BufferedReader(fileData);

        long entry = 0;
        String data = buffRead.readLine();
        Scanner dataScanner;
        String primaryKey;

        while (data != null){
            dataScanner = new Scanner(data);
            dataScanner.useDelimiter(",");
            primaryKey = dataScanner.next();
            dataScanner = new Scanner(primaryKey);
            dataScanner.useDelimiter("_");

            writter = writter.replaceAll("\\s+", "");

            if (writter.equalsIgnoreCase(dataScanner.next()) && year.equalsIgnoreCase(dataScanner.next())){
                entry = dataScanner.nextInt();
            }
            data = buffRead.readLine();
        }
        return  entry;
    }

    protected static String getYearFormat()throws IOException{
        //Parsing untuk memastikan data tahun cocok atau tidak
        boolean isValidYear = false;
        Scanner userInput = new Scanner(System.in);
        String year = userInput.nextLine();

        while (!isValidYear) {
            try {
                Year.parse(year);
                isValidYear = true;
            } catch (Exception err) {
                System.out.println("Tahun yang anda input tidak sesuai format");
                System.out.print("Please, Input Tahun Lagi: ");
                isValidYear = false;
                year = userInput.nextLine();
            }
        }
        return year;
    }

    public static boolean yesOrNo(String message){
        Scanner userOption = new Scanner(System.in);
        System.out.print("\n" + message + " (y/n)?: ");
        String pilihanUser = userOption.next();

        while (!pilihanUser.equalsIgnoreCase("y") && !pilihanUser.equalsIgnoreCase("n")){
            System.err.println("Pilihan Anda bukan y atau n!!");
            System.out.print("\n" + message + " (y/n)?: ");
            pilihanUser = userOption.next();
        }

        return pilihanUser.equalsIgnoreCase("y");
    }

    public static void clearScreen(){
        try {
            if (System.getProperty("os.name").contains("Windows")){ //untuk windows user
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            }else {} //untuk user linux, mac
            System.out.print("\033\143");
        }catch (Exception ex){
            System.err.println("Can't clear screen");
        }
    }
}
