/**
 * Muestra los números del 320 al 160, contando de 20 en 20 utilizando un bucle 
 * do-while .
 * 
 * @author costy
 */

public class CuentaAtrasConDoWhile {
  public static void main(String[] args) {   
    
    System.out.println("Numeros multiplos de 5 de 0 a 100 son: ");
    
    int a = 320; 
    
    do {
      System.out.print(" " + a);
      a -= 20;
    } while  (a >= 160);
  }
}
