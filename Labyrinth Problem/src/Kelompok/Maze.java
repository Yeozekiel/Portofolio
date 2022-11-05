package Kelompok;

import java.util.Arrays;
import java.util.Scanner;

public class Maze {
    Scanner input = new Scanner(System.in);
    Coordinate[] coordinate;
    int[][] vertex;
    int[][] adjacencyMatrix;
    int[] parentNode;
    int vertexNumber;
    int minimumStep;
    int row;
    int col;

    public void solveMaze(int[][] maze){
        col = maze[0].length;
        row = maze.length;
        vertex = new int[row][col];
        vertex = findVertex(maze);
        setAdjacencyMatrix();

//        System.out.println("\nRepresentasi Kelompok.Maze dengan Adjacency Matrix: ");
//        for(int i=0;i<vertexNumber;i++){
//            for(int j=0;j<vertexNumber;j++){
//                System.out.print(adjacencyMatrix[i][j]+" ");
//            }
//            System.out.println();
//        }
        if(!dijkstra(0,vertexNumber-1)){
            System.out.println("Tidak ada solusi");
        }
    }

    public void playMaze(int[][] maze){
        char arah=' ';
        int langkah=-1,vertexPointer=0,x,y;
        int[][] editableMaze1;
        int[][] editableMaze2=new int[maze.length][maze[0].length];
        int menang=2;
        String skip;
        col = maze[0].length;
        row = maze.length;
        editableMaze1 = findVertex(maze);
        for(int i=0;i<row;i++) {
            if (col >= 0) System.arraycopy(editableMaze1[i], 0, editableMaze2[i], 0, col);
        }
        while(vertexPointer!=vertexNumber-1 && arah!='0'){
            x=coordinate[vertexPointer].x;
            y=coordinate[vertexPointer].y;
            for(int i=0;i<row;i++){
                for(int j=0;j<col;j++){
                    if(i==coordinate[0].x && j==coordinate[0].y) System.out.print("S ");
                    else if(i==coordinate[vertexNumber-1].x && j==coordinate[vertexNumber-1].y) System.out.print("F ");
                    else if(i==x && j==y) System.out.print("x ");
                    else if(editableMaze2[i][j]==-2) System.out.print("o ");
                    else if(editableMaze2[i][j]!=0) System.out.print("  ");
                    else System.out.print("█ ");
                }
                System.out.println();
            }
            System.out.println("(W atas | A kiri | S bawah | D kanan | 0 menyerah)");
            arah = input.next().charAt(0);
            if(arah=='A' || arah=='a'){
                if(y>0) {
                    if (editableMaze1[x][y-1]!=0) {
                        if(editableMaze2[x][y-1] != -2){
                            editableMaze2[x][y] = -2;
                            y--;
                            while(editableMaze1[x][y]<0){
                                editableMaze2[x][y]=-2;
                                y--;
                            }
                            editableMaze2[x][y]=-2;
                        }else {
                            editableMaze2[x][y] = -1;
                            y--;
                            while(editableMaze1[x][y]<0){
                                editableMaze2[x][y]=-1;
                                y--;
                            }
                        }
                    }
                }
            }
            else if(arah=='D' || arah=='d'){
                if(y<col-1) {
                    if (editableMaze1[x][y+1]!=0) {
                        if(editableMaze2[x][y+1] != -2){
                            editableMaze2[x][y] = -2;
                            y++;
                            while(editableMaze1[x][y]<0){
                                editableMaze2[x][y]=-2;
                                y++;
                            }
                            editableMaze2[x][y]=-2;
                        }else {
                            editableMaze2[x][y] = -1;
                            y++;
                            while(editableMaze1[x][y]<0){
                                editableMaze2[x][y]=-1;
                                y++;
                            }
                        }
                    }
                }
            }
            else if(arah=='W' || arah=='w'){
                if(x>0) {
                    if (editableMaze1[x-1][y]!=0) {
                        if(editableMaze2[x-1][y] != -2){
                            editableMaze2[x][y] = -2;
                            x--;
                            while(editableMaze1[x][y]<0){
                                editableMaze2[x][y]=-2;
                                x--;
                            }
                            editableMaze2[x][y]=-2;
                        }else {
                            editableMaze2[x][y] = -1;
                            x--;
                            while(editableMaze1[x][y]<0){
                                editableMaze2[x][y]=-1;
                                x--;
                            }
                        }
                    }
                }
            }
            else if(arah=='S' || arah=='s'){
                if(x<row-1) {
                    if (editableMaze1[x+1][y]!=0) {
                        if(editableMaze2[x+1][y] != -2){
                            editableMaze2[x][y] = -2;
                            x++;
                            while(editableMaze1[x][y]<0){
                                editableMaze2[x][y]=-2;
                                x++;
                            }
                            editableMaze2[x][y]=-2;
                        }else {
                            editableMaze2[x][y] = -1;
                            x++;
                            while(editableMaze1[x][y]<0){
                                editableMaze2[x][y]=-1;
                                x++;
                            }
                        }
                    }
                }
            }
            else if(arah=='0'){
                menang=0;
            }
            else{
                System.out.println("input tidak valid! hanya W/A/S/D/0");
                System.out.print("ketik apa saja untuk melanjutkan... ");
                skip = input.next();
            }
            vertexPointer=editableMaze1[x][y]-1;
        }
        solveMaze(maze);
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(editableMaze2[i][j]==-2)langkah++;
            }
        }
        System.out.println("langkahmu      : "+langkah +"\nlangkah terbaik: "+minimumStep);
        if(langkah>minimumStep) menang=1;
        if(menang==2) {
            ReadFile.printFile("C:\\groupproject\\CoverWin.txt");
            System.out.println("Selamat! Anda menang! itu adalah solusi terbaik");
        }
        else if(menang==1){
            ReadFile.printFile("C:\\groupproject\\CoverLose.txt");
            System.out.println("Anda kalah karena itu bukan jalan terpendek");
        }else{
            ReadFile.printFile("C:\\groupproject\\CoverLose.txt");
            System.out.println("Anda kalah karena menyerah");
        }
        System.out.print("ketik apa saja untuk kembali ke menu utama... ");
        skip = input.next();
    }
