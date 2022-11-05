package Kelompok;
import java.util.Random;
public class MazeGenerator {
    private static int[][] mazeV;
    int jalur_baris;
    int jalur_kolom;

    public int[][] getMazeV(){
        return mazeV;
    }

    public MazeGenerator(int baris, int kolom){
        this.jalur_baris=baris;
        this.jalur_kolom=kolom;
        mazeV =new int[baris*2+1][kolom*2+1];
        for(int i=0; i<baris*2+1; i++){
            for(int j=0; j<kolom*2+1; j++){
                if(i%2==0) mazeV[i][j]=0;
                else if(j%2==0) mazeV[i][j]=0;
                else mazeV[i][j]=1;
            }
        }
    }

    public void printMaze(){
        for(int i=0; i<jalur_baris*2+1; i++){
            for(int j=0; j<jalur_kolom*2+1; j++){
                System.out.print(mazeV[i][j]+" ");
            }
            System.out.println();
        }
    }

    public void deleteWalls() {
        Random random = new Random();
        int n = jalur_baris * jalur_kolom;
        DisjointSet connected = new DisjointSet(n);
        int i = 0;
        do {
            int p = random.nextInt(n);
            int q = p;
            int j = random.nextInt(4);
            switch (j) {
                case 0 : q++;
                case 1 : q--;
                case 2 : q += jalur_kolom;
                default : q -= jalur_kolom;
            }
//            System.out.println(p + " " + q + " " + i); //ini interface apa yang terjadi, bisa dihapus!!
            if (q >= n || q < 0) continue;

            if (!connected.isSameSet(p, q)) {
                if (p / jalur_kolom == q / jalur_kolom) {
                    mazeV[Math.max(p, q) / (jalur_kolom) * 2 + 1][(Math.max(p, q) % (jalur_kolom) * 2)] = 1;
                } else if (p % jalur_kolom == q % jalur_kolom) {
                    mazeV[Math.max(p, q) / (jalur_kolom) * 2][(Math.max(p, q) % (jalur_kolom) * 2 + 1)] = 1;
                } else continue;
                connected.union(p, q);
                i++;
            }
        } while (i < n - 1);
    }
}
