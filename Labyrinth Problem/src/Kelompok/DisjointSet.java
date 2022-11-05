package Kelompok;

import java.util.LinkedList;
class Set{
    private int parent;
    private int rank;

    public Set(int data){
        this.parent=data;
        this.rank=0;
    }

    public int getParent(){
        return this.parent;
    }

    public void setParent(int parent){
        this.parent=parent;
    }

    public int getRank(){
        return this.rank;
    }

    public void setRank(int rank){
        this.rank=rank;
    }
}

public class DisjointSet {
    private Set[] sets;
    private int sz;

    public DisjointSet(int numItem){
        this.sz=numItem;
        this.sets=new Set[sz];
        for(int i=0; i<this.sz; i++){
            this.sets[i]=new Set(i);
        }
    }

    public int find(int item){
        int parent=this.sets[item].getParent();
        if(item==parent){
            return item;
        } else{
            parent=find(parent);
            this.sets[item].setParent(parent); //path compression
            return parent;
        }
    }

    public boolean isSameSet(int firstItem, int secondItem){
        return find(firstItem)==find(secondItem);
    }

    public void union(int firstItem, int secondItem){
        int firstItemParent=find(firstItem);
        int secondItemParent=find(secondItem);

        if(firstItemParent!=secondItemParent){
            int firstRank=this.sets[firstItemParent].getRank();
            int secondRank=this.sets[secondItemParent].getRank();

            if(firstRank<secondRank){
                this.sets[firstItemParent].setParent(secondItemParent);
            } else if (firstRank>secondRank){
                this.sets[secondItemParent].setParent(firstItemParent);
            } else{
                this.sets[secondItemParent].setParent(firstItemParent);
                this.sets[firstItemParent].setRank(firstRank+1);
            }
        }
    }

    public void print(){
        for(int i=0; i<this.sz; i++){
            System.out.println("Parent of "+i+" = "+find(i));
        }
    }

    public void printRank(){
        for(int i=0; i<this.sz; i++){
            System.out.println("Rank of "+i+" = "+this.sets[i].getRank());
        }
    }

    /*
elements are 0, 1, 2, ... 24; sets are connected parts of the maze
start with each element in its own set;
repeat {
pick two adjacent elements p and q (= p ± 1 or p ± 5) at random;
if (find(p) != find(q)) {
erase the wall between p and q;
union(p, q);
}
}
until 24 walls have been erased
*/
}
