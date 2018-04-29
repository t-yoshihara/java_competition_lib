
//ソート済み配列を渡すこと
class BinarySearch{
    int[] array;
    BinarySearch(int[] array){
        this.array=array;
    }
    int lower_bound(int key){
        int lower=-1;int upper=array.length;
        while(upper - lower>1){
            int center =(upper+lower)/2;
            if(array[center]>=key)upper=center;
            else lower=center;
        }
        return upper;
    }
    int upper_bound(int key){
        int lower=-1;int upper=array.length;
        while(upper-lower >1){
            int center=(upper+lower)/2;
            if(array[center]>key)upper=center;
            else lower=center;
        }
        return upper;
    }
}