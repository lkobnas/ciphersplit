import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Decode extends Frame{
    private static int code_size_1 =7;
    private static int code_size_2 =5;
    private static int prefix = 2;
    private static int ASCII_size = 8;
    private static char[] full_1, full_2;
    private static int counter_start = 0;
    private static int counter_end = 0;
    private static final char[] SPACE = {'0','0','0','0','1','0','1','0'};
    private static final char[] END_OF_TEXT = {'0','0','0','0','0','0','1','1'};

    // Method to find the code from the text
    public static int findCode(char [] first, char [] second) {
        int code2, code1;
        char[] temp1 = new char[code_size_1];
        char[] temp2 = new char [code_size_2];
        for(int i= 0 ; i<code_size_2;i++) {
            if(i%2==0) {
                temp2[i] = first[i/2+prefix];
            }else {
                temp2[i] = second[i/2+prefix];
            }
        }
        if(code_size_2%2 == 0) {
            for(int i =0;i<code_size_1;i++) {
                if(i%2==0) {
                    temp1[i] = first[i/2+code_size_2/2+prefix];
                }else {
                    temp1[i] = second[i/2+code_size_2/2+prefix];
                }
            }
        }else {
            for(int i =0;i<code_size_1;i++) {
                if(i%2!=0) {
                    temp1[i] = first[i/2+code_size_2/2+1+prefix];
                }else {
                    temp1[i] = second[i/2+code_size_2/2+prefix];
                }
            }
        }

        StringBuilder t1 = new StringBuilder();
        StringBuilder t2 = new StringBuilder();
        t1.append(temp1);
        t2.append(temp2);
        code1 = Integer.parseInt(t1.toString(),2);
        code2 = Integer.parseInt(t2.toString(),2);

        return code1-code2;
    }

    // Method to merge data chunks from 2 input files and decode them
    public static void mergePrint(char[] first, char[] second,int code,FileWriter output) throws IOException {
        char data[] = new char[first.length+second.length];
        for(int i =0; i<first.length+second.length;i++) {
            if(i%2==0) {
                data[i]=first[i/2];
            }else {
                data[i]=second[i/2];
            }
        }
        for(int i=2;i<(first.length+second.length)/8;i++) {
            char[] ascii = new char[8];
            for(int j =0; j< ASCII_size; j++) {
                ascii[j] = data[i*8+j];
            }
            if(Arrays.equals(ascii, END_OF_TEXT)){
                return;
            }
            if(Arrays.equals(ascii, SPACE)){
                output.write("\n");
                continue;
            }

            StringBuilder a = new StringBuilder();
            a.append(ascii);
            int ascii_code = Integer.parseInt(a.toString(),2);

            output.write((char)(ascii_code-code));
        }

    }

    // Check end of line
    public static boolean checkEnd(char[] d1, char[] d2 ,FileWriter output) throws IOException {
        for(int i=counter_start;i<full_1.length-4;i++){
            if(d1[i]=='0' && d1[i+1]=='0' && d1[i+2]=='0' && d1[i+3]=='1' && d2[i]=='0' && d2[i+1]=='0' && d2[i+2]=='0' && d2[i+3]=='1'){       //End of file
                return true;
            }
            if(d1[i]=='0' && d1[i+1]=='0' && d1[i+2]=='1' && d1[i+3]=='1' && d2[i]=='0' && d2[i+1]=='0' && d2[i+2]=='0' && d2[i+3]=='0'){       //End of file
                counter_end = i;
                output.write("\n");
                return false;
            }
        }
        return false; //should not run this
    }

    // Recover data from parity file
    public static char[] recover(char[] d, char[] p) {
        char[] re = new char[d.length];
        for(int i=0; i<d.length;i++) {
            if(d[i]==p[i]) {
                re[i]= '0';
            }else {
                re[i]= '1';
            }
        }
        return re;
    }
    Decode(String fileLocation1, String fileLocation2) throws IOException{
        char[] d1 = null, d2 = null, p = null;
        Scanner f1 = new Scanner(new File(fileLocation1));
        Scanner f2 = new Scanner(new File(fileLocation2));
        FileWriter output = new FileWriter("output.txt");
        char[] firstline1 = new char[8];
        char[] firstline2 = new char[8];
        try {
            full_1 = f1.nextLine().toCharArray();
            full_2 = f2.nextLine().toCharArray();
            for(int i=0;i<8;i++){
                firstline1[i] = full_1[i];
                firstline2[i] = full_2[i];
            }

        } catch (NoSuchElementException e) {
            JOptionPane.showOptionDialog(null, "Error file empty","Failed to Decode", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE,null,null,0);
            System.out.print("Error file empty");
        }
        int code = 0;

        // 2 out of 3 files are needed, so total 6 combinations
        if((firstline1[0]=='0'&&firstline1[1]=='1')&&(firstline2[0]=='1'&&firstline2[1]=='0')) {       //d1 d2
            d1 = new char[full_1.length];
            d2 = new char[full_2.length];
            d1 = full_1;
            d2 = full_2;
            code = findCode(firstline1,firstline2);
        }else if((firstline1[0]=='1'&&firstline1[1]=='0')&&(firstline2[0]=='0'&&firstline2[1]=='1')) { //d2 d1
            d1 = new char[full_2.length];
            d2 = new char[full_1.length];
            d1 = full_2;
            d2 = full_1;
            code = findCode(firstline2,firstline1);
        }else if((firstline1[0]=='0'&&firstline1[1]=='1')&&(firstline2[0]=='1'&&firstline2[1]=='1')) { //d1 p
            d1 = new char[full_1.length];
            p = new char[full_2.length];
            d1 = full_1;
            p = full_2;
            code = findCode(firstline1,recover(firstline1,firstline2));
        }else if((firstline1[0]=='1'&&firstline1[1]=='1')&&(firstline2[0]=='0'&&firstline2[1]=='1')) { //p d1
            p = new char[full_1.length];
            d1 = new char[full_2.length];
            p = full_1;
            d1 = full_2;
            code = findCode(firstline2,recover(firstline2,firstline1));
        }else if((firstline1[0]=='1'&&firstline1[1]=='0')&&(firstline2[0]=='1'&&firstline2[1]=='1')) { //d2 p
            d2 = new char[full_1.length];
            p = new char[full_2.length];
            d2 = full_1;
            p = full_2;
            code = findCode(recover(firstline1,firstline2),firstline1);
        }else if((firstline1[0]=='1'&&firstline1[1]=='1')&&(firstline2[0]=='1'&&firstline2[1]=='0')) { //p d2
            p = new char[full_1.length];
            d2 = new char[full_2.length];
            p = full_1;
            d2 = full_2;
            code = findCode(recover(firstline2,firstline1),firstline2);
        }else {
            JOptionPane.showOptionDialog(null, "Error. Selected file is not an encoded file","Failed to Decode", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE,null,null,0);
            System.out.println("Error. Selected file is not an encoded file");
            throw new IOException();
        }
            if (p == null) { // d1 d2

                mergePrint(d1, d2, code, output);
            } else if (d1 == null) { // d2 p

                mergePrint(recover(d2, p), d2, code, output);
            } else { // d1 p

                mergePrint(d1, recover(d1, p), code, output);
            }
        output.close();
        File f = new File("output.txt");
        String t = "File Decoded Successfully";
        JOptionPane.showOptionDialog(null, t,"Done", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE,null,null,0);
        Desktop desktop= Desktop.getDesktop();
        desktop.open(f);

    }
}
