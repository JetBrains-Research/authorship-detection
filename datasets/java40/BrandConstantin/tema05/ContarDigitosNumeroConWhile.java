/**
 * Realiza un programa que nos diga cuántos dígitos tiene un número introducido 
 * por teclado.
 * 
 * @author costy
 */
import java.util.Scanner;

public class ContarDigitosNumeroConWhile {
  public static void main(String[] args) {   
    Scanner tecla = new Scanner(System.in);
    
    System.out.println("CONTAR DIGITOS DE UN NUMERO");
    System.out.println("============================================");

    int ultimoDigito = 0;

    System.out.println("Escribe un numero y te digo de cuantos digitos esta formado");
    int num = tecla.nextInt();
    
    while (num > 0){
      num = num / 10;
      ultimoDigito++;
    }
    
    System.out.println("El numero tiene " + ultimoDigito + " digitos");
  }
}
