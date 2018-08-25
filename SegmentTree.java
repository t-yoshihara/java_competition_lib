
class SegmentTree{
	int n;
	long[] data;

	SegmentTree(int _n){
		n=1;
		while(n<_n)n*=2;
		data = new long[n*2+1];
	}

	// [0, n)
	void add(int i, long x){
		i+=n;
		data[i]+=x;
		for(i/=2;i>0;i/=2)data[i]=data[2*i]+data[2*i+1];
	}

	long get(int a, int b){
		return get(a,b, 1, 0, n);
	}
	long get(int a, int b, int k, int l, int r){
		if(r<=a || b<=l)return 0;
		if(a<=l&&r<=b)return data[k];
		return get(a,b,2*k, l,(l+r)/2) + get(a,b,2*k+1,(l+r)/2,r);
	}
}
