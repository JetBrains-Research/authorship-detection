/**
* Escribe un programa que calcule el factorial de un número entero leído por
* teclado.
* 
* @author costy
 */
import java.util.Scanner;

public class CalcularFactorialConFor {

public static void main(String[] args) {
Scanner teclado = new Scanner(System.in);
    
    int numIntrod;
    int factorial = 1;

    System.out.println("Introduce un numro para calcular el factorial");
    numIntrod = teclado.nextInt();
    System.out.println("-------------------------------------------------");
    
    for (int i = 1; i <= numIntrod; i ++){
        factorial *= i;
        System.out.print(" += " + factorial);
    }

  }
}