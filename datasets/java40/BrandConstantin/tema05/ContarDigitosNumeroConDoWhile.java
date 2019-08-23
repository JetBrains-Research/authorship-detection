/**
 * Realiza un programa que nos diga cuántos dígitos tiene un número introducido 
 * por teclado.
 * 
 * @author costy
 */
import java.util.Scanner;

public class ContarDigitosNumeroConDoWhile {
  public static void main(String[] args) {   
    Scanner tecla = new Scanner(System.in);
    
    System.out.println("CONTAR DIGITOS DE UN NUMERO");
    System.out.println("============================================");

    int ultimoDigito = 0;

    System.out.println("Escribe un numero y te digo de cuantos digitos esta formado");
    int num = tecla.nextInt();
    
    do{
      num = num / 10;
      ultimoDigito++;
    } while (num > 0);
    
    System.out.println("El numero tiene " + ultimoDigito + " digitos");
  }
}
