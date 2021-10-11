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

        System.out.println(ht.getPosition("a"));
        System.out.println(ht.getPosition("b"));
        System.out.println(ht.getPosition("d"));
        System.out.println(ht.getPosition("abc"));
        System.out.println(ht.getPosition("cba"));
    }


    public static void main(String[] args) {
        testSymbolTable();
    }
}
