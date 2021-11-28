class BigNumber {
    public static final BigNumber ZERO = new BigNumber("0");
    public static final BigNumber ONE = new BigNumber("1");
    public static final BigNumber MONE = new BigNumber("-1");

    private int [] digits = new int[20];
    private boolean isNegative = false;

    public BigNumber(String s){
        if (s.charAt(0)=='-') {
            isNegative=true;
            s = s.substring(1);
        } else if (s.charAt(0)=='+'){
            s = s.substring(1);
        }
        var chars = s.substring(Math.max(s.length() -20,0)).toCharArray();
        for (int i = chars.length-1; i >=0 ; i--) {
            digits[ (chars.length-1) - i ] = chars[i]-48;
        }
    }

    public BigNumber increase() {
        return plus(ONE);
    }

    public BigNumber decrease() {
        return minus(ONE);
    }

    public BigNumber shiftRight() {
        return shiftRight(1);
    }

    public BigNumber shiftLeft() {
        return shiftLeft(1);
    }

    public BigNumber shiftRight(int amount) {
        var res = clone();
        for (int i = amount; i <res.digits.length ; i++) {
            res.digits[ i - amount ] = res.digits[i];
        }
        for (int i = res.digits.length-amount; i< res.digits.length;i++){
            res.digits[i] = 0;
        }
        return res;
    }

    public BigNumber shiftLeft(int amount) {
        return new BigNumber(this + "0".repeat(amount));
    }

    public BigNumber plus(BigNumber other) {
        if (!isNegative && other.isNegative){
            var cl = other.clone();
            cl.isNegative = false;
            return this.minus(cl);
        }
        if (isNegative && !other.isNegative){
            var cl = this.clone();
            cl.isNegative = false;
            return other.minus(cl);
        }
        if (isNegative && other.isNegative){
            var cl1 = clone();
            var cl2 = other.clone();
            cl1.isNegative = cl2.isNegative = false;
            var res = cl1.plus(cl2);
            res.isNegative = true;
            return res;
        }

        var res = clone();
        var c = false;
        for (int i = 0; i < res.digits.length; i++) {
           var sum = digits[i] + other.digits[i] + (c ? 1 : 0);
           res.digits[i] = sum%10;
           c = sum>9;
        }
        return res;
    }

    public BigNumber minus(BigNumber other) {

        if (isNegative && !other.isNegative){
            var cl = clone();
            cl.isNegative = false;
            var res = cl.plus(other);
            res.isNegative = true;
            return res;
        }
        if (!isNegative && other.isNegative){
            var cl = other.clone();
            cl.isNegative = false;
            return cl.plus(this);
        }
        if (isNegative && other.isNegative){
            var cl = clone();
            var cl2 = other.clone();

            cl.isNegative =false;
            cl2.isNegative = false;
            return cl2.minus(cl);
        }

        var com = compareTo(other);

        if (com == 0){
            return ZERO;
        }
        if(com <0){
            var c = other.clone();
            var res = c.minus(this);
            res.isNegative = !res.isNegative;
            return res;
        }
        var res = ZERO.clone();
        for (int i = 0; i < digits.length; i++) {
            var m = digits[i] - other.digits[i];
            if (m<0){
                m+=10;
                digits[i+1]--;
            }
            res.digits[i] = m;
        }
        return res;
    }

    public BigNumber times(BigNumber other) {
        var res = ZERO.clone();
        var temp = ZERO.clone();
        var c = 0;
        for (int j = 0; j < other.digits.length; j++) {
            if (other.digits[j]==0)
                continue;
            for (int i = 0; i < digits.length; i++) {
                var t = other.digits[j]*digits[i]+c;
                temp.digits[i] = t%10;
                c = t/10;
            }
            c=0;
            res = res.plus(temp.shiftLeft(j));
        }
        res.isNegative = isNegative != other.isNegative;
        if (equals(ZERO)||other.equals(ZERO)){
            return ZERO;
        }
        return res;
    }

    public BigNumber toThePowerOf(BigNumber other) {
        if (other.equals(ZERO)){
            return ONE;
        }
        if (equals(ZERO)){
            return ZERO;
        }
        var res = this.clone();
        var counter = other.clone().decrease();
        while (!(counter.toString().equals("0"))){
            res = times(res);
            counter = counter.decrease();
        }
        return res;
    }

    @Override
    public BigNumber clone(){
        return new BigNumber(toString());
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder(toStringZero());
        res = new StringBuilder(res.toString().replace("0", " "));
        res.append('X');
        res = new StringBuilder(res.toString().trim());
        res = new StringBuilder(res.toString().replace(" ", "0"));
        var ws = res.length()==1 ? "0" : res.substring(0 , res.length()-1);
        return (isNegative ? "-" : "") + ws;
    }

    @Override
    public boolean equals(Object other){
        // Should throw exception but for now it works :)
        if (!(other instanceof BigNumber)){
            return false;
        }
        return toString().equals(other.toString());
    }

    public int compareTo(BigNumber other){
        return toStringZero().compareTo(other.toStringZero());
    }

    private String toStringZero(){
        StringBuilder res = new StringBuilder();
        for (var i : digits) {
            res.append(i);
        }
        res.reverse();
        return res.toString();
    }

}