import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Encode extends Frame{
    // Method to fill zeros in the character array if its length is less than the specified size
    // Returns the updated character array
    public static char[] fillzeros(int size, char[] chArray) {
        if(chArray.length<size) {
            char[] ch = new char[size];
            int zeros = size-chArray.length;

            for(int k=0;k<size;k++) {
                if(k<zeros) {
                    ch[k] = '0';
                }else{
                    ch[k]=chArray[k-zeros];
                }
            }
            return ch;
        }else {
            return chArray;
        }

    }
    Encode(String fileLocation) throws IOException{
        String str;

        // Read the input file
        File file = new File(fileLocation); 			//read file
        Scanner f = new Scanner(file,"UTF-8");

        // Create FileWriter instances for three output files
        FileWriter out1 = new FileWriter("output1.txt");
        FileWriter out2 = new FileWriter("output2.txt");
        FileWriter out3 = new FileWriter("output3.txt");

        // Write initial codes to the output files
        out1.write("01");
        out2.write("10");
        out3.write("11");

        // Generate random codes for encryption and convert them to binary representation
        Random rand = new Random();
        int code1 = rand.nextInt(128-32)+32;
        String temp1 = Integer.toBinaryString(code1);
        char[] binC1 = temp1.toCharArray();

        int code2 = rand.nextInt(32);
        String temp2 = Integer.toBinaryString(code2);
        char[] binC2 = temp2.toCharArray();

        // Pad zeros to the binary codes
        binC1 = fillzeros(7,binC1);
        binC2 = fillzeros(5,binC2);

        // Combine the binary codes into a single code
        char[] binCode = new char[12];
        for(int i =0;i<12;i++) {
            if(i<5) {
                binCode[i]=binC2[i];
            }else {
                binCode[i]=binC1[i-5];
            }
        }
        
        // Write encoded data and parity bits to the output files
        int parityCount =0;
        for(int i =0;i<binCode.length;i++) {
            if(i%2 ==0) {
                out1.write(binCode[i]);
                parityCount++;
            } else {
                out2.write(binCode[i]);
                parityCount++;
            }
            if (parityCount == 2) {
                if((binCode[i-1]==binCode[i])) {
                    out3.write("0");
                }else {
                    out3.write("1");
                }
                parityCount = 0;
            }
        }

        // Read the input file line by line
        int line=0;
        while (f.hasNextLine()) {
            line++;
            str = f.nextLine();
            char[] charArray = str.toCharArray();
            String binArray;

            parityCount = 0;
            for(int i=0; i<charArray.length;i++) {
                if(charArray[i]<32 || charArray[i]>127) {
                    String t = "Text contain unrecognizable character, failed to encode\n Error code ("
                            +(int)(charArray[i])+") Error char: "+charArray[i]+" at line "+line+", character "+(i+1);
                    JOptionPane.showOptionDialog(null, t,"Failed to Encode", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE,null,null,0);
                    System.out.println("Error code ("+(int)(charArray[i])+") Error char: "+charArray[i]+" at line "+line+", character "+(i+1));
                    throw new IOException();
                }
                
                // Convert the character to a binary string
                binArray = Integer.toBinaryString(charArray[i]-code2+code1);

                // Pad zeros to the binary string
                char[] ch = fillzeros(8,binArray.toCharArray());

                // Write encoded data and parity bits to the output files
                for(int j=0;j<ch.length;j++) {
                    if(j%2 ==0) {
                        out1.write(ch[j]);
                        parityCount++;
                    } else {
                        out2.write(ch[j]);
                        parityCount++;
                    }
                    if (parityCount == 2) {
                        if((ch[j-1]==ch[j])) {
                            out3.write("0");
                        }else {
                            out3.write("1");
                        }
                        parityCount = 0;
                    }
                }
            }
            // These represent next line
            out1.write("0011");
            out2.write("0000");
            out3.write("0011");

        }
        if(line ==0){
            String t = "File is empty";
            JOptionPane.showOptionDialog(null, t,"Failed to Encode", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE,null,null,0);
            throw new IOException();
        }
        // These code represent end of file
        out1.write("0001");
        out2.write("0001");
        out3.write("0000");
        out1.close();
        out2.close();
        out3.close();
        // out.close();
        f.close();

        File f1 = new File("output1.txt");
        File f2 = new File("output2.txt");
        File f3 = new File("output3.txt");

        Desktop desktop= Desktop.getDesktop();
        try {
            desktop.open(new File("."));
        }catch(IllegalArgumentException iae){
            System.out.println("Failed to open file");
        }
        String t = "File Encoded Successfully";
        JOptionPane.showOptionDialog(null, t,"Done", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE,null,null,0);

    }
}

