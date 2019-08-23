package Algorithms.Huffman;

/**
 	*************************************************************************************
	* File: DLNode.java                Date: 21/06/2004		      Version: 1.01 		*
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


public class DLNode{
	private DLNode next,prev;
	private HuffmanNode elem;
	
	public DLNode(){
		
		next=null;
		prev=null;
		elem=null;
	}
	
	public DLNode(DLNode next,DLNode prev,HuffmanNode elem){
		
		this.next=next;
		this.prev=prev;
		this.elem=elem;
	}
	
	public DLNode getNext(){
		return next;
	}
	
	public DLNode getPrev(){
		return prev;
	}
	
	public void setNext(DLNode n){
		next=n;
	}
	
	public void setPrev(DLNode n){
		prev=n;
	}
	
	public void setElement(HuffmanNode o){
		elem=o;
	}
	
	public HuffmanNode getElement(){
		return elem;
	}
	
}
