/**
* Realiza un programa que pida un número por teclado y que luego muestre ese
* número al revés.
* 
* @author costy
 */
import java.util.Scanner;

public class MostrarNumeroRevesConDo {

public static void main(String[] args) {
Scanner teclado = new Scanner(System.in);
    
    long numIntrod;
    long reves = 0;

    System.out.println("Introduce un numro largo y te lo escribo a réves");
    numIntrod = teclado.nextLong();
    long numero = numIntrod;
    
    do {
        reves = (reves * 10) + (numero % 10);
        numero = numero / 10;
    } while (numero > 0);
    System.out.println("Numero: " + reves);
  }
}