//    void findVertex berfungsi untuk menandai vertex yang ada di dalam labirin
//    i=baris j=kolom
//    jika array movement[i][j][0]=1 maka dari i,j bisa bergerak 1 langkah ke atas
//    jika array movement[i][j][1]=1 maka dari i,j bisa bergerak 1 langkah ke bawah
//    jika array movement[i][j][2]=1 maka dari i,j bisa bergerak 1 langkah ke kiri
//    jika array movement[i][j][3]=1 maka dari i,j bisa bergerak 1 langkah ke kanan
    public int[][] findVertex(int[][] maze){
        int[][][] movement = new int[row][col][4];
        int[][] vertexMaze = new int[row][col];
        vertexNumber = 0;
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(maze[i][j]==1){
                    if(i!=0)        if(maze[i-1][j]==1) movement[i][j][0]=1;
                    if(i!=row-1)    if(maze[i+1][j]==1) movement[i][j][1]=1;
                    if(j!=0)        if(maze[i][j-1]==1) movement[i][j][2]=1;
                    if(j!=col-1)    if(maze[i][j+1]==1) movement[i][j][3]=1;
                }
            }
        }
//        vertex[i][j]=-1 untuk menandakan bahwa petak i,j berada di edge, jadi petak itu bukan vertex
        for(int i=0;i<row;i++) {
            for(int j=0; j<col; j++) {
                if(Arrays.equals(movement[i][j], new int[]{1, 1, 0, 0}) || Arrays.equals(movement[i][j], new int[]{0,0,1,1})){
                    vertexMaze[i][j]=-1;
                }else if(maze[i][j]==1){
                    vertexNumber++;
                    vertexMaze[i][j]=vertexNumber;
                }
            }
        }
        adjacencyMatrix = new int[vertexNumber][vertexNumber];
        coordinate = new Coordinate[vertexNumber];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(vertexMaze[i][j]>0){
                    coordinate[vertexMaze[i][j]-1] = new Coordinate(i,j);
                }
            }
        }
        return vertexMaze;
    }

