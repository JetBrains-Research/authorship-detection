/**
 * Escribe un programa que muestre en tres columnas, el cuadrado y el cubo de 
 * los 5 primeros números enteros a partir de uno que se introduce por teclado.
 * 
 * @author costy
 */
import java.util.Scanner;

public class CuadradoYcuboConFor {
  public static void main(String[] args) {   
    
    System.out.println("Escribe cualquier numero de 1 a 99");
    
    int num;
    int contador;
      
    Scanner teclado = new Scanner(System.in);
    num = teclado.nextInt();
    
    for (contador = 0; contador < 5; contador++){
      System.out.println(num + " || " + num*num + " || "+ num*num*num);  
      num++;
    }
  }
}
