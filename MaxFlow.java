import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

@SuppressWarnings("unchecked")
class MaxFlow{
    // Dinic O(V^2*E)
    class Edge{
        int to,cap,rev;
        Edge(int to,int cap,int rev){this.to=to;this.cap=cap;this.rev=rev;}
    }
    List<Edge> edges[];
    void add_edge(int from,int to,int cap){
        edges[from].add(new Edge(to,cap,edges[to].size()));
        edges[to].add(new Edge(from,0,edges[from].size()-1));
    }
    int[] itr;
    int[] rank;

    MaxFlow(int V){
        edges = new ArrayList[V];
        for(int i=0;i<V;++i)edges[i]=new ArrayList<>();
        itr = new int[V];
        rank = new int[V];
    }

    private void bfs(int s){
        Arrays.fill(rank,-1);
        rank[s]=0;
        Queue<Integer> que = new ArrayDeque<>();
        que.add(s);
        while(!que.isEmpty()){
            int v = que.poll();
            for(Edge e : edges[v])if(rank[e.to]<0 && e.cap>0){
                rank[e.to]=rank[v]+1;
                que.add(e.to);
            }
        }
    }
    private int dfs(int s,int t,int f){
        if(s==t)return f;
        while(itr[s]<edges[s].size()){
            Edge e = edges[s].get(itr[s]);
            if(rank[e.to]>rank[s] && e.cap>0){
                int res = dfs(e.to,t,Math.min(f, e.cap));
                if(res>0){
                    e.cap-=res;
                    edges[e.to].get(e.rev).cap+=res;
                    return res;
                }
            }
            itr[s]++;
        }
        return 0;
    }

    int get(int s,int t){
        int res = 0;
        boolean update=true;
        while(update){
            update=false;
            Arrays.fill(itr,0);
            bfs(s);
            while(true){
                int f = dfs(s,t,Integer.MAX_VALUE);
                if(f>0){
                    res+=f;
                    update=true;
                }else break;
            }
        }
        return res;
    }

    // AOJ Network Flow
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int V = scan.nextInt();
        int E = scan.nextInt();
        MaxFlow mf = new MaxFlow(V);
        while(E-->0){
            int u = scan.nextInt();
            int v = scan.nextInt();
            int c = scan.nextInt();
            mf.add_edge(u, v, c);
        }
        System.out.println(mf.get(0,V-1));
    }
}
