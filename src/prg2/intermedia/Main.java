package prg2.intermedia;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        //Pido la entrada de una hoja
        Scanner sc = new Scanner(System.in);
        int numPages = sc.nextInt();
        sc.nextLine();

        //Check good input
        if (numPages<=0) invalidImput();

        Page pages[] = new Page[numPages];
        for (int i = 0; i<numPages;i++){
            pages[i] = new Page(sc);
        }
        for (int i = 0; i<numPages;i++){
            pages[i].solve();
        }

    }
    public static void invalidImput(){
        System.out.println("Invalid Input");
        System.exit(-1);
    }
}
