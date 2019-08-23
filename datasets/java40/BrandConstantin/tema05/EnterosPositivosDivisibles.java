/**
* Escribe un programa que muestre por pantalla todos los números enteros
* positivos menores a uno leído por teclado que no sean divisibles entre otro 
* también leído de igual forma.
* 
* @author costy
 */
import java.util.Scanner;

public class EnterosPositivosDivisibles {

public static void main(String[] args) {
Scanner teclado = new Scanner(System.in);
    
    System.out.println("Introduce un numro que sea grande");
    int numIntrod = teclado.nextInt();  

    System.out.println("Introduce un divisor que sea mas pequeño que el " + 
                 " anterior");
    int divisor = teclado.nextInt(); 
    
    do {
        for (int i = 1; i < numIntrod; i++){
            if((i % divisor) != 0){
                System.out.print(i + "   ");
            }
        }
    } while (numIntrod < 0);
  }
}