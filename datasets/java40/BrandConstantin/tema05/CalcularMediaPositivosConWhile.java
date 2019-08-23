/**
 * Escribe un programa que calcule la media de un conjunto de números positivos 
 * introducidos por teclado. A priori, el programa no sabe cuántos números se 
 * introducirán. El usuario indicará que ha terminado de introducir los datos 
 * cuando meta un número negativo.
 * 
 * @author costy
 */
import java.util.Scanner;

public class CalcularMediaPositivosConWhile {
  public static void main(String[] args) {   
    Scanner teclado = new Scanner(System.in);
    
    System.out.println("CALCULAR MEDIA POSITIVOS");
    System.out.println("===========================================");
    
    double contador = 0;
    double suma = 0;
    double media;
    double num = 0;

    
    while (num >= 0 && num < 100000){
        System.out.println("Introduce numeros:");
        num = teclado.nextDouble();
        contador++;
        suma += num;
        
        if(num < 0){
            System.out.println("Has introducido un numero negativo, el programa finaliza aquii!!! ");
        }
    }
    System.out.println("La media es " + (media = (suma - num) / (contador -1)));
  }
}