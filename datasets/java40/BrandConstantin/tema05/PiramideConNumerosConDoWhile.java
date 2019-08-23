/**
Escribe un programa que lea un número n e imprima una pirámide de números con n
* filas como en la siguiente figura:
*    1
*   121
*  12321
* 1234321
* 
* @author costy
 */
import java.util.Scanner;

public class PiramideConNumerosConDoWhile {

public static void main(String[] args) {
    Scanner teclado = new Scanner(System.in);

    System.out.println("Introduce la altura de la pirámide:");
    int numIntrod = teclado.nextInt();
    System.out.println("=================================================");
    

    int a = 1;
    
    do {
        int espacio = 0;
        while ( espacio < (numIntrod - a)){
            espacio++; 
            System.out.print(" ");
        }
        
        for (int numero1 = 1; numero1 <= a; numero1++) {
            System.out.print(numero1);
        }
        
        for (int numero = a - 1; numero > 0; numero--) {
            System.out.print(numero);
        }
        
        System.out.println();
        a++; 
        
    } while ( a <= numIntrod);
  }
}