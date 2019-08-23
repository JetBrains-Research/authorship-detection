/**
 * Escribe un programa que muestre los n primeros términos de la serie 
 * de Fibonacci. El primer término de la serie de Fibonacci es 0, el 
 * segundo es 1 y el resto se calcula sumando los dos anteriores, por lo
 * que tendríamos que los términos son 0, 1, 1, 2, 3, 5, 8, 13, 21, 34,
 * 55, 89, 144… El número n se debe introducir por teclado.
 * 
 * @author costy
 */
import java.util.Scanner;

public class SeriaFibonacciConDoWhile {
  public static void main(String[] args) {   
    
    System.out.println("Escribe cualquier numero");
    
    int i = 1;
    int elemento;
    int num1 = 0;
    int num2 = 1;
    int suma;
    
    Scanner teclado = new Scanner(System.in);
    elemento = teclado.nextInt();
    
    do {  
        if (elemento == 1){
            System.out.print("0");  
        } else {
            System.out.print(num1 + " ");
            do{
                suma = num1 + num2;
                System.out.print(num2 + " ");
                num1 = num2;
                num2 = suma;
            }while (i++ < (elemento - 1));
        }
    }while (i < elemento);
  }
}
