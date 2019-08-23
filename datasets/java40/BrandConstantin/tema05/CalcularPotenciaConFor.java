/**
 * Escribe un programa que pida una base y un exponente (entero positivo) y que 
 * calcule la potencia.
 * 
 * @author costy
 */
import java.util.Scanner;

public class CalcularPotenciaConFor {
  public static void main(String[] args) {   
    Scanner teclado = new Scanner(System.in);
    
    System.out.println("Escribe el base (numero entero positivo)");
    int base = teclado.nextInt();
    
    System.out.println("Escribe la exponente (numero entero positivo)");
    int expo = teclado.nextInt();
    
    int potencia = 1;
    
    for (int i = 1; i <= expo; i++){
    potencia *= base;
    }
    
    System.out.println(base + " ^ " + expo + " = " + potencia);
  }
}
