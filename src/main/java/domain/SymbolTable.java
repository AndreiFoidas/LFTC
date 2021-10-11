package domain;

import java.util.ArrayList;
import java.util.LinkedList;

public class SymbolTable {
    private final int size;
    private ArrayList<LinkedList<String>> table;

    public SymbolTable(int size){
        this.size = size;

        this.table = new ArrayList<>();
        for (int i=0;i<size;++i){
            this.table.add(new LinkedList<>());
        }
    }

    public int hash(String key){
        int ASCIISum = 0;
        for(int i=0;i<key.length();++i){
            ASCIISum+=key.charAt(i);
        }
        return ASCIISum % this.size;
    }

    public boolean contains(String key){
        return this.table.get(this.hash(key)).contains(key);
    }

    public boolean add(String key){
        if (this.contains(key))
            return false;

        int hashValue = this.hash(key);
        this.table.get(hashValue).add(key);
        return true;
    }

    public Pair search(String key){
        if (this.contains(key)){
            int listPosition = this.hash(key);
            int listIndex = 0;
            for(String el:this.table.get(listPosition)) {
                if (!el.equals(key))
                    listIndex++;
                else
                    break;
            }

            return new Pair(listPosition, listIndex);
        }
        return null;
    }
}
