/**
 * Muestra los números del 320 al 160, contando de 20 en 20 hacia atrás 
 * utilizando un bucle for .
 * 
 * @author costy
 */

public class CuentaAtrasConFor {
  public static void main(String[] args) {   
    
    System.out.println("Numeros del 320 al 160 de 20 en 20: ");
    
    for (int a = 320; a >= 160; a -= 20) {
      System.out.print(" " + a);
    }
  }
}
