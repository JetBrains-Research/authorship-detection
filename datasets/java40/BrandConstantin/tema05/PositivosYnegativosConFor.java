/**
 * Escribe un programa que lea una lista de diez números y determine cuántos son
 *  positivos y cuántos son negativos.
 * 
 * @author costy
 */
import java.util.Scanner;
public class PositivosYnegativosConFor {
  public static void main(String[] args) {   
    Scanner teclado = new Scanner(System.in);
    
    int positivos = 0;
    int negativos = 0;
    int contador;
    
    System.out.println("Escribe 10 numeros:");
    System.out.println("==========================================");
    
    for(contador = 0; contador < 10; contador++){
        System.out.println("Escribe un numero:");
        int num;
        num = teclado.nextInt();
        
        if(num >=0){
            positivos++;
        } else {
            negativos++;
        } 
    }
    System.out.println("Hay " + positivos + " numeros poisitivos y " +
         negativos + " numeros negativos");
  }
}
