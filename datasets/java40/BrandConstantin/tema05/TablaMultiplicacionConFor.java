/*
 * Muestra la tabla de multiplicar de un número introducido por teclado.
 *
 * @author Costy
 */
import java.util.Scanner;
public class TablaMultiplicacionConFor {
    public static void main(String[] args){
        Scanner tecla = new Scanner(System.in);
        
        System.out.println("TABLA MULTIPLICAR");
        System.out.println("============================================");
        
        int i;
        int resultado;
        
        System.out.println("Escribe el numero que quieres multiplicar");
        int num = tecla.nextInt();
        System.out.println("============================================");
        
        for(i = 1; i <= 10; i++){
            resultado = i * num;
            System.out.println(num + " X " + i + " = " + resultado);
        }
    }
}
