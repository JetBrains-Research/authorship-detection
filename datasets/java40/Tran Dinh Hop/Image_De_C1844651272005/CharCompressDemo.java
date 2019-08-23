// This program is in Image Compression1.pdf. See it
package Algorithms.GEN;

import java.math.*;

public class CharCompressDemo {
static int posA='A';
public static void main(String[] args) {
String incoming=args[0].toUpperCase();
if (incoming.length()%3==1) incoming+="[[";
else if (incoming.length()%3==2) incoming+="[";
byte[] original=incoming.getBytes();
BigInteger origNum=new BigInteger(1,original);
System.out.println("Original string in binary:\n"+origNum.toString(2));
byte[] compressed=new byte[2*incoming.length()/3];
compress(original,compressed);
BigInteger compNum=new BigInteger(1,compressed);
System.out.println("Compressed string in binary:\n"+compNum.toString(2));
}
public static void compress(byte[] o,byte[] c) {
for (int i=0,j=0;i<o.length-2;i+=3,j+=2) {
int c1=o[i]-posA;
int c2=o[i+1]-posA;
int c3=o[i+2]-posA;
int res1=0,res2=0;
//Do the first compressed byte
//Put 1st value shifted 2 bits up
res1=res1|(c1<<2);
//Put first 2 bits of 2nd value in lo position
res1=res1|(c2>>>3);
//Do the second compressed byte
//Put last 3 bits of 2nd value in high position-mask out first 5 bits
res2=res2|((c2&7)<<5);
//Put 3rd value in lo position
res2=res2|c3;
c[j]=(byte)res1;
c[j+1]=(byte)res2;
}
}
}

