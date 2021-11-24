package parser;

import java.util.*;

public class Parser {
    private final Grammar grammar;

    public Parser(Grammar grammar){
        this.grammar = grammar;
    }

    public List<LR0Item> goTo(List<LR0Item> state, String symbol){
        List<LR0Item> items = new ArrayList<>();
        for(LR0Item item: state){
            if(item.getDotPosition() < item.getContent().size() && item.getContent().get(item.getDotPosition()).equals(symbol)){
                items.add(item);
            }
        }

        for(int i = 0; i < items.size(); ++i){
            LR0Item item = items.get(i);
            LR0Item newItem = new LR0Item(item.getNonTerminal(), item.getDotPosition() + 1, item.getContent());
            items.set(i, newItem);
        }

        return this.closure(items);
    }

    public List<LR0Item> closure(List<LR0Item> items){
        List<LR0Item> closure = new ArrayList<>(items);

        int index = 0;
        while (index < closure.size()){
            LR0Item item = closure.get(index);
            int dotPosition = item.getDotPosition();
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
        LR0Item startingItem = new LR0Item("S'", 0, Collections.singletonList(this.grammar.getStartingSymbol()));

        List<LR0Item> startingState = new ArrayList<>();
        startingState.add(startingItem);
        List<LR0Item> s0 = closure(startingState);
        collection.add(s0);

        System.out.println("s0 = " + s0);

        int index = 0;
        while(index < collection.size()){
            List<LR0Item> state = collection.get(index);
            for (String symbol: this.grammar.getTerminalsAndNonTerminals()){
                List<LR0Item> result = this.goTo(state, symbol);
                if(!result.isEmpty() && !collection.contains(result)){

                    System.out.println("s" + collection.size() + " = goTo(s" + collection.indexOf(state) + ", " + symbol + ") = " + result);
                    collection.add(result);
                }
            }

            index++;
        }

        return collection;
    }
}
