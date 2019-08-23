/**
* Realiza un programa que pinte una pirámide por pantalla. La altura se debe
* pedir por teclado. El carácter con el que se pinta la pirámide también se 
* debe pedir por teclado.
* 
* @author costy
 */
import java.util.Scanner;

public class PiramideConFor {

public static void main(String[] args) {
    Scanner teclado = new Scanner(System.in);
    
    System.out.println("Introduce la altura de la pirámide:");
    System.out.println("------------------------------------------------------");
    
    int numIntrod = teclado.nextInt();
    int numero;
    
    System.out.println("------------------------------------------------------");

    for (int a = 1; a <= numIntrod; a++) {
        for (int espacio = 0; espacio < (numIntrod - a); espacio++){
            System.out.print(" ");
        }
        for (numero = 1; numero <= a; numero++) {
            System.out.print(" " + "*");
        }
        System.out.println();
    }
    System.out.println("------------------------------------------------------");
  }
}