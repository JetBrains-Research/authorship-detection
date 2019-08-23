/*
 * Muestra la tabla de multiplicar de un número introducido por teclado.
 *
 * @author Costy
 */
import java.util.Scanner;
public class TablaMultiplicacionConDoWhile {
    public static void main(String[] args){
        Scanner tecla = new Scanner(System.in);
        
        System.out.println("TABLA MULTIPLICAR");
        System.out.println("============================================");
        
        int i = 1;
        int resultado;
        
        System.out.println("Escribe el numero que quieres multiplicar");
        int num = tecla.nextInt();
        System.out.println("============================================");
        
        do{
            resultado = i * num;
            System.out.println(num + " X " + i + " = " + resultado);
            i++;
        } while (i <= 10);
    }
}
