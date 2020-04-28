package com.tutorial;

//Import Java Library
import java.io.*;
import java.util.Scanner;

//Import CRUD library
import CRUD.Operator;
import CRUD.Utility;

public class MainMenu {
    public static void main(String[] args)throws IOException {

        Scanner userOption = new Scanner(System.in);
        String pilihanUser;
        boolean isContinue = true;

        while (isContinue) {
            Utility.clearScreen();
            System.out.println("Database Perpustakaan\n");
            System.out.println("1.\tLihat Seluruh Buku");
            System.out.println("2.\tCari Data Buku");
            System.out.println("3.\tTambah Data Buku");
            System.out.println("4.\tUbah Data Buku");
            System.out.println("5.\tHapus Data Buku");

            System.out.print("\n\nPilihan Anda: ");
            pilihanUser = userOption.next();

            switch (pilihanUser) {
                case "1":
                    System.out.println("\n================");
                    System.out.println("LIST SELURUH BUKU");
                    System.out.println("=================");
                    Operator.showData();
                    break;
                case "2":
                    System.out.println("\n=============");
                    System.out.println("CARI DATA BUKU");
                    System.out.println("==============");
                    Operator.searchData();
//                    Operator.showData();
                    break;
                case "3":
                    System.out.println("\n===============");
                    System.out.println("TAMBAH DATA BUKU");
                    System.out.println("================");
                    Operator.addBook();
                    Operator.showData();
                    break;
                case "4":
                    System.out.println("\n=============");
                    System.out.println("UBAH DATA BUKU");
                    System.out.println("==============");
                    Operator.updateData();
                    break;
                case "5":
                    System.out.println("\n==============");
                    System.out.println("HAPUS DATA BUKU");
                    System.out.println("===============");
                    Operator.deleteData();
                    break;
                default:
                    System.err.println("Oops!, your Input is not found on the list.\nPlease Choose number 1 - 5");
            }

            isContinue = Utility.yesOrNo("Apakah anda ingin kembali ke main menu");
        }

    }
}
