/**
* Igual que el ejercicio anterior pero esta vez se debe pintar una pirámide hueca.
* Pero pidiendo el caracter por teclado
* @author costy
 */
import java.util.Scanner;

public class PiramideHuecaConFor {

public static void main(String[] args) {
    Scanner teclado = new Scanner(System.in);
    
    System.out.println("Introduce la altura de la pirámide:");
    int alturaIntrod = teclado.nextInt();
    System.out.println("Introduce el carácter:");
    String caracter = teclado.next();
    System.out.println("------------------------------------------------------");

    int altura = 1;
    int i = 0;
    int espacio = alturaIntrod - 1;
    int hueco = 0;

    System.out.println("------------------------------------------------------");

    while(altura < alturaIntrod){
        for(i = 1; i <= espacio; i++){
            System.out.print(" ");
        }
        
        System.out.print(caracter);
        for(i = 1; i < hueco; i++){
            System.out.print(" ");
        }
        
        if (altura > 1){
            System.out.print(caracter);
        } 
        
        System.out.println("");
        altura++;
        espacio--;
        hueco += 2;
    }
    
    for(i = 1; i < altura * 2; i++){
        System.out.print(caracter);
    }
  }
}