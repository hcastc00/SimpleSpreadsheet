package prg2.intermedia;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Page {

    private int rows;
    private int columns;
    private Scanner sc;
    private String[][] cells;
    private boolean[][] isFormula;
    private Queue<Cell> formulas = new LinkedList<Cell>();
    private int[][] solution;

    public Page(Scanner sc) {
        this.sc = sc;
        readSize();
        cells = new String[this.rows][this.columns];
        isFormula = new boolean[this.rows][this.columns];
        solution = new int[this.rows][this.columns];
        readCells();
        getFormulas();


    }

    private void readSize() {
        String input = this.sc.nextLine();
        String[] size = input.split(" ", 2);
        this.columns = Integer.parseInt(size[0]);
        this.rows = Integer.parseInt(size[1]);

        if (this.rows <= 0 || this.columns <= 0) {
            Main.invalidImput();
        }
    }

    private void readCells() {
        String line;
        String [] lineCells;
        for (int i = 0; i < this.rows; i++) {
            line = sc.nextLine();
            lineCells = line.split(" ");
            if(lineCells.length!=this.columns) Main.invalidImput();
            this.cells[i] = lineCells;
        }
    }

    private void getFormulas() {
        Cell cell;
        for (int y = 0; y<this.rows; y++) {
            for (int x = 0; x<this.columns; x++){
                if (this.cells[y][x].charAt(0) == '='){
                    this.isFormula[y][x] = true;
                }
            }
        }
    }

    public void solve(){
      Cell form;

       for (int y=0; y<this.rows; y++){
           for (int x=0; x<this.columns; x++){
               if (isFormula[y][x]){
                   form = new Cell(y,x);
                   solution[y][x] = solveFormula(form);
               }
               else{
                   solution[y][x] = Integer.parseInt(cells[y][x]);
               }
           }
       }

       print2D(solution);
    }

    private int solveFormula(Cell form){
        String formula = this.cells[form.getY()][form.getX()].replaceAll("=","");
        String[] operators = formula.split("\\+");
        Cell coords;
        int value = 0;
        for (int j=0; j<operators.length; j++){
           coords = stringToCoords(operators[j]);
            if (isFormula[coords.getY()][coords.getX()]){
                value += solveFormula(coords);
            }
            else{
                value += Integer.parseInt(cells[coords.getY()][coords.getX()]);
            }
        }
        return value;
    }


    private Cell stringToCoords(String input){
        StringBuilder letters = new StringBuilder();
        StringBuilder numbers = new StringBuilder();
        Cell coords;

        for (int i=0;i<input.length();i++) {
            if (Character.isDigit(input.charAt(i))) {
                numbers.append(input.charAt(i));
            } else {
                letters.append(input.charAt(i));
            }
        }
        coords = new Cell(Integer.parseInt(numbers.toString())-1,lettersToNumber(letters.toString()));
        return coords;
    }

    private int lettersToNumber(String letters){
        letters = letters.toUpperCase();
        char[] lettersSplit = letters.toCharArray();
        int value = 0;
        for (int i=0, b=lettersSplit.length-1;i<lettersSplit.length;i++,b--){
            value += (lettersSplit[b] - 64) * Math.pow(26,i);
        }
        value--;
        return value;
    }

    public static void print2D(int mat[][])
    {
        // Loop through all rows
        for (int[] row : mat)

            // converting each row as string
            // and then printing in a separate line
            System.out.println(Arrays.toString(row));
    }

}
