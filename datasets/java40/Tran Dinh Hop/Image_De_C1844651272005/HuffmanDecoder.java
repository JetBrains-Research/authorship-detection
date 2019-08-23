package Algorithms.Huffman;

/**
 	*************************************************************************************
	* File: HuffmanDecoder.java                Date: 21/06/2004		      Version: 1.01 		*
	*-----------------------------------------------------------------------------------*
	* This program is released under the GNU General Public License 2.00. 	              
	* Details of GNU GPL at http://www.opensource.org/licenses/gpl-license.php  		    
	* You must agree to this license before using, copying or modifying this code.								    
	*
 	* Redistribution and use in source and binary forms, with or without
 	* modification, are permitted provided that the following conditions
 	* are met:
 	*
 	* 1. Redistributions of source code must retain the above copyright
 	*    notice, this list of conditions and the following disclaimer.
 	* 2. The source can be used and modified by individual/organizations, 
	*	  ONLY IF the source will not be used 
 	*    for commercial purposes or incorporated into commercial applications.
	*
 	* --------------------------------- WARRANTY --------------------------------- 
	* THIS SOFTWARE IS PROVIDED BY THE AUTHOR "AS IS" AND ANY EXPRESS OR IMPLIED 
	* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
	* MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  
	* IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
	* SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
	* PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
	* OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
	* WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR 
	* OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
	* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
	*
 	* --------------------------------- CONTACT --------------------------------- 
	* This project was written in a burning hurry, so it is not a model of efficient
	* nor even good code.
	* Please send comments, bug reports, improvements to: hoptrandinh@yahoo.com  
	*
	* Author:  http://www.Planet-Source-Code.com/vb/scripts/ShowCode.asp
	* Kh.Mahmudul Alam (Dipu) student of computer science and technology,  
	* Bangladesh University of Engineering & Technology 
	*
	* @Edited by Tran Dinh Hop, 6 May 2004
	* @Please visit http://www.freewebs.com/DigitZone for updated version
*/


import java.io.*;
import javax.swing.*;

public class HuffmanDecoder {

	private int totalBytes=0,mycount=0;
	private int freq[],arr=0;
	private String summary="";
	private File inputFile;
	private Table table;

	private FileInputStream in1;
	private ObjectInputStream inF;
	private BufferedInputStream in;
		
	private File outputFile ;
	private FileOutputStream outf;
	
	public HuffmanDecoder(File inFile, File outFile){
		inputFile = inFile;
		outputFile = outFile; 
		decode();
	}

	public void decode(){
		freq=new int[256];
		for(int i=0;i<256;i++){
			freq[i]=0;
		}
		
	
  //  File inputFile = new File(JOptionPane.showInputDialog("Enter the input File name"));
	
	try
	{
   	in1 = new FileInputStream(inputFile);
		inF=new ObjectInputStream(in1);
		in=new 	BufferedInputStream(in1);

//			int arr=0;
		table=(Table)(inF.readObject());

		
		//outputFile = new File(table.fileName());
		outf=new FileOutputStream(outputFile);
		
		summary+="File name : "+ table.fileName();
		summary+="\n";	
	
	} catch(Exception exc){
		
		System.out.println("Error creating file");
		JOptionPane.showMessageDialog(null,"Error"+"\nNot a valid < hff > format file.","Summary",JOptionPane.INFORMATION_MESSAGE);								
		System.exit(0);
	}


	HuffmanNode tree=new HuffmanNode(),one,two;
	PriorityQueue q=new PriorityQueue();
	
	try{
	
	//*****creating priority queue ******//
		for(int j=0;j<256;j++){
			int r =table.pop();
		//	System.out.println("Size of table "+r+" "+j);			
			if (r>0){
				HuffmanNode t=new HuffmanNode("dipu",r,j,null,null,null);
				q.insertM(t);				
			}
		}
	
	//****** create tree ******//
	
		while (q.sizeQ()>1){
			one=q.removeFirst();
			two=q.removeFirst();
			int f1=one.getFreq();
			int f2=two.getFreq();
			if (f1>f2){
				HuffmanNode t=new HuffmanNode(null,(f1+f2),0,two,one,null);			
				one.up=t;
				two.up=t;
				q.insertM(t);	
			}
			else{
				HuffmanNode t=new HuffmanNode(null,(f1+f2),0,one,two,null);			
				one.up=t;
				two.up=t;
				q.insertM(t);			
			}
		}
		
		tree =q.removeFirst();
		
	}catch(Exception exc){
		System.out.println("Priority queue exception");
	}
	
		String s="";
		try{
			mycount=in.available();
			while (totalBytes<mycount){
				arr=in.read();
				s+=toBinary(arr);
				while (s.length()>32){
					for(int a=0;a<32;a++){
						int wr=getCode(tree,s.substring(0,a+1));
						if(wr==-1)continue;
						else{
							outf.write(wr);
							s=s.substring(a+1);
							break;
						}
												
					}
				}
				totalBytes++;	
			}
			s=s.substring(0,(s.length()-8));
			s=s.substring(0,(s.length()-8+arr));
			int counter;		
			while (s.length()>0){
				if(s.length()>16)counter=16;
				else counter=s.length();
				for(int a=0;a<counter;a++){
					int wr=getCode(tree,s.substring(0,a+1));
					if(wr==-1)continue;
					else{
						outf.write(wr);
						s=s.substring(a+1);
						break;
					}
				}
			}

			outf.close();
		
		}catch(IOException eofexc){
			System.out.println("IO error");
		}

		summary+="Compressed size : "+ mycount+" bytes.";
		summary+="\n";	
	
		summary+="Size after decompressed : "+table.originalSize()+" bytes.";
		summary+="\n";	
    }

	private int getCode(HuffmanNode node,String decode){
		while (true){
			if (decode.charAt(0)=='0'){
				node=node.lchild;
			}
			else{
				node=node.rchild;
			}
			if (node.lchild==null&&node.rchild==null){
				return node.getValue();
			}
			if(decode.length()==1)break;
			decode=decode.substring(1);
		}
	
		return -1;
	}


 	public  String toBinary(int b){
		int arr[]=new int[8];
	    	String s="";
		for(int i=0;i<8;i++){
			arr[i]=b%2;
	    		b=b/2;			
		}
	    	for(int i=7;i>=0;i--){
			s+=arr[i];
	    	}
		return s;
	}
	
	public int toInt(String b){
		int output=0,wg=128;
	    	for(int i=0;i<8;i++){
	   			output+=wg*Integer.parseInt(""+b.charAt(i));
				wg/=2;
	     }	
		return output;
     }
	
	public int getCurrent(){
		return totalBytes;
	}
	
	public int lengthOftask(){
		return mycount;
	}
	
	public String getSummary(){
		return summary;
	}
}


