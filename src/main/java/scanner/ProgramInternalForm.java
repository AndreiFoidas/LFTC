package scanner;

import domain.Pair;

import java.util.HashMap;

public class ProgramInternalForm {
    private final HashMap<Integer, Pair> pif = new HashMap<>();

    public ProgramInternalForm() {}

    public void add(int code, Pair position){
        pif.put(code, position);
    }

    public HashMap<Integer, Pair> getPIF() {
        return this.pif;
    }

    @Override
    public String toString() {
        return "ProgramInternalForm{" +
                "pif=" + pif +
                '}';
    }
}
