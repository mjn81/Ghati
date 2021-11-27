
public class Ghati {
    public static void main(String[] args) {
        BigNumber x = new BigNumber("2");
        BigNumber x2 = new BigNumber("0");
        BigNumber x3 = new BigNumber("3");
        BigNumber x4 = new BigNumber("0");
        BigNumber x5 = new BigNumber("7");
        BigNumber x6 = new BigNumber("10");
        Polynomial p = new Polynomial();
        p.add(x , x5);
//        p.add(x3, x4);
//        p.add(x, x6);
        Polynomial b = new Polynomial();
        b.add(x2 , x6);
//        b.add(x , x3);
//        b.add(x6 , x5);
        Polynomial res = p.minus(b);

        System.out.println(p);
        System.out.println(b);
        System.out.println(res);
        System.out.println(b.isZero());
        System.out.println(p.isZero());
    }

}
