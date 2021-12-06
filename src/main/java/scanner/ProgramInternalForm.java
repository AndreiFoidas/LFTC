package scanner;

import domain.Pair;

import java.util.HashMap;

public class ProgramInternalForm {
    private final HashMap<Integer, Pair<Integer, Integer>> pif = new HashMap<>();

    public ProgramInternalForm() {}

    public void add(int code, Pair<Integer, Integer> position){
        pif.put(code, position);
    }

    public HashMap<Integer, Pair<Integer, Integer>> getPIF() {
        return this.pif;
    }

    @Override
    public String toString() {
        return "ProgramInternalForm{" +
                "pif=" + pif +
                '}';
    }
}
