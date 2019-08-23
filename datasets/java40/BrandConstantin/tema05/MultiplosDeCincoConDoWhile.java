/**
 * Muestra los números múltiplos de 5 de 0 a 100 utilizando un bucle do-while .
 * 
 * @author costy
 */

public class MultiplosDeCincoConDoWhile {
  public static void main(String[] args) {   
    
    System.out.println("Numeros multiplos de 5 de 0 a 100 son: ");
    
    int a = 0; 
    
    do {
      System.out.print(" " + a);
      a += 5;
    } while  (a < 101);
  }
}
