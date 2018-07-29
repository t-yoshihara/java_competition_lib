import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


/**
 * java 接尾辞配列とLCP配列
 * sa[i] : 辞書順でi番目のsuffixはsa[i]から始まる.
 * rank[i] : saの逆関数 rank[sa[i]]=i;
 * lcp[i] : 辞書順でi番目とi+1番目の共通接頭辞長さ
 */
class SuffixArray{
    int N;
    String s;
    int[] sa;
    int[] rank;
    int[] tmp;
    int[] lcp;
    SuffixArray(String s){
        N=s.length();
        this.s=s+'$';//終端文字
        sa = new int[N+1];
        rank=new int[N+1];
        tmp=new int[N+1];
        lcp=new int[N+1];
        build_sa();
       build_lcp();
    }

    void build_lcp(){
        //rank:saの逆関数、 i文字目から始まるsuffixは辞書順でrank[i]番目
        for(int i=0;i<N;++i)rank[sa[i]]=i;
        int h=0;
        for(int i=0;i<N;++i){
            if(rank[i]==N)lcp[rank[i]]=h=0;
            else{
                int pos1 = sa[rank[i]];
                int pos2 = sa[rank[i]+1];
                while(s.charAt(pos1+h)==s.charAt(pos2+h))++h;
                lcp[rank[i]]=h;
                h = Math.max(0,h-1);
            }
        }
    }

    void build_sa(){
        //rank[i] : i文字目からはじまる部分suffixが何番目か
        for(int i=0;i<=N;++i){
            sa[i]=i;//0~Nを分布させる 
            rank[i]=(i==N ? -1:s.charAt(i));
        }
        for(int k=1;k<=N;k*=2){
            msort(sa,0,N+1,k);

            tmp[sa[0]]=0;
            for(int i=1;i<=N;++i){
                tmp[sa[i]] = tmp[sa[i-1]];
                if(rank[sa[i]]==rank[sa[i-1]]){
                    int lk = (sa[i]+k<=N ? rank[sa[i]+k] : -1);
                    int rk = (sa[i-1]+k<=N ? rank[sa[i-1]+k] : -1);
                    if(rk<lk)tmp[sa[i]]++;
                }else tmp[sa[i]]++;
            }
            for(int i=0;i<=N;++i)rank[i]=tmp[i];
        }
    }

    void msort(int[] array, int l, int r,int k){
        if(r-l<=1)return;
        int c = (r+l)/2;
        msort(array, l,c,k);
        msort(array, c,r,k);
        int[] tmp = new int[r-l];
        int index=0;
        int lindex=l;
        int rindex=c;
        for(int i=l;i<r;++i){
            if(lindex<c && rindex<r){
                if(rank[array[lindex]]==rank[array[rindex]]){
                    int lk = (array[lindex]+k<=N ? rank[array[lindex]+k] : -1);
                    int rk = (array[rindex]+k<=N ? rank[array[rindex]+k] : -1);
                    if(lk<rk)tmp[index++]=array[lindex++];
                    else tmp[index++]=array[rindex++];
                }else{
                    if(rank[array[lindex]]<rank[array[rindex]])tmp[index++]=array[lindex++];
                    else tmp[index++]=array[rindex++];
                }
            }else{
                if(lindex<c)tmp[index++]=array[lindex++];
                else tmp[index++]=array[rindex++];
            }
        }
        for(int i=l;i<r;++i){
            array[i] = tmp[i-l];
        }
    }
}



/**
 * Z-algorithm
 * presize[i] : S, S[i:]の共通接頭辞サイズ
 */
class Zalgo{
	String s;
	int[] presize;

	Zalgo(String s){
		this.s=s;
		presize = new int[s.length()];
		presize[0] = s.length();
		int index=1, size=0;
		while(index<s.length()){
			while(index+size<s.length() && s.charAt(size)==s.charAt(index+size))++size;
			presize[index]=size;
			if(size==0){
				++index;
				continue;
			}
			int k=1;
			while(k+index < s.length() && k+presize[k]<size){
				presize[index+k] = presize[k]; ++k;
			}
			index+=k;size-=k;
		}
	}
}




class Main{
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        String s = scan.next();
        SuffixArray sarray = new SuffixArray(s);
        int n = s.length();
        long sigma[] = new long[n+1];
        sigma[0]=0;
        for(int i=1;i<=n;++i)sigma[i]=sigma[i-1]+(long)i;
        long ans = 0;
        for(int i=1;i<=n;++i)ans += sigma[n-sarray.sa[i]] - (long)sigma[sarray.lcp[i]];
        System.out.println(ans);
    }
}
