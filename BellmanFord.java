import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class BellmanFord{
    class Edge{
        int from,to,cost;
        Edge(int from, int to,int cost){this.from=from;this.to=to;this.cost=cost;}
    }
    void add_edge(int from,int to,int cost){
        edges.add(new Edge(from,to,cost));
    }
    List<Edge> edges=new ArrayList<>();
    int[] d;
    int V;
    BellmanFord(int V){
        this.V=V;
        d = new int[V];
    }
    BellmanFord(int V,int[] from,int[] to,int[] cost){
        this.V=V;
        d = new int[V];
        for(int i=0;i<from.length;++i)add_edge(from[i], to[i], cost[i]);
    }
    boolean shortestDis(int s){
        boolean update=false;
        Arrays.fill(d, Integer.MAX_VALUE/2);
        d[s]=0;
        int cnt=0;
        do{
            ++cnt;
            if(cnt==V)return true;
            update=false;
            for(Edge e:edges)if(d[e.from]+e.cost<d[e.to]){
                d[e.to]=d[e.from]+e.cost;
                update=true;
            }
        }while(update);
        return false;
    }


    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int V = scan.nextInt();
        int E = scan.nextInt();
        int s = scan.nextInt();
        int[] from = new int[E];
        int[] to = new int[E];
        int[] c = new int[E];
        for(int i=0;i<E;++i){
            from[i]=scan.nextInt();
            to[i]=scan.nextInt();
            c[i]=scan.nextInt();
        }
        BellmanFord bell = new BellmanFord(V,from,to,c);
        bell.shortestDis(s);
        for(int i=0;i<V;++i)System.out.println((bell.d[i]<Integer.MAX_VALUE/2 ? bell.d[i]:"INF"));
    }
}
