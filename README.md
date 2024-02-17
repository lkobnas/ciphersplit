# CipherSplit - A text file encoder and decoder

## Background
This project is a program developed to practice the concepts of Caesar cipher and the parity bit in RAID 5. The program takes an input text file and generates three encrypted text files. By utilizing XOR operations, only two out of the three files are required to recover the original file.

## Challenge here!
To better understand the functionality of the program, I encourage you to download the given encripted files [here](/example/Encrpted/) and explore the encryption and decryption process. You can first try to decrpt it without looking at the source code. 
Then, you may download the executable [here](#download) to check the original message!
Besides, you can examine the code, experiment with different input files, and observe how the two classes, Encode and Decode, work together to encrypt and decrypt the data.

Please note that this program is designed for educational purposes and may not provide advanced encryption or security features.

We hope you enjoy exploring Caesar cipher and RAID 5 parity bit encryption with this program!

## Download
You will need JDK 17 to run this app
[JDK 17](https://www.oracle.com/hk/java/technologies/downloads/#java17)
[CipherSplit App]()

## Concept
### Caesar Cipher

### Parity bit and XOR

## Function
The program performs the following functions:

Reads an input text file.
Encrypts the file using Caesar cipher and the parity bit concept in RAID 5.
Generates three encrypted text files as output using the Encode class. The output files are named output1.txt, output2.txt, and output3.txt.
Allows the recovery of the original file by using any two of the three generated files with the Decode class. The output file for the decoder is named output.txt.
Design
The program consists of the following classes:

Main.java
This class contains the main method and is the entry point of the program. It initializes the Frame class.

Frame.java
This class represents a graphical user interface (GUI) frame.

Encode.java
This class extends the Frame class and is responsible for the encryption process. It includes methods to encode the data using Caesar cipher, create parity bits for RAID 5, and generate the three output files (output1.txt, output2.txt, and output3.txt).

Decode.java
This class extends the Frame class and contains the main logic for decoding the encrypted files. It includes methods to find the code from the text, merge and print the data chunks, check for the end of line, and recover data from the parity file. The output file for the decoder is named output.txt.







