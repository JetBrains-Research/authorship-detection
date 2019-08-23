/*
 * Realiza un programa que pida 10 números por teclado y que los almacene en un array. A continuación se debe
mostrar el contenido de ese array junto al índice (0 – 9). Seguidamente el programa debe colocar en las últimas
posiciones los números que terminen en 7 dejando el resto en las primeras posiciones.
 * 
 * 
 *@Costy 
 */
 
import java.util.Scanner;
public class SieteFinal {
    public static void main(String[] args) {
    
    Scanner num = new Scanner(System.in);
    
    int[] numero = new int[10];
    String verde = "\033[32m";
    String naranja = "\033[33m";
    int i;
    int contador=0;
    int contador7=9;
    int [] auxiliar=new int [10];
    
    System.out.println("Escribe 10 numeros:");
    System.out.println("============================================================================");
    
    //Se guardan los numeros en un array
    for (i = 0; i < numero.length; i++) {
      System.out.print((i + 1) + " numero:");
      numero[i] = Integer.parseInt(num.nextLine());
    }
    
    System.out.println(naranja + "=======================================================================");
    
    //Mostras el indice
    for (i = 0; i < numero.length; i++){
      System.out.print(verde + i + "\t");
    }
    System.out.println(naranja + "\n-----------------------------------------------------------------------\n");
    for (i = 0; i < numero.length; i++){
      System.out.print(numero[i] + "\t");
    }
    
      //leer el 7 del final
    for(i = 0; i < 10; i++){
        if((numero[i] % 10) == 7){
               auxiliar[contador7] = numero[i]; 
               contador7--;
        } else {
               auxiliar[contador] = numero[i];
               contador++; 
        }
    }
    System.out.println();

    System.out.println(naranja + "\n=========================================================================");

    //ordenar los numeros 
    for (i = 0; i < auxiliar.length; i++){
      System.out.print(verde + i + "\t");
    }
    System.out.println(naranja + "\n-----------------------------------------------------------------------\n");
    for (i = 0; i < auxiliar.length; i++){
      System.out.print(auxiliar[i] + "\t");
    }
  }
}