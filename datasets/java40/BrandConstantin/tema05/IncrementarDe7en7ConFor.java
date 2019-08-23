/**
 * Escribe un programa que obtenga los números enteros comprendidos entre dos 
 * números introducidos por teclado y validados como distintos, el programa debe
 * empezar por el menor de los enteros introducidos e ir incrementando de 7 en 7.
 * 
 * @author costy
 */
import java.util.Scanner;

public class IncrementarDe7en7ConFor {
  public static void main(String[] args) {   
    
    Scanner teclado = new Scanner(System.in); 
          
    System.out.println("Escribe un numero:");
    int num1 = teclado.nextInt();
    
    System.out.println("Escribe otro numero:");
    int num2 = teclado.nextInt();
    System.out.println("==================================================");
    
    if (num1 == num2){
      System.err.println("Escribe 2 numeros distinctos por favor!"); 
    } else if(num1 > num2){
        System.err.println("El primer numero tiene que ser mas pequeños que el segundo"); 
    }else{
        for (int i = num1; i <= num2; i += 7){
          System.out.println(i + " ");
        }
      }
  }
}
