package CRUD;

import jdk.jshell.execution.Util;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Operator {

      public static void updateData()throws IOException {
        //Ambil database orginal
        File database = new File("database.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader buffInput = new BufferedReader(fileInput);

        //buat temporary database
        File tempDB = new File("tempDB.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter buffOutput = new BufferedWriter(fileOutput);

        //show data
        System.out.println("List buku");
        showData();

        //ambil user input yg akan di update
        Scanner userInput = new Scanner(System.in);
        System.out.println("Masukkan Nomot Buku yang akan di update: ");
        int numBook = userInput.nextInt();

        //show data yang akan diupdate
        String data = buffInput.readLine();
        int index = 0;

        while (data != null){
            index++;

            StringTokenizer st = new StringTokenizer(data, ",");

            //Show Index == numBook
            if (numBook == index){
                System.out.println("\nData yang akan anda update adalah: ");
                System.out.println("--------------------------------------");
                System.out.println("No Referensi     : " + st.nextToken());
                System.out.println("Tahun            : " + st.nextToken());
                System.out.println("Penulis          : " + st.nextToken());
                System.out.println("Penerbit         : " + st.nextToken());
                System.out.println("Judul            : " + st.nextToken());
                System.out.println("--------------------------------------");

                //Update data

                //Ambil input dari user

                String[] fieldData = {"Tahun", "Penulis", "Penerbit", "Judul"};
                String[] tempData = new String[4];

                st = new StringTokenizer(data, ",");
                String originalData = st.nextToken();

                for (int i = 0; i < fieldData.length; i++) {
                    boolean isUpdate = Utility.yesOrNo("Apakah anda ingin merubah " + fieldData[i]);
                    originalData = st.nextToken();
                    if (isUpdate){
                        //user input

                        if (fieldData[i].equalsIgnoreCase("tahun")){
                            System.out.print("Input Tahun dengan format(YYYY): ");
                            tempData[i] = Utility.getYearFormat();
                        }else {
                            userInput = new Scanner(System.in);
                            System.out.print("\nMasukkan " + fieldData[i] + " baru: ");
                            tempData[i] = userInput.nextLine();
                        }

                    }else {
                        tempData[i] = originalData;
                    }
                }

                //Tampilkan data baru
                st = new StringTokenizer(data, ",");
                st.nextToken();
                System.out.println("\nData yang anda perbaharui adalah: ");
                System.out.println("--------------------------------------");
                System.out.println("Tahun            : " + st.nextToken() + "  --->  " + tempData[0]);
                System.out.println("Penulis          : " + st.nextToken() + "  --->  " + tempData[1]);
                System.out.println("Penerbit         : " + st.nextToken() + "  --->  " + tempData[2]);
                System.out.println("Judul            : " + st.nextToken() + "  --->  " + tempData[3]);
                System.out.println("--------------------------------------");

                boolean isUpudate = Utility.yesOrNo("Apakah Anda yakin ingin mengupdate data tersebut ");

                if (isUpudate){

                    //Cek data baru di database
                    boolean isExist = Utility.checkBookOnDatabase(tempData, false);

                    if (isExist){
                        System.err.println("Data sudah ada di database, Update failed. \nSilahkam delete data yang bersangkutan");
                    }else {

                        //Format data baru ke database
                        String year = tempData[0];
                        String writter = tempData[1];
                        String publisher = tempData[2];
                        String titleBook = tempData[3];


                        //Buat primaary key
                        long entry_Number = Utility.getEntryPerYear(writter, year) + 1;
                        String writterWithoutSpace = writter.replaceAll("\\s+", ""); //cara untuk menghilangkan spasi pada nama penulis menggunakan regex(regular expression)
                        String primaryKey = writterWithoutSpace+"_"+year+"_"+entry_Number;

                        //Tulis data ke dataabse
                        buffOutput.write(primaryKey + ","+ year +","+ writter + "," + publisher + "," + titleBook);
                    }

                }else {
                    //Copy data
                    buffOutput.write(data);
                }

            }else {
                //Copy data
                buffOutput.write(data);
            }

            buffOutput.newLine();
            data = buffInput.readLine();
        }

        // Menulis data ke file
        buffOutput.flush();

        //Delete origial database
        database.delete();

        //Renama tempDB to Database
        tempDB.renameTo(database);
    }

    public static void deleteData()throws IOException{
        //Ambil Database original | Hanya untuk membaca data saja
        File database = new File("database.txt"); //just read
        FileReader fileInput = new FileReader(database);
        BufferedReader buffInput = new BufferedReader(fileInput);

        //Create Temporary Database
        /*
        Kita tidak dapat menggunakan file reader/writter atau buffer reader/writter.
        Karena kita tidak akan bisa me-rename database nya
        Jadi, kita gunakan file input
         */
        File temporary_db = new File("tempDB.txt"); //membuat file tempDB
        FileWriter fileOutput = new FileWriter(temporary_db);
        BufferedWriter buffOutput = new BufferedWriter(fileOutput);

        //Show Data
        System.out.println("List Buku");
        showData();

        //Ambil user input untuk men delete data
        Scanner userInput = new Scanner(System.in);
        System.out.print("Masukkan Nomor buku yang akan dihapus: ");
        int deleteNum = userInput.nextInt();

        //Looping untuk membaca tiap data per=baris dan skip data yg akan di hapus
        boolean isFound = false; //untuk handle jika tidak ada user input yg ditemukan datanya
        int dataOnDb = 0;
        String data = buffInput.readLine();

        while (data != null){
            dataOnDb++;
            boolean isDelete = false;

            StringTokenizer st = new StringTokenizer(data,",");
            //Show data that will be deleted
            if (deleteNum == dataOnDb){
                System.out.println("\nData yang ingin anda hapus adalah");
                System.out.println("------------------------------------");
                System.out.println("No Referensi     : "+ st.nextToken());
                System.out.println("Tahun            : "+ st.nextToken());
                System.out.println("Penulis          : "+ st.nextToken());
                System.out.println("Penerbit         : "+ st.nextToken());
                System.out.println("Judul            : "+ st.nextToken());


                isDelete = Utility.yesOrNo("Are you sure to delete this data???");
                isFound = true;
            }

            if (isDelete == true){
                //Skip dan delete data
                System.out.println("Data Berhasil Di hapus!!");
            }else {
                //pindahkan data yg tidak di delete dari database.txt to tempDB.txt
                buffOutput.write(data);
                buffOutput.newLine();
            }

            data = buffInput.readLine();
        }

        if (!isFound){
            System.err.println("Buku not found");
        }

        //Simpan ke tempDB / menulis data ke file
        buffOutput.flush();
        //Delete database.txt
        database.delete();

        //renama tempDB -> database/txt
        temporary_db.renameTo(database);

    }

    public static void showData()throws IOException {
        FileReader fileData;
        BufferedReader buffRead;

        try {
            fileData = new FileReader("database.txt");
            buffRead = new BufferedReader(fileData);
        } catch (Exception e){
            System.out.println("Database tidak ditemukan");
            System.out.println("Silahkan tambah data terlebih dahulu");
            addBook();
            return;
        }

        System.out.println("\n| No |\tTahun |\tPenulis              |\tPenerbit             |\tJudul");
        System.out.println("-----------------------------------------------------------------------------------------");
        String data = buffRead.readLine();
        //String Tokenizer bisa membaca data secara perbaris dan perkata dengan batasannya adalah delimiter
        //Scanner membaca data secara keseluruhan yang berada di file
        int nomorData = 1;

        while (data != null) {
            StringTokenizer stringToken = new StringTokenizer(data, ",");

            stringToken.nextToken();
            System.out.printf("|%2d  ", nomorData);
            System.out.printf("|\t%5s ", stringToken.nextToken());
            System.out.printf("|\t%-20s ", stringToken.nextToken());
            System.out.printf("|\t%-20s ", stringToken.nextToken());
            System.out.printf("|\t%s", stringToken.nextToken());
            System.out.print("\n");

            nomorData += 1;
            data = buffRead.readLine();
        }
        System.out.println("-----------------------------------------------------------------------------------------");
    }

    public static void searchData()throws IOException{

        //Membaca data yang dicari ada atau tidak?
        try {
            File file = new File("database2.txt");
        }catch (Exception ex){
            System.err.println("Database tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahulu");
            addBook();
            return;
        }

        //Ambil keyword dari user
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("Masukkan Keyword untuk mencari buku: ");
        String searchString = terminalInput.nextLine();

        String[] keyword = searchString.split("\\s+");

        //Cek keyword dari user apakah sesuai dengan data yang ada di database
        Utility.checkBookOnDatabase(keyword, true);
    }

    public static void addBook()throws IOException{
        FileWriter addData = new FileWriter("database.txt", true); //append ini adalah menentukan method
        // untuk menambah data tersebut, jika 'false' maka akan meng-overwrite data yang sudah ada
        BufferedWriter buffAddData = new BufferedWriter(addData);

        //Ambil data dari Input User
        Scanner userInput = new Scanner(System.in);
        String writter, titleBook, publisher, year;

        System.out.print("Input Nama Penulis: ");
        writter = userInput.nextLine();
        System.out.print("Input Judul Buku: ");
        titleBook = userInput.nextLine();
        System.out.print("Input Penerbit: ");
        publisher = userInput.nextLine();
        System.out.print("Input Tahun dengan format(YYYY): ");
        year = Utility.getYearFormat(); //Memamnggil method dimana butuh pengecekan data format tahun

        //Check Avalability Book In the Database

        String[] keywords = {year+","+writter+","+publisher+","+titleBook};
        System.out.println(Arrays.toString(keywords));

        boolean isExist = Utility.checkBookOnDatabase(keywords, false);
        System.out.println(isExist);

        //Menulis Buku di Database
        if (!isExist){
            System.out.println(Utility.getEntryPerYear(writter, year));
            long entry_Number = Utility.getEntryPerYear(writter, year) + 1;
            //Tempalate
//            fiersabesari_2012_1,2012,fiersabesari,media kita,jejak langkah
            String writterWithoutSpace = writter.replaceAll("\\s+", ""); //cara untuk menghilangkan spasi pada nama penulis menggunakan regex(regular expression)
            String primaryKey = writterWithoutSpace+"_"+year+"_"+entry_Number;
            System.out.println("\nData yang anda masukkan adalah: ");
            System.out.println("-----------------------------------");
            System.out.println("Primary Key  : "+ primaryKey);
            System.out.println("Tahun terbit : "+ year);
            System.out.println("Penulis      : "+ writter);
            System.out.println("Judul        : "+ titleBook);
            System.out.println("Penerbit     : "+ publisher);

            boolean isAdd = Utility.yesOrNo("Apakah Anda ingin menambahkan data tersebut?");

            if (isAdd){
                buffAddData.write(primaryKey + ","+ year +","+ writter + "," + publisher + "," + titleBook);
                buffAddData.newLine();
                buffAddData.flush();
            }

        }else {
            System.out.println("Data buku yang anda masukkan sudah ada di Database sebagai berikut");
            Utility.checkBookOnDatabase(keywords, true);
        }

        //Menutup Proses Input Jika sudah benar
        buffAddData.close();
    }
}
