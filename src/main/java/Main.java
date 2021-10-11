import domain.HashTable;

public class Main {
    public static void main(String[] args) {
        HashTable ht = new HashTable(20);
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
        System.out.println(ht.getPosition("abc"));
        System.out.println(ht.getPosition("cba"));
    }
}
