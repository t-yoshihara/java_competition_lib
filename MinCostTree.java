import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

@SuppressWarnings("unchecked")
class MinCostTree{

    int N;
    class Edge{
        int from,to;
        long w;
        Edge(int from,int to,long w){this.from=from;this.to=to;this.w=w;}
    }
    List<Edge> edges[];
    List<Edge> minedge[];
    void add_edge(int u,int v,long w){
        edges[u].add(new Edge(u,v,w));
        edges[v].add(new Edge(v,u,w));
    }
    MinCostTree(int N){
        this.N=N;
        edges = new ArrayList[N];
        minedge = new ArrayList[N];
        for(int i=0;i<N;++i)edges[i]=new ArrayList<>();
        for(int i=0;i<N;++i)minedge[i]=new ArrayList<>();
    }
    long mincost(){
        PriorityQueue<Edge> que = new PriorityQueue<>((a,b)->a.w<b.w ? -1:1);
        boolean used[] = new boolean[N];
        used[0]=true;
        long res = 0;
        for(Edge e : edges[0])que.add(e);
        while(!que.isEmpty()){
            Edge e = que.poll();
            if(used[e.to])continue;
            used[e.to]=true;
            minedge[e.from].add(e);
            res += e.w;
            for(Edge ed : edges[e.to])que.add(ed);
        }
        return res;
    }
}

class Main{

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        MinCostTree mct = new MinCostTree(n);
        for(int i=0;i<n;++i){
            for(int j=0;j<n;++j){
                long a = scan.nextLong();
                if(i>j && a>=0)mct.add_edge(i, j, a);
            }
        }
        System.out.println(mct.mincost());
    }


}