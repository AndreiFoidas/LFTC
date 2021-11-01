package domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class FiniteAutomaton {
    private List<String> states, alphabet, finalStates;
    private String initialState;
    private Map<AbstractMap.SimpleEntry<String, String>, List<String>> transitions;

    public FiniteAutomaton(String filename){
        this.states = new ArrayList<>();
        this.alphabet = new ArrayList<>();
        this.finalStates = new ArrayList<>();
        this.transitions = new HashMap<>();

        readFromFile(filename);
    }

    public FiniteAutomaton(List<String> states, List<String> alphabet, List<String> finalStates, String initialState, Map<AbstractMap.SimpleEntry<String, String>, List<String>> transitions) {
        this.states = states;
        this.alphabet = alphabet;
        this.finalStates = finalStates;
        this.initialState = initialState;
        this.transitions = transitions;
    }

    public void readFromFile(String fileName){
        try{
            File file = new File(fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line = br.readLine();
            if (line != null){
                this.states.addAll(Arrays.asList(line.split(" ")));
            }
            line = br.readLine();
            if (line != null){
                this.alphabet.addAll(Arrays.asList(line.split(" ")));
            }
            line = br.readLine();
            if (line != null){
                this.initialState = line.strip();
            }
            line = br.readLine();
            if (line != null){
                this.finalStates.addAll(Arrays.asList(line.split(" ")));
            }
            line = br.readLine();
            while (line != null){
                String[] tokens = line.split(" ");
                AbstractMap.SimpleEntry<String, String> key = new AbstractMap.SimpleEntry<>(tokens[0], tokens[1]);
                if(this.transitions.containsKey(key)){
                    this.transitions.get(key).add(tokens[2]);
                }
                else{
                    ArrayList<String> value = new ArrayList<>(Collections.singletonList(tokens[2]));
                    this.transitions.put(key, value);
                }
                line = br.readLine();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isDeterministic(){
        var keys = this.transitions.keySet();
        for (AbstractMap.SimpleEntry<String, String> key: keys){
            if (this.transitions.get(key).size() > 1)
                return false;
        }
        return true;
    }

    public boolean isValid(){
        if (!this.states.contains(this.initialState))
            return false;
        for (String fState: this.finalStates){
            if(!this.states.contains(fState))
                return false;
        }
        var keys = this.transitions.keySet();
        for (AbstractMap.SimpleEntry<String, String> key: keys){
            if (!this.states.contains(key.getKey()))
                return false;
            if (!this.states.contains(key.getValue()))
                return false;
            List<String> list = this.transitions.get(key);
            for(String token:list){
                if(!this.states.contains(token))
                    return false;
            }
        }
        return true;
    }

    public boolean acceptsSequence(String sequence){
        return true;
    }

    public List<String> getStates() {
        return this.states;
    }

    public List<String> getAlphabet() {
        return this.alphabet;
    }

    public List<String> getFinalStates() {
        return this.finalStates;
    }

    public String getInitialState() {
        return this.initialState;
    }

    public Map<AbstractMap.SimpleEntry<String, String>, List<String>> getTransitions() {
        return this.transitions;
    }

    @Override
    public String toString() {
        return "FiniteAutomaton:" +
                "\nstates=" + this.states +
                "\nalphabet=" + this.alphabet +
                "\nfinalStates=" + this.finalStates +
                "\ninitialState='" + this.initialState + '\'' +
                "\ntransitions=" + this.transitions;
    }
}
