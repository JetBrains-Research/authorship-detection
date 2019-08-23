/**
* Realiza un programa que pinte una pirámide por pantalla. La altura se debe
* pedir por teclado. El carácter con el que se pinta la pirámide también se 
* debe pedir por teclado.
* 
* @author costy
 */
import java.util.Scanner;

public class PiramideConWhile {

public static void main(String[] args) {
    Scanner teclado = new Scanner(System.in);
    
    System.out.println("Introduce la altura de la pirámide:");
    System.out.println("------------------------------------------------------");
    
    int numIntrod = teclado.nextInt();
    int numero;
    
    System.out.println("------------------------------------------------------");
    int a = 1;
    while ( a <= numIntrod) {
        
        int espacio = 0;
        while ( espacio < (numIntrod - a)){
            espacio++;
            System.out.print(" ");
        }
        
        numero = 1;
        while ( numero <= a) {
            numero++;
            System.out.print(" " + "*");
        }
        
        a++;
        System.out.println();
    }
    System.out.println("------------------------------------------------------");
  }
}