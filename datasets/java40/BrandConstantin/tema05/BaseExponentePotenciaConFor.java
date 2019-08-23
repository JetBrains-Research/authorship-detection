/**
 * Escribe un programa que dados dos números, uno real (base) y un entero 
 * positivo (exponente), saque por pantalla todas las potencias con base el 
 * numero dado y exponentes entre uno y el exponente introducido. No se deben 
 * utilizar funciones de exponenciación. Por ejemplo, si introducimos el 2 y el 
 * 5, se deberán mostrar 2^1 , 2^2 , 2^3 , 2^4 y 2^5 .
 * 
 * @author costy
 */
import java.util.Scanner;

public class BaseExponentePotenciaConFor {
    public static void main(String[] args) {   
        
    Scanner teclado = new Scanner(System.in);
    
    System.out.println("Escribe el base (numero entero positivo)");
    int base = teclado.nextInt();
    
    System.out.println("Escribe la exponente (numero entero positivo)");
    int exponente = teclado.nextInt();
    
    int potencia = 1;
    int y;
    
    for(y = 1; y <= exponente; y++){
        potencia *= base;
        System.out.println(base + " ^ " + y + " = " + potencia);   
    }
  } 
}
