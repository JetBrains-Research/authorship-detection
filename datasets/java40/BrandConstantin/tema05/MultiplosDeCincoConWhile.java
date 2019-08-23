/**
 * Muestra los números múltiplos de 5 de 0 a 100 utilizando un bucle while . .
 * 
 * @author costy
 */

public class MultiplosDeCincoConWhile {
  public static void main(String[] args) {   
    
    System.out.println("Numeros multiplos de 5 de 0 a 100 son: ");
    
    int a = 0; 
    
    while (a < 101) {
      System.out.print(" " + a);
      a += 5;
    }
  }
}
