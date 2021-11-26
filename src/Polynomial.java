class Polynomial {
    int start , finish;
    static int avail ;
    static Node [] arr;
    public  Polynomial (){
        start = avail;
    }
    public void add(BigNumber coefficient, BigNumber exponent){
        
    }

    public void remove(BigNumber coefficient, BigNumber exponent) {

    }

//    public Polynomial plus(Polynomial other) {
//        // TODO: your code ...
//    }
//
//    public Polynomial minus(Polynomial other) {
//        // TODO: your code ...
//    }
//
//    public Polynomial times(Polynomial other) {
//        // TODO: your code ...
//    }
//
//    public boolean isZero() {
//        // TODO: your code ...
//    }
//
//    public BigNumber getCoefficient(BigNumber exponent) {
//        // TODO: your code ...
//    }
//
//    public BigNumber getMaximumExponent() {
//        // TODO: your code ...
//    }

    private boolean exists(Node n){
        for (int i = start; i < finish; i++) {
            if (arr[i].equals(n)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        for (var i=0; i<avail;i++){
            res.append(arr[i].toString()).append(" ");
        }
        return res.toString();
    }


}
