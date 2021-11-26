public class Node {
    BigNumber coef;
    BigNumber expo;
    public Node(){
        coef = BigNumber.ZERO.clone();
        expo = BigNumber.ZERO.clone();
    }
    public Node(BigNumber c , BigNumber e){
        coef = c.clone();
        expo = e.clone();
    }

    @Override
    public String toString(){
        return coef+"x^"+expo;
    }

    @Override
    public boolean equals(Object other){
        return toString().equals(other.toString());
    }
}
