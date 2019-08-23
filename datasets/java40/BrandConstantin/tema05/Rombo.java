/**
*  Realiza un programa que pinte por pantalla un rombo hecho con  asteriscos. el programa
* debe pedir la altura. Se debe comparar que la altura sea un numero impar mayor o igual a 3, en 
* caso contrario se debe mostrar un mensaje de error.
* 
* 
**/
import java.util.Scanner;
public class Rombo {
    public static void main( String [] args ) {
        Scanner teclado = new Scanner(System.in);
        
        int  i;
        int k;
        int j;
        
        System.out.print("Introduce un número: ");
        int size = teclado.nextInt();
       System.out.print("===================================================================\n");
       
        if (size > 3) {
            for (i = 1; i <= (size - 1); i++){
                for (k = size; k > i; k--){
                    System.out.print(" ");
                } 
                for ( j =1; j <= i; j++){
                    System.out.print("*" + " ");
                }
                    System.out.println();
            }
            for (i = size ; i > 0; i--){
                for (k = size; k > i; k--){
                    System.out.print(" ");
                 }
                for (j =1; j <= i; j++){
                    System.out.print("*" + " ");
                }
                System.out.println();
            }
        } else {
            System.err.print("El numero tiene que ser mayor que 3");
        }
    }
}
