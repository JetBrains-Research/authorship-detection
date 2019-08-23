/**
 * Realiza un programa que sume los 100 números siguientes a un número entero 
 * positivo introducido por teclado. Se debe comprobar que el dato introducido 
 * es correcto (que es un número positivo).
 * 
 * @author costy
 */
import java.util.Scanner;

public class Sumar100NumerosConFor {
  public static void main(String[] args) {   
    
    Scanner teclado = new Scanner(System.in);
    
    System.out.println("Escribe un numero positivo y te sumo los 100 siguentes");
    System.out.println("==================================================");
    
    int num = teclado.nextInt();
    int suma = 0;
    
    for (int i = num + 1; i < num + 100; i++){
      suma += i;
    }
    System.out.println("La suma de los 100 numeros es: " + suma);
  }
}