/**
 * Escribe un programa que pida una base y un exponente (entero positivo) y que 
 * calcule la potencia.
 * 
 * @author costy
 */
import java.util.Scanner;

public class CalcularPotenciaConWhile {
  public static void main(String[] args) {   
    Scanner teclado = new Scanner(System.in);
    
    System.out.println("Escribe el base (numero entero positivo)");
    int base = teclado.nextInt();
    
    System.out.println("Escribe la exponente (numero entero positivo)");
    int expo = teclado.nextInt();
    
    int potencia = 1;
    int i = 1;
    
    while ( i <= expo){
        potencia *= base;
        i++;
    }
   
    System.out.println(base + " ^ " + expo + " = " + potencia);
  }
}
