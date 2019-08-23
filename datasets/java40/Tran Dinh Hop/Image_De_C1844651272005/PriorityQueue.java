package Algorithms.Huffman;

/**
 	*************************************************************************************
	* File: PriorityQueue.java                Date: 21/06/2004		      Version: 1.01 		*
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


public class PriorityQueue{

private DLNode head,tail;
private int size=0;
private int capacity;
private HuffmanNode obj[];
	
	public PriorityQueue(int cap){
	
		head=new DLNode();
		tail=new DLNode();
		head.setNext(tail);
		tail.setPrev(head);
		capacity=cap;
		obj=new HuffmanNode[capacity];
	}
	
	
	public PriorityQueue(){
		
		head=new DLNode();
		tail=new DLNode();
		head.setNext(tail);
		tail.setPrev(head);
		capacity=1000;
		obj=new HuffmanNode[capacity];
	}
		
	
	public void insertM(HuffmanNode o)throws Exception{
		
		if (size==capacity)
			throw new Exception("Queue is full");
		
			if (head.getNext()==tail){
				DLNode d=new DLNode(tail,head,o);
				head.setNext(d);
				tail.setPrev(d);
			}
			else{
				DLNode n=head.getNext();
				HuffmanNode CurrenMax=null;
				int key=o.getFreq();
				while (true){
					if (n.getElement().getFreq()>key){
						DLNode second=n.getPrev();						
						DLNode huf=new DLNode(n,second,o);
						second.setNext(huf);
						n.setPrev(huf);
						break;
					}
					if (n.getNext()==tail){
						DLNode huf=new DLNode(tail,n,o);
						n.setNext(huf);
						tail.setPrev(huf);
						break;
					}
					n=n.getNext();
				}
			}
		size++;
	}

	public HuffmanNode removeFirst() throws Exception{
		
		if(isEmpty())
		  throw new Exception("Queue is empty");
	
		HuffmanNode o=head.getNext().getElement();
		DLNode sec=head.getNext().getNext();
		head.setNext(sec);
		sec.setPrev(head);
		size--;
		return o;
	}
	
	//public HuffmanNode removeLast() throws Exception
	//{
	//	if(isEmpty())	
	//	  throw new Exception("Queue is empty");
	//	DLNode d=tail.getPrev();
	//	HuffmanNode o=tail.getPrev().getElement();
	//	tail.setPrev(d.getPrev());
	//	d.getPrev().setNext(tail);
	//	size--;
	//	return o;
	//}

	public boolean isEmpty(){
		if(size==0)return true;
		return false;
	}
	
	public int sizeQ(){
		return size;
	}
	
	//public HuffmanNode first()throws Exception
	//{
	//	if(isEmpty())
	//	  throw new Exception("Stack is empty");
	//	return head.getNext().getElement();
	//}
	//
	
	//public HuffmanNode Last()throws Exception
	//{
	//	if(isEmpty())
	//	  throw new Exception("Stack is empty");
	//	return tail.getPrev().getElement();
	//}
}
