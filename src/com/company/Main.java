package com.company;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.BitSet;

public class Main {

    public static void main(String[] args) {
        //регистр
        BitSet reg = new BitSet();

        //М-последовательность
        int M = 26;

        //определяем первый элемент регистра
        reg.set(0, M);

        try(FileInputStream fileIn = new FileInputStream("text.txt");
            FileOutputStream fileOut = new FileOutputStream("textDecr.txt")){
            byte[] buff = new byte[fileIn.available()];
            fileIn.read(buff, 0, fileIn.available());

            for (int i = 0; i < buff.length; i++)
                buff[i] = (byte) (buff[i] ^ generateKeySet(reg, M));

            fileOut.write(buff);
            System.out.println("Encrypt succeed!");

        }
        catch (IOException e){
            e.printStackTrace();
        }

        reg.set(0, M);
        try (FileOutputStream fOut = new FileOutputStream("textDecr.txt");
             FileInputStream fIn = new FileInputStream("textEnc.txt")) {

            byte[] buffEnc = new byte[fIn.available()];
            fIn.read(buffEnc, 0, fIn.available());

            for (int i = 0; i < buffEnc.length; i++) {
                buffEnc[i] = (byte) (buffEnc[i] ^ generateKeySet(reg, M));
            }

            fOut.write(buffEnc);
            System.out.println("Decrypted succeed!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte generateKeySet(BitSet input, int M) {
        BitSet toReturn = new BitSet(0);

        for (int i = 0; i <= 7; i++){
            toReturn.set(i, input.get(M - 1));
            BitSet temp = (BitSet) input.clone();

            for (int j = 1; j < M; j++)
                input.set(j, temp.get(j - 1));

            input.set(0, (input.get(25) ^ input.get(7) ^ input.get(6) ^ input.get(0)));
        }

        return toReturn.toByteArray()[0];
    }
}
