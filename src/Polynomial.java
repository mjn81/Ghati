class Polynomial {
    private int avail;
    private Node [] arr;
    public Polynomial(){
        arr = new Node[100];
        avail = 0;
    }
    public void add(BigNumber coefficient, BigNumber exponent){
        var tmp = new Node(coefficient, exponent);
        if (exists(tmp)){
            return;
        }
        arr[avail] = tmp;
        sort(this
        );
        avail++;
    }

    public void remove(BigNumber coefficient, BigNumber exponent) {
        var tmp = new Node(coefficient , exponent);
        if (!exists(tmp)){
            return;
        }
        Node [] nar = new Node[100];
        int c=0;
        for (int i = 0; i < avail; i++) {
            if (arr[i].equals(tmp)){
                continue;
            }
            nar[c] = arr[i];
            c++;
        }
        arr = nar;
        avail--;
    }

    public Polynomial plus(Polynomial other) {
        var res = new Polynomial();
        int i=0,j=0;
        while (i<avail && j<other.avail){
            if (arr[i].expo.compareTo(other.arr[j].expo)<0){
                res.add(other.arr[j].coef , other.arr[j].expo);
                j++;
            }
            else if (arr[i].expo.compareTo(other.arr[j].expo)>0){
                res.add(arr[i].coef , arr[i].expo);
                i++;
            }
            else{
                res.add(arr[i].coef.plus(other.arr[j].coef) , arr[i].expo);
                i++;
                j++;
            }
        }
        // solving original algorithms ( the one in the book) Bug
        var last = arr[avail-1].expo.compareTo(other.arr[other.avail-1].expo)>0 ? other.arr[other.avail-1] : arr[avail-1];
        res.add(last.coef , last.expo);
        return res;
    }

    public Polynomial minus(Polynomial other) {
        var res = new Polynomial();
        int i=0,j=0;
        while (i<avail && j<other.avail){
            if (arr[i].expo.compareTo(other.arr[j].expo)<0){
                res.add(other.arr[j].coef.times(BigNumber.MONE) , other.arr[j].expo);
                j++;
            }
            else if (arr[i].expo.compareTo(other.arr[j].expo)>0){
                res.add(arr[i].coef , arr[i].expo);
                i++;
            }
            else{
                res.add(arr[i].coef.minus(other.arr[j].coef) , arr[i].expo);
                i++;
                j++;
            }
        }
        // solving original algorithms ( the one in the book) Bug
        if (arr[avail-1].expo.compareTo(other.arr[other.avail-1].expo)>0){
            var last =  other.arr[other.avail-1];
            res.add(last.coef.times(BigNumber.MONE) , last.expo);
        }else{
            var last= arr[avail-1];
            res.add(last.coef , last.expo);
        }
        return res;
    }

    public Polynomial times(Polynomial other) {
        Polynomial res = new Polynomial();

        for (int i = 0; i < avail; i++) {
            for (int j = 0; j < other.avail; j++) {
                var n = new Node(arr[i].coef.times(other.arr[j].coef) , arr[i].expo.plus(other.arr[j].expo));
                if (res.exists(n)){
                    Polynomial tmp = new Polynomial();
                    tmp.add(n.coef , n.expo);
                    res = res.plus(tmp);
                }else{
                    res.add(n.coef , n.expo);
                }
            }
        }
        return res;
    }

    public boolean isZero() {
        return arr[0].coef.equals(BigNumber.ZERO);
    }

    public BigNumber getCoefficient(BigNumber exponent) {
        for (int i = 0; i < avail; i++) {
            if (arr[i].expo.equals(exponent)){
                return arr[i].coef;
            }
        }
        // just to get no error
        return BigNumber.ZERO;
    }

    public BigNumber getMaximumExponent() {
        return arr[0].expo;
    }

    private boolean exists(Node n){
        for (int i = 0; i < avail; i++) {
            if (arr[i].equals(n)){
                return true;
            }
        }
        return false;
    }

    private void sort(Polynomial p)
    {
        for (int i = 1; i <= avail; ++i) {
            var key = p.arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j].expo.compareTo(key.expo) < 0) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }


    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        for (var i=0; i<avail;i++){
            res.append(arr[i].toString()).append(" ");
        }
        return res.toString();
    }

    public BigNumber toBigNumber(BigNumber x){
        BigNumber res = BigNumber.ZERO.clone();
        for (int i = 0; i < avail; i++) {
            res = res.plus(arr[i].coef.times(x.toThePowerOf(arr[i].expo)));
        }
        return res;
    }

}
