class Next_permutation{

    // use comprable data such as Integer(not int).
    static boolean next_permutation(Comparable[] comp){
        int lastIndex = comp.length-2;
        while(lastIndex>=0 && comp[lastIndex].compareTo(comp[lastIndex+1])>=0)--lastIndex;
        if(lastIndex<0)return false;
        int swapIndex = comp.length-1;
        while(comp[lastIndex].compareTo(comp[swapIndex])>=0)swapIndex--;
        Comparable tmp = comp[lastIndex];
        comp[lastIndex++] = comp[swapIndex];
        comp[swapIndex] = tmp;
        swapIndex = comp.length-1;
        while(lastIndex<swapIndex){
            tmp = comp[lastIndex];
            comp[lastIndex] = comp[swapIndex];
            comp[swapIndex] = tmp;
            ++lastIndex;--swapIndex;
        }
        return true;
    }

    public static void main(String[] args){
        Character[] a = {'0','1','2','3','4'};
        int cnt = 0;
        do{
            ++cnt;
            System.out.println(a[0]+" "+a[1]+" "+a[2]+" "+a[3]+" "+a[4]);
        }while(next_permutation(a));
        System.out.println(cnt);
    }
}