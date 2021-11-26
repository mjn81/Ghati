
public class Ghati {
    public static void main(String[] args) {
        BigNumber x = new BigNumber("2");
        BigNumber x2 = new BigNumber("0");
        BigNumber x3 = new BigNumber("3");
        BigNumber x4 = new BigNumber("0");
        Polynomial p = new Polynomial();
        p.add(x , x2);
        p.add(x3, x4);
        p.remove(x , x2);
        System.out.println(p);
    }

}
