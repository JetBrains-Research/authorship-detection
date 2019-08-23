/**
 * Muestra los números múltiplos de 5 de 0 a 100 utilizando un bucle for .
 * 
 * @author costy
 */

public class MultiplosDeCincoConFor {
  public static void main(String[] args) {   
    
    System.out.println("Numeros multiplos de 5 de 0 a 100 son: ");
    
    for (int a = 0; a < 101; a += 5) {
      System.out.print(" " + a);
    }
  }
}
