package Suudoku_Algo;
import Suudoku_Algo.CheckSuudoku;;

public class SetSuudoku{
    CheckSuudoku c = new CheckSuudoku();
    private int count;
    
    public int[][] perce_grid(boolean value[][][]){
        int grid[][] = new int[9][9];
        for(int row = 0; row < 9; row++){
            for(int col = 0; col < 9; col++){
                if(c.count_false(value[row][col]) == 1){
                    for(count = 0; count < 9; count++)
                        if(value[row][col][count] == false) break;
                        grid[row][col] = count+1;
                }else{
                    grid[row][col] = 0;
                }
            }
        }
        return grid;
    }

    public boolean[][][] copy_value(boolean original[][][]){
        boolean copy[][][] = new boolean[9][9][9];
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                for(int k = 0; k < 9; k++){
                    if(original[i][j][k] == true) copy[i][j][k] = true;
                }
            }
        }
        return copy;
    }

    public int[][] copy_grid(int original[][]){
        int copy[][] = new int[9][9];
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                copy[i][j] = original[i][j];
            }
        }
        return copy;
    }

    /** 制約伝搬 **/
    public boolean[][][] peer(boolean values[][][]){
        int data;
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(c.count_false(values[i][j]) != 1) continue;
                for(count = 0; count < 9; count++)
                    if(values[i][j][count] == false) break;
                data = count+1;
                // row(行)
                for(int row = 0; row < 9; row++){
                    if(c.count_false(values[i][row]) == 1) continue;
                    values[i][row][data-1] = true;
                }
                // colmun(列)
                for(int colmun = 0; colmun < 9; colmun++){
                    if(c.count_false(values[colmun][j]) == 1) continue;
                    values[colmun][j][data-1] = true;
                }
                // block(ブロック)
                int b1 = (i/3)*3;
                int b2 = (j/3)*3;
                for(int b_row = 0; b_row < 3; b_row++){
                    for(int b_colmun = 0; b_colmun < 3; b_colmun++){
                        if(c.count_false(values[b1+b_row][b2+b_colmun]) == 1) continue;
                        values[b1+b_row][b2+b_colmun][data-1] = true;
                    }
                }
            }
        }
        return values;
    }  
}