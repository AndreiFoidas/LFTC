import domain.Scanner;
import domain.SymbolTable;

public class Main {

    static void testSymbolTable(){
        SymbolTable ht = new SymbolTable(20);
        ht.add("a");
        ht.add("b");
        ht.add("c");
        ht.add("a");
        ht.add("abc");
        ht.add("cba");

        System.out.println(ht.contains("a"));
        System.out.println(ht.contains("b"));
        System.out.println(ht.contains("abc"));
        System.out.println(ht.contains("d"));
        System.out.println(ht.contains("e"));

        System.out.println(ht.search("a"));
        System.out.println(ht.search("b"));
        System.out.println(ht.search("d"));
        System.out.println(ht.search("abc"));
        System.out.println(ht.search("cba"));
    }


    public static void main(String[] args) {
        //testSymbolTable();
        Scanner s = new Scanner();
        s.scanFile("src/files/p1.txt");
        System.out.println();
        Scanner s1 = new Scanner();
        s1.scanFile("src/files/p2.txt");
        System.out.println();
        Scanner s2 = new Scanner();
        s2.scanFile("src/files/p3.txt");
        System.out.println();
        Scanner s3 = new Scanner();
        s3.scanFile("src/files/p1err.txt");
        System.out.println();
    }
}
