package domain;

import java.util.*;

public class LR0Item {
    private String nonTerminal;
    private int dot;
    private List<String> content;

    public LR0Item(String nonTerminal, int dot, List<String> content){
        this.nonTerminal = nonTerminal;
        this.dot = dot;
        this.content = content;
    }

    public String getNonTerminal() {
        return nonTerminal;
    }

    public int getDot() {
        return dot;
    }

    public List<String> getContent() {
        return content;
    }
}
