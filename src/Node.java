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
        // Should throw exception but for now it works :)
        if (!(other instanceof Node)){
            return false;
        }
        Node tmp = (Node) other;
        return expo.equals(tmp.expo);
    }
}
