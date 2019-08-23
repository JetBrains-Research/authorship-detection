/**
 * Realiza un programa que vaya pidiendo números hasta que se introduzca un 
 * numero negativo y nos diga cuantos números se han introducido, la media de
 * los impares y el mayor de los pares. El número negativo sólo se utiliza para
 * indicar el final de la introducción de datos pero no se incluye en el cómputo.
 * 
 * @author costy
 */
import java.util.Scanner;

public class MediaImparesYmayorParDoWhile {
  public static void main(String[] args) {   
      
    Scanner teclado = new Scanner(System.in);
    
    System.out.println("Introduce numeros (el programa finaliza con un numero " + 
        " con un numero negativo )");
    System.out.println("------------------------------------------------------------");
    
    int numIntrod;
    int totalNumeros = 0;
    int sumaImpares = 0;
    int numImpar = 0;
    int mayorPar = 0;

    do {
        numIntrod = teclado.nextInt();

        if (numIntrod >= 0){
            totalNumeros++;

            if ((numIntrod % 2) != 0){
                numImpar++;
                sumaImpares += numIntrod;
            } else {
                if (numIntrod > mayorPar){
                    mayorPar = numIntrod;
                }
              }
        } else {
            System.err.println("ERROR: has introducido un numero negativo " +
                ", aqui finaliza el programa!");
        }

    } while (numIntrod >= 0);
    System.out.println("Se han introducido " + totalNumeros + " numeros");
    System.out.println("Media de los impares es " + (sumaImpares / numImpar));
    System.out.println("El mayor de los pares es " + mayorPar);
  }
}