package prg2.intermedia;
import java.util.Scanner;

public class Page {

    private int rows;
    private int columns;
    private Scanner sc = Main.sc;
    private String[][] cells;
    private boolean[][] isFormula;
    private int[][] solution;

    public Page() {
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
        String[] lineCells;
        for (int i = 0; i < this.rows; i++) {
            line = this.sc.nextLine();
            lineCells = line.split(" ");
            if (lineCells.length != this.columns) Main.invalidImput();
            this.cells[i] = lineCells;
        }
    }

    private void getFormulas() {
        for (int y = 0; y < this.rows; y++) {
            for (int x = 0; x < this.columns; x++) {
                if (this.cells[y][x].charAt(0) == '=') {
                    this.isFormula[y][x] = true;
                }
            }
        }
    }

    public void solve() {
        Cell form;

        for (int y = 0; y < this.rows; y++) {
            for (int x = 0; x < this.columns; x++) {
                if (isFormula[y][x]) {
                    form = new Cell(y, x);
                    solution[y][x] = solveFormula(form);
                } else {
                    solution[y][x] = Integer.parseInt(cells[y][x]);
                }
            }
        }
        printMat(solution);
    }

    private int solveFormula(Cell form) {
        String formula = this.cells[form.getY()][form.getX()].replaceAll("=", "");
        String[] operators = formula.split("\\+");
        Cell coords;
        int value = 0;
        for (int i = 0; i < operators.length; i++) {
            String operator = operators[i];
            coords = stringToCoords(operator);
            if (isFormula[coords.getY()][coords.getX()]) {
                value += solveFormula(coords);
            } else {
                value += Integer.parseInt(cells[coords.getY()][coords.getX()]);
            }
        }
        return value;
    }

    private Cell stringToCoords(String input) {
        StringBuilder letters = new StringBuilder();
        StringBuilder numbers = new StringBuilder();
        Cell coords;

        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i))) {
                numbers.append(input.charAt(i));
            } else {
                letters.append(input.charAt(i));
            }
        }
        coords = new Cell(Integer.parseInt(numbers.toString()) - 1, lettersToNumber(letters.toString()));
        return coords;
    }

    private int lettersToNumber(String letters) {
        letters = letters.toUpperCase();
        char[] lettersSplit = letters.toCharArray();
        int value = 0;
  /*
  Recorre el arry de letras de izda a derecha, es decir, de mas valor (exp mayor) a menos
  Las letras mayusculas emiezan en A=64, le resto 64 para que A=0
  Se hace un cambio de base, de base 26 a base 10
   */
        for (int exp = 0, pos = lettersSplit.length - 1; exp < lettersSplit.length; exp++, pos--) {
            value += (lettersSplit[pos] - 64) * Math.pow(26, exp);
        }
        value--;
        return value;
    }

    private void printMat(int[][] mat) {
        for (int y = 0; y < this.rows; y++) {
            for (int x = 0; x < this.columns; x++) {
                System.out.print(mat[y][x]);
                if (x == this.columns - 1) break; //No mete espacio al final de los ultimos numeros de cada fila
                System.out.print(" ");
            }
            System.out.println("");
        }
    }
}
//Clase cell utilizada para manejar coordenadas de forma mas comoda
class Cell {
    private int y, x;

    public Cell(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}