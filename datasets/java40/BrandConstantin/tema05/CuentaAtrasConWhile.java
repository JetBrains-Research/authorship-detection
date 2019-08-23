/**
 * Muestra los números del 320 al 160, contando de 20 en 20 hacia atrás 
 * utilizando un bucle while .
 * 
 * @author costy
 */

public class CuentaAtrasConWhile {
  public static void main(String[] args) {   
    
    System.out.println("Numeros del 320 al 160 de 20 en 20: ");
    
    int a = 320; 
    
    while (a >= 160) {
      System.out.print(" " + a);
      a -= 20;
    }
  }
}
