package domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Grammar {
    String fileName;
    private List<String> terminals;
    private List<String> nonTerminals;
    private String startingSymbol;
    private Map<List<String>, List<List<String>>> productions;

    public Grammar(String fileName){
        this.terminals = new ArrayList<>();
        this.nonTerminals = new ArrayList<>();
        this.productions = new HashMap<>();
        this.fileName = fileName;

        readGrammar();
    }

    public void readGrammar(){

        try{
            File file = new File(fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line = br.readLine();
            if (line != null){
                this.terminals.addAll(Arrays.asList(line.split(" ")));
            }
            line = br.readLine();
            if (line != null){
                this.nonTerminals.addAll(Arrays.asList(line.split(" ")));
            }
            line = br.readLine();
            if (line != null){
                this.startingSymbol = line.strip();
            }

            line = br.readLine();
            while (line != null){
                String[] tokens = line.split("->");

                List<List<String>> rules = new ArrayList<>();
                List<String> allRules = List.of(tokens[1].split(("\\|")));

                for (String rule: allRules){
                    rules.add(Arrays.asList(rule.split(" ")));
                }

                List<String> symbols = List.of(tokens[0].split(" "));
                productions.put(symbols, rules);

                line = br.readLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Map<List<String>, List<List<String>>> getProductionForNonTerminal(String nonTerminal){
        Map<List<String>, List<List<String>>> productions = new HashMap<>();
        Set<List<String>> keys = this.productions.keySet();
        for(List<String> key: keys){
            if(key.equals(nonTerminal)){
                productions.put(key, this.productions.get(key));
            }
        }

        return productions;
    }

    public String getFileName() {
        return fileName;
    }

    public List<String> getTerminals() {
        return terminals;
    }

    public List<String> getNonTerminals() {
        return nonTerminals;
    }

    public String getStartingSymbol() {
        return startingSymbol;
    }

    public Map<List<String>, List<List<String>>> getProductions() {
        return productions;
    }

    @Override
    public String toString() {
        return "Grammar" +
                "\nfileName='" + fileName + '\'' +
                "\nterminals=" + terminals +
                "\nnonTerminals=" + nonTerminals +
                "\nstartingSymbol='" + startingSymbol + '\'' +
                "\nproductions=" + productions;
    }
}