//    method setAdjacencyMatrix adalah method yang berguna untuk membuat adjacencyMatrix dari maze agar
//    shortest path bisa dicari. adjacencyMatrix dibuat dengan melakukan pengecekan tetangga dari setiap vertex
//    (setiap vertex secara iteratif dari 0,0 sampai row-1,col-1 akan dicek tetangga kanan dan bawahnya.)
    private void setAdjacencyMatrix(){
        int distance;
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(vertex[i][j]!=0 && vertex[i][j]!=-1){
                    if(j!=col-1){
                        distance=0;
                        for(int k=j+1;k<col;k++){
                            distance++;
                            if(vertex[i][k]==0) break;
                            else if(vertex[i][k]!=-1){
                                adjacencyMatrix[vertex[i][j]-1][vertex[i][k]-1]=distance;
                                adjacencyMatrix[vertex[i][k]-1][vertex[i][j]-1]=distance;
                                break;
                            }
                        }
                    }
                    if(i!=row-1){
                        distance=0;
                        for(int k=i+1;k<row;k++){
                            distance++;
                            if(vertex[k][j]==0) break;
                            else if(vertex[k][j]!=-1){
                                adjacencyMatrix[vertex[i][j]-1][vertex[k][j]-1]=distance;
                                adjacencyMatrix[vertex[k][j]-1][vertex[i][j]-1]=distance;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean dijkstra(int src,int dst){
        boolean res=true;
        parentNode = new int[vertexNumber];
        int[] distance = new int[vertexNumber];
        boolean[] fixed = new boolean[vertexNumber];
        for(int i=0;i<vertexNumber;i++){
            distance[i]=Integer.MAX_VALUE;
            fixed[i]=false;
        }
        parentNode[src]=-1;
        distance[src]=0;
        while (true){
            int marked=minIndex(distance,fixed);
            if(marked<0) break;
            if(distance[marked]==Integer.MAX_VALUE) break;
            fixed[marked]=true;
            for(int j=0;j<vertexNumber;j++){
                if(adjacencyMatrix[marked][j]>0 && !fixed[j]){
                    int newDistance=distance[marked]+adjacencyMatrix[marked][j];
                    if(newDistance<distance[j]){
                        distance[j]=newDistance;
                        parentNode[j]=marked;
                    }
                }
            }
        }
        if(distance[dst]==Integer.MAX_VALUE){
            res=false;
        }else{
            drawPath();
            System.out.println();
            printPath(dst);
            minimumStep=distance[dst];
        }
        return res;
    }

    private int minIndex(int[] distance,boolean[] fixed){
        int idx=0;
        for(;idx< fixed.length;idx++)
            if(!fixed[idx]) break;
        if(idx==fixed.length) return -1;
        for(int i=idx+1;i< fixed.length;i++)
            if(!fixed[i] && distance[i]<distance[idx]) idx=i;
        return idx;
    }

    private boolean findNeighbor(int x,int y,int parent,int direction){
        //atas,bawah,kiri,kanan
        boolean res=false;
        if(direction==1){
            int temp = x;
            while(vertex[temp][y] == -1) temp--;
            if(vertex[temp][y]==parent){
                for(int i=x;i>=temp;i--){
                    vertex[i][y]=-2;
                }
                res = true;
            }
        }else if(direction==2){
            int temp = x;
            while(vertex[temp][y]==-1 && temp<=row-1) temp++;
            if(vertex[temp][y]==parent){
                for(int i=x;i<=temp;i++){
                    vertex[i][y]=-2;
                }
                res = true;
            }
        }else if(direction==3){
            int temp=y;
            while(vertex[x][temp] == -1) temp--;
            if(vertex[x][temp]==parent){
                for(int i=y;i>=temp;i--){
                    vertex[x][i]=-2;
                }
                res=true;
            }
        }else if(direction==4){
            int temp=y;
            while(vertex[x][temp]==-1 && temp<=col-1) temp++;
            if(vertex[x][temp]==parent){
                for(int i=y;i<=temp;i++){
                    vertex[x][i]=-2;
                }
                res=true;
            }
        }
        return res;
    }

    private void printPath(int dst){
        StringBuilder path= new StringBuilder();
        while(parentNode[dst]!=-1){
            path.insert(0, "-"+(dst+1));
            dst= parentNode[dst];
        }
        path.insert(0,dst+1);
        System.out.println(path);
    }

    private void drawPath(){
        int parent,x,y,temp = vertexNumber-1;
        boolean status;
        vertex[coordinate[temp].x][coordinate[temp].y]=-2;
        while(temp!=0){
            status=false;
            x=coordinate[temp].x;
            y=coordinate[temp].y;
            parent = parentNode[temp];
            if(x!=0)                    if(findNeighbor(x-1,y,parent+1,1)) status=true;
            if(x!=row-1 && !status)     if(findNeighbor(x+1,y,parent+1,2)) status=true;
            if(y!=0 && !status)         if(findNeighbor(x,y-1,parent+1,3)) status=true;
            if(y!=col-1 && !status)     findNeighbor(x,y+1,parent+1,4);
            temp = parentNode[temp];
        }
        System.out.println("\nSolusi terbaik: ");
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(vertex[i][j]==-1) vertex[i][j]=1;
                if(vertex[i][j]==-2) System.out.print("o ");
                else if(vertex[i][j]!=0) System.out.print("  ");
                else System.out.print("█ ");
            }
            if(i!=row-1)System.out.println();
        }
    }

}
