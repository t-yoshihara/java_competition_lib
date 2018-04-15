import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

@SuppressWarnings("unchecked")
class MinCostFlow{
    class Edge{
        int to,cap,cost,rev;
        Edge(int to,int cap,int cost,int rev){this.to=to;this.cap=cap;this.cost=cost;this.rev=rev;}
    }
    List<Edge> edges[];
    void add_edge(int from,int to,int cap,int cost){
        edges[from].add(new Edge(to,cap,cost,edges[to].size()));
        edges[to].add(new Edge(from,0,-cost,edges[from].size()-1));
    }
    int[] dis;
    int[] h;
    int[] prevv;
    Edge[] preve;
    MinCostFlow(int V){
        edges = new ArrayList[V];
        dis =new int[V];
        h = new int[V];
        prevv=new int[V];
        preve=new Edge[V];
        for(int i=0;i<V;++i)edges[i]=new ArrayList<>();
    }

    int get(int s,int t,int f){
        int res = 0;
        class Node{
            int v,cost;
            Node(int v,int cost){this.v=v;this.cost=cost;}
        }
        Arrays.fill(h,0);
        while(f>0){
            Arrays.fill(dis, Integer.MAX_VALUE);
            dis[s]=0;
            PriorityQueue<Node> que = new PriorityQueue<>((a,b)->a.cost-b.cost);
            que.add(new Node(s,0));
            while(!que.isEmpty()){
                Node node = que.poll();
                if(dis[node.v]<node.cost)continue;
                for(Edge e : edges[node.v])if(dis[node.v]+e.cost + (h[e.to]-h[node.v]) <dis[e.to] && e.cap>0){
                    dis[e.to] = dis[node.v]+e.cost + (h[e.to]-h[node.v]);
                    prevv[e.to]=node.v;
                    preve[e.to]=e;
                    que.add(new Node(e.to, dis[e.to]));
                }
            }
            if(dis[t]==Integer.MAX_VALUE)return -1;
            for(int i=0;i<h.length;++i)h[i] -= dis[i];
            int d = f;

            for(int v=t;v!=s;v=prevv[v])d=Math.min(d, preve[v].cap);
            res += d * -h[t];
            f-=d;
            for(int v=t;v!=s;v=prevv[v]){
                preve[v].cap-=d;
                edges[v].get(preve[v].rev).cap+=d;
            }
        }
        return res;
    }

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int V = scan.nextInt();
        int E = scan.nextInt();
        int F = scan.nextInt();
        MinCostFlow mcf = new MinCostFlow(V);
        while(E-->0){
            int u = scan.nextInt();
            int v = scan.nextInt();
            int c = scan.nextInt();
            int d = scan.nextInt();
            mcf.add_edge(u, v, c, d);
        }
        System.out.println(mcf.get(0,V-1,F));
    }
}
