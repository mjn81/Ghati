import java.lang.management.BufferPoolMXBean;
import java.util.Scanner;

public class Ghati {
    private static BigNumber[] results;
    private static Polynomial[][] pol;
    private static char [] polyOp;
    private static BigNumber [] big;
    private static String [] bigOp;
    static int n ,m;
    static BigNumber x ,target;
    public static void main(String[] args) {
        init();

        calculatePolynomialOperations();
        calculateBigNumberOperations();

        sortResults();

        int index = findTarget();
        System.out.println(index);
    }

    private static void init() {
        Scanner input = new Scanner(System.in);
        n = input.nextInt();
        input.nextLine();
        String [] inPoly = new String[n];
        for (int i = 0; i < n; i++) {
            inPoly[i] = input.nextLine();
        }
        m = input.nextInt();
        input.nextLine();
        String [] inBig = new String[m];
        for (int i = 0; i < m; i++) {
            inBig[i] = input.nextLine();
        }
        x = new BigNumber(input.nextLine());
        target = new BigNumber(input.nextLine());

        pol = new Polynomial[n][2];
        polyOp = new char[n];
        for (int i = 0; i < n; i++) {
            var in = inPoly[i];
            splitPoly(in, i);
        }

        big = new BigNumber[m];
        bigOp = new String[m];
        for (int i = 0; i < m; i++) {
            var res = inBig[i].split(" ");
            big[i] = new BigNumber(res[0]);
            bigOp[i] = res[1];
        }
            results = new BigNumber[n+m];
    }

    private static void calculatePolynomialOperations() {
        for (int i = 0; i < n; i++) {
            switch (polyOp[i]){
                case '+':
                    results[i] =  pol[i][0].plus(pol[i][1]).toBigNumber(x);
                    break;
                case '-':
                    results[i] =  pol[i][0].minus(pol[i][1]).toBigNumber(x);
                    break;
                case '*':
                    results[i] =  pol[i][0].times(pol[i][1]).toBigNumber(x);
                    break;
            }
        }
    }

    private static void calculateBigNumberOperations() {
        for (int i = n; i < n+m; i++) {
            switch (bigOp[i-n]){
                case "++":
                    results[i] =  big[i-n].increase();
                    break;
                case "--":
                    results[i] =  big[i-n].decrease();
                    break;
                case "R":
                    results[i] =  big[i-n].shiftRight();
                    break;
                case "L":
                    results[i] =  big[i-n].shiftLeft();
                    break;
            }
        }
    }

    private static void sortResults() {
        for (int i = 0; i < n+m; i++) {
            for (int j = i+1; j < n + m; j++) {
                if (results[i].compareTo(results[j])>0){
                    BigNumber tmp = results[i];
                    results[i] = results[j];
                    results[j] = tmp;
                }
            }
        }
    }

    private static int findTarget() {
        int l = 0, r = n+m-1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (results[mid].equals(target))
                return mid;
            if (results[mid].compareTo(target)<0)
                l = mid + 1;
            else
                r = mid - 1;
        }
        return -1;
    }


    private static void splitPoly(String in , int row){
        /// with regex
//        var polyStr =in.split("\\)[+,\\-,\\*]\\(");
//        var op = in.replaceAll("[()]" , "").split("\\dx\\^\\d");
//        int c=0;
//        for (var s : polyStr) {
//            var onePoly = s.replaceAll("[()]" ,"").split("\\+");
//            pol[row][c] = new Polynomial();
//            for (String value : onePoly) {
//                var res = value.split("x\\^");
//                pol[row][c].add(new BigNumber(res[0]), new BigNumber(res[1]));
//            }
//            c++;
//        }
        /// without regex
        var str = in.replace("x^" , " ");

        boolean isp = false ,c = true , isComp = false ,isInit = false;
        var counter = 0;
        BigNumber coef = null, expo = null;
        StringBuilder dig = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i)=='('){
                isp=true;
                isInit = false;
            }
            if (!isp&&str.charAt(i)=='+'||str.charAt(i)=='*'||str.charAt(i)=='-'){
                polyOp[row] = str.charAt(i);
                continue;
            }
            if(isp){
                if (str.charAt(i)<=57 && str.charAt(i)>=48){
                    dig.append(str.charAt(i));
                    isComp = false;
                }else if (str.charAt(i)==' ' && !isComp) {
                    if (c) {
                        coef = new BigNumber(dig.toString());
                        c = false;
                    } else {
                        expo = new BigNumber(dig.toString());
                        if (!isInit){
                            pol[row][counter] = new Polynomial();
                            isInit = true;
                        }
                        pol[row][counter].add(coef, expo);
                        c = true;
                        isComp = true;
                    }
                    dig = new StringBuilder();
                }

            }
            if (str.charAt(i) == ')'){
                isp =false;
                if (!isInit)
                    pol[row][counter]= new Polynomial();
                expo = new BigNumber(dig.toString());
                pol[row][counter].add(coef, expo);
                c = true;
                isComp = false;
                dig = new StringBuilder();
                counter++;
            }
        }
//        var polyStr = in.split(" ");
//        var
//        for (var s : polyStr) {
//            System.out.println(s);
//        }
        // operator splitter
//        for (var s : op) {
//            if (s.equals("-")){
//                polyOp[row]= s;
//                break;
//            }else if (s.equals("*")){
//                polyOp[row] = s;
//                break;
//            }
//        }
    }


}
