/**
 * Escribe un programa que permita ir introduciendo una serie indeterminada de 
 * números mientras su suma no supere el valor 10000. Cuando esto último ocurra,
 * se debe mostrar el total acumulado, el contador de los números introducidos 
 * y la media.
 * 
 * @author costy
 */
import java.util.Scanner;

public class SumarHasta10MilConDoWhile {
  public static void main(String[] args) {
    
    Scanner teclado = new Scanner(System.in);
    
    int suma = 0;
    int numIntrod;
    int totalNumeros = 0;
    
    do {
        System.out.println("Introduce numeros:");
        numIntrod = teclado.nextInt();  
        
        suma += numIntrod;
        totalNumeros++;

    } while (suma <= 10000);
    
    System.out.println("La media de los numeros es " + (suma / totalNumeros));
    System.out.println("El total acumulado siendo " + suma);
    System.out.println("Con un total de numeros introducidos de " + totalNumeros);  
  }
}