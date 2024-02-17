# CipherSplit - A text file encoder and decoder

## Description
This program is developed to practice the concepts of Caesar cipher and the parity bit in RAID 5. The program takes an input text file and generates three encrypted text files. Any two of the three files are required to recover the original file.

## Challenge here!
To better understand the functionality of the program, I encourage you to download the given encripted files [*here*](/example/Encrpted/) and explore the encryption and decryption process. You can first try to decrpt it without looking at the source code. 
Then, you may download the executable [*here*](#download) to check the original message!
Besides, you can examine the code, experiment with different input files, and observe how the two classes, Encode and Decode, work together to encrypt and decrypt the data.

Please note that this program is designed for educational purposes and may not provide advanced encryption or security features.

## Download
You will need JDK 17 to run this app <br>
Download [JDK 17](https://www.oracle.com/hk/java/technologies/downloads/#java17)<br>
Download [CipherSplit App](/File%20encoder%20and%20decoder%20update.jar)

I hope you enjoy exploring encryption with this program!

## Concept
### Caesar Cipher
The Caesar cipher is a substitution cipher that shifts the letters of the alphabet by a fixed number of positions. It is named after Julius Caesar, who is said to have used this cipher to communicate secretly. 

The Caesar cipher operates on the principle of modular arithmetic. Each letter is assigned a numerical value (e.g., A=0, B=1, C=2, etc.), and the shift is applied by adding the shift value to the original numerical value. If the result exceeds the range of the alphabet, it wraps around to the beginning. For example, with a shift of 3, "Z" would be encrypted as "C."

### Parity bit and XOR
A parity bit is a simple form of error detection used in computer systems, data transmission, and storage. It is a single bit appended to a group of bits to ensure the total number of 1s in the group is either odd or even, depending on the parity scheme.

In the context of RAID 5, the parity bit is used for fault tolerance and data recovery in a disk array. The parity bit is calculated by performing an exclusive OR (XOR) operation on the corresponding bits from multiple disks. The resulting parity bit is stored on a separate disk, providing redundancy and allowing recovery of data in case of disk failure.

## Function
The program performs the following functions:

**Encoder:**
Reads an input text file.
Encrypts the file using Caesar cipher and the parity bit concept in RAID 5.
Generates three encrypted text files as output using the [Encode class](/src/Encode.java). The output files are named ```output1.txt```, ```output2.txt```, and ```output3.txt```.

**Decoder:**
Recover original file by using any two of the three generated files with the [Decode class](/src/Decode.java). The output file for the decoder is named ```output.txt```.


## How it works

The encoder first generates two random integers that determine the shift index using the formula: ```encrypted_character = original - code2 + code1``` 
Each encrypted character is represented by 8 bits, and the program writes the bits alternately to ```output1.txt``` and ```output2.txt```. For instance, if the encrypted data is 01101100, the bits at odd indices are written to output1 (0110), and the bits at even-numbered indices are written to output2 (1010). ```output3.txt``` stores the parity bit of output1 and output2 using the XOR operation. Therefore, in this case, output3 will be 1100.

The first two bits indicate the file type: output1(01), output2(10), output3(11). They are followed by code2 (5 bits) and code1 (7 bits), in total 12 bits. These 12 bits are written to output1, output2, and output3 alternately using the method described above.








