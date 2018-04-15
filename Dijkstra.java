import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;


@SuppressWarnings("unchecked")
class Dijkstra{
    class Edge{
        int to,cost;
        Edge(int to,int cost){this.to=to;this.cost=cost;}
    }
    class Node{
        int v,dis;
        Node(int v,int dis){this.v=v;this.dis=dis;}
    }
    List<Edge> edges[];
    int[] d;
    List<Integer> path = new ArrayList<>();
    int[] prev;
    void add_edge(int from,int to,int cost){
        edges[from].add(new Edge(to,cost));
    }
    Dijkstra(int N){
        edges = new ArrayList[N];
        d = new int[N];
        prev=new int[N];
        for(int i=0;i<N;++i)edges[i]=new ArrayList<>();
    }
    Dijkstra(int N,int[] from, int[] to,int[] cost){
        edges = new ArrayList[N];
        d = new int[N];
        prev = new int[N];
        for(int i=0;i<N;++i)edges[i]=new ArrayList<>();
        for(int i=0;i<from.length;++i)edges[from[i]].add(new Edge(to[i],cost[i]));
    }

    int shortest(int s,int t){
        Arrays.fill(d, Integer.MAX_VALUE);
        d[s]=0;
        PriorityQueue<Node> que = new PriorityQueue<>((a,b)->a.dis-b.dis);
        que.add(new Node(s,0));
        while(!que.isEmpty()){
            Node node = que.poll();
            if(node.dis > d[node.v])continue;
            for(Edge e : edges[node.v]){
                if(d[e.to]<=d[node.v]+e.cost)continue;
                d[e.to]=d[node.v]+e.cost;
                prev[e.to]=node.v;
                que.add(new Node(e.to, d[e.to]));
            }
        }
        if(d[t]!=Integer.MAX_VALUE){
            int v = t;
            do{
                path.add(v);v=prev[v];
            }while(v!=s);
            Collections.reverse(path);
        }
        return d[t];
    }



    // AIZU ONLINE JUDGE
    // 単一始点最短経路
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
        Dijkstra dijkstra = new Dijkstra(V, from, to, c);
        dijkstra.shortest(s, 0);
        for(int i=0;i<V;++i)System.out.println((dijkstra.d[i]<Integer.MAX_VALUE ? String.valueOf(dijkstra.d[i]):"INF"));
    }

}
