
/**
 * Escribe un programa que muestre, cuente y sume los múltiplos de 3 que hay 
 * entre 1 y un número leído por teclado.
 * 
 * @author costy
 */
import java.util.Scanner;

public class MultiplosTresConDo {
  public static void main(String[] args) {
    
    Scanner tecla = new Scanner(System.in);
    
    System.out.println("Introduce un numero mas > que 1");
    int numIntroducido = tecla.nextInt();
    
    int cuenta = 0;
    int suma = 0;
    int i = 1;
    
    do{
        if ((i % 3) == 0){
            System.out.println(i + "");
            cuenta++;
            suma += i;   
        }
        i++;
    } while ( i <= numIntroducido);

    System.out.println("Desde 1 hasta numero introducido hay " + cuenta);
    System.out.println("Los cuales suman " + suma);
  }
}
