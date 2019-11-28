package prg2.intermedia;
import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args){
        //Pido la entrada de una hoja
        int numPages = Main.sc.nextInt();
        sc.nextLine();

        //Compruebo numero de hojas validas
        if (numPages<=0) invalidImput();

        Page pages[] = new Page[numPages];
        for (int i = 0; i<numPages;i++){
            pages[i] = new Page();
        }
        for (int i = 0; i<numPages;i++){
            pages[i].solve();
        }

    }
    public static void invalidImput(){
        System.err.println("Invalid Input");
        System.exit(-1);
    }
}
