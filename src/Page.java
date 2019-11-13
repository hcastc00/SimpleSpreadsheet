import java.util.Scanner;

public class Page {

    int rows;
    int columns;
    Cell[][] cells;

    public Page(Scanner sc){
        //TODO leer amboen en la misma linea
        this.rows = sc.nextInt();
        this.columns = sc.nextInt();

        if (this.rows<=0 || this.columns<=0){
            invalidImput();
        }else{
            cells = new Cell[rows][columns];
        }


    }

    private void invalidImput(){
        System.out.println("Invalid Imput");
        System.exit(-1);
    }
}
