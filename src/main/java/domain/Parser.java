package domain;

import java.util.*;

public class Parser {
    private Grammar grammar;

    public Parser(Grammar grammar){
        this.grammar = grammar;
    }

    public List<LR0Item> goTo(List<LR0Item> state, String symbol){
        return new ArrayList<>();
    }

    public List<LR0Item> closure(List<LR0Item> items){
        List<LR0Item> closure = new ArrayList<>(items);

        int index = 0;
        while (index < closure.size()){
            LR0Item item = closure.get(index);
            int dotPosition = item.getDot();
            if (dotPosition < item.getContent().size()) {
                String symbol = item.getContent().get(dotPosition);
                if (this.grammar.getNonTerminals().contains(symbol)) {
                    for (List<String> production : this.grammar.getProductionForNonTerminal(symbol)) {
                        LR0Item newItem = new LR0Item(symbol, 0, production);
                        if (!closure.contains(newItem))
                            closure.add(newItem);
                    }
                }
            }

            index++;
        }

        return closure;
    }

    public List<List<LR0Item>> canonicalCollection(){
        List<List<LR0Item>> collection = new ArrayList<>();

        var rez = this.grammar.getProductionForNonTerminal(this.grammar.getStartingSymbol());
        List<String> content = rez.get(0);
        LR0Item startingItem = new LR0Item(this.grammar.getStartingSymbol(), 0, content);

        List<LR0Item> startingState = new ArrayList<>();
        startingState.add(startingItem);
        List<LR0Item> s0 = closure(startingState);
        collection.add(s0);

        System.out.println("s0 = " + s0);

        return collection;
    }
}
