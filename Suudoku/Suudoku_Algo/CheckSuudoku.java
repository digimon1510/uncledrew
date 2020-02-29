package Suudoku_Algo;

public class CheckSuudoku{
   /** peerに同じ値がないかどうかの確認 **/
    public boolean check_same(int grid[][]){
        for(int row = 0; row < 9; row++){
            for(int col = 0; col < 9; col++){
                if(grid[row][col] == 0) continue;
                if(!(check_row(grid, row)) || !(check_column(grid, col)) || !(check_brock(grid, row, col)))
                    return false;
            }
        }
        return true;
    }

    private boolean check_row(int grid[][], int row){
        boolean check[] = new boolean[9];
        for(int col = 0; col < 9; col++){
            if(grid[row][col] == 0) continue;
            else if(check[grid[row][col]-1] == true) return false;
            
            check[grid[row][col]-1] = true;
        }
        return true;
    }

    private boolean check_column(int grid[][], int col){
        boolean check[] = new boolean[9];
        for(int row = 0; row < 9; row++){
            if(grid[row][col] == 0) continue;
            else if(check[grid[row][col]-1] == true) return false;
            
            check[grid[row][col]-1] = true;
        }
        return true;
    }

    private boolean check_brock(int grid[][], int row, int col){
        boolean check[] = new boolean[9];
        int b_r = (row/3)*3, b_c = (col/3)*3;
        for(int b_row = 0; b_row < 3; b_row++){
            for(int b_col = 0; b_col < 3; b_col++){
                if(grid[b_row+b_r][b_col+b_c] == 0) continue;
                else if(check[grid[b_row+b_r][b_col+b_c]-1] == true) return false;
                
                check[grid[b_row+b_r][b_col+b_c]-1] = true;
            }
        }
        return true;
    }
    
    public boolean check_no_num(boolean v[][][]){
        for(int row = 0; row < 9; row++){
            for(int col = 0; col < 9; col++){
                if(count_false(v[row][col]) == 0) return false;
            }
        }
        return true;
    }
    
    /** falseの数(任意のマスの値の可能性)を数える **/
    public int count_false(boolean v[]){
        int count = 0;
        for(int i  = 0; i < 9; i++) if(v[i] == false) count++;
        return count;
    }
}