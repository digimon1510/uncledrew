package Suudoku_Algo;

public  class PrintSuudoku{
    public void print_value(boolean a[][][]){
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                for(int k = 0; k < 9; k++){
                    System.out.print(a[i][j][k]+"\t");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public void print_grid(int grid[][]){
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                System.out.print(grid[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void print_peer(boolean v[][][]){
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                for(int k = 0; k < 9; k++){
                    if(v[i][j][k] == true) continue;
                    System.out.print(k+1+",");
                }
                System.out.print(" | ");
            }
            System.out.println("\n");
        }
    }
}