package Suudoku_Algo;
import java.util.ArrayList;

/** 構造体（クラスで代用） **/
// バックトラックで使用
class values{
    int row, col, index; // 行、列、サブの値
    boolean v_copys[][][]; // バックラックするためのサブのvalue
}

public class Suudoku{
    ArrayList<values> list = new ArrayList<>();

    CheckSuudoku c = new CheckSuudoku();
    SetSuudoku ss = new SetSuudoku();
    PrintSuudoku p = new PrintSuudoku();

    public boolean value[][][] = new boolean[9][9][9]; // 盤面
    public int board[][] = new int[9][9];  // 与えられた初期値
    public int b_copy[][] = new int[9][9]; // 回答

    public Suudoku(int grid[][]){
        set_suudoku(grid);
        if(solve())System.out.println("Solved !");
        else System.out.println("Not solved ..");
        p.print_grid(board);
        p.print_grid(b_copy);
    }

    /** 数独の盤面をセット **/
    public void set_suudoku(int data[][]){
        // すでにboardに数値がある場合に、valueを操作
        // その数値以外の値をTrueに変更
        board = data;
        for(int i = 0;  i < 9; i++){
            for(int j = 0; j < 9; j++){
                b_copy[i][j] = board[i][j];
                if(board[i][j] == 0) continue;
                for(int k = 0; k < 9; k++){
                    if(k == board[i][j]-1) continue;
                    value[i][j][k] = true;
                }
            }
        }
        value = ss.peer(value);
    }

    /** 数独のソルバー **/
    public boolean solve(){
        if(c.check_same(b_copy) == false) return false;

        value = ss.peer(value);
        for(int row = 0; row < 9; row++){
            for(int col = 0; col < 9; col++){
                if(c.count_false(value[row][col]) != 2) continue;
                int index[] = new int[2];
                int count = 0;
                for(int i = 0; i < 9; i++){
                    if(value[row][col][i] == true) continue;
                    index[count] = i+1;
                    count++;
                    if(count == 2) break;
                }

                // 構造体(クラス)の生成
                values v = new values();
                v.row = row;
                v.col = col;
                v.index = index[1]-1;
                v.v_copys = ss.copy_value(value);
                list.add(v); // リストに追加

                value[row][col][index[0]-1] = true;
                b_copy = ss.perce_grid(value);
                if(solve() == false){
                    value = list.get(list.size()-1).v_copys;
                    value[list.get(list.size()-1).row][list.get(list.size()-1).col][list.get(list.size()-1).index] = true;
                    list.remove(list.size()-1);
                    b_copy = ss.perce_grid(value);
                    solve();
                }else{return true;}
            }
        }
        b_copy = ss.perce_grid(value);

        if(!(c.check_no_num(value)) || !(c.check_same(b_copy))) return false;
        return true;
    }

    public static void main(String [] args){
        int g[][] = {{8,0,0,0,0,0,0,0,0},
                     {0,0,3,6,0,0,0,0,0},
                     {0,7,0,0,9,0,2,0,0},
                     {0,5,0,0,0,7,0,0,0},
                     {0,0,0,0,4,5,7,0,0},
                     {0,0,0,1,0,0,0,3,0},
                     {0,0,1,0,0,0,0,6,8},
                     {0,0,8,5,0,0,0,1,0},
                     {0,9,0,0,0,0,4,0,0}};
        /*int g[][] = {{0,0,5,3,0,0,0,0,0},
                     {8,0,0,0,0,0,0,2,0},
                     {0,7,0,0,1,0,5,0,0},
                     {4,0,0,0,0,5,3,0,0},
                     {0,1,0,0,7,0,0,0,6},
                     {0,0,3,2,0,0,0,8,0},
                     {0,6,0,5,0,0,0,0,9},
                     {0,0,4,0,0,0,0,3,0},
                     {0,0,0,0,0,9,7,0,0}};*/
        Suudoku s = new Suudoku(g);
    }
}