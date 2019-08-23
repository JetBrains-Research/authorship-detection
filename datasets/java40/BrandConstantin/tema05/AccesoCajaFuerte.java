/*
 * Realiza el control de acceso a una caja fuerte. La combinación será un número de 4 cifras.
 * El programa nos pedirá la combinación para abrirla. Si no acertamos, se nos mostrará el
 * mensaje “Lo siento, esa no es la combinación” y si acertamos se nos dirá “La caja fuerte se
 * ha abierto satisfactoriamente”. Tendremos cuatro oportunidades para abrir la caja fuerte.
 *
 * @author Costy
 */
import java.util.Scanner;
public class AccesoCajaFuerte {
    public static void main(String[] args){
        Scanner tecla = new Scanner(System.in);
        
        System.out.println("ACCESO CAJA FUERTE (la combinación esta formada por cifras de 1 a 9)");
        System.out.println("============================================");
        
        int combinacion;
        int i = 4;
        boolean encontrado = false;
        
        do {
            System.out.println("Escribe la combinación: (formada por 4 cifras!)");
            combinacion = tecla.nextInt();

            if(combinacion == 1298){
                encontrado = true;
            } else {
                System.err.println("Esta no es la combinación!");
             } 
            i--;
        } while ((i > 0) && (!encontrado));
        if(encontrado){
            System.out.println("Has abierto la caja fuerte!");
        } else {
            System.out.println("Has agotado las oportunidades!");
        }

    }
}
