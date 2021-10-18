package domain;

import java.util.HashMap;

public class Scanner {

    private SymbolTable symbolTable = new SymbolTable(50);
    private ProgramInternalForm pif = new ProgramInternalForm();
    private Codification codes = new Codification();

    public Scanner(){
    }

    public void Scanning(){
        while(true) {
            String token = " "; //USE JAVA TOKANIZER (careful la operatori compusi)
            if (codes.getSeparators().contains(token) || codes.getOperators().contains(token)
                || codes.getReservedWords().contains(token)){
                this.pif.add(codes.getCodes().get(token), new Pair(0, 0));
            }
            else{
                if (token.length() != 0) {//will check for identifier and constants
                    Pair index = this.symbolTable.search(token);
                    this.pif.add(0, index);
                }
                else{
                    throw new RuntimeException("Lexical error!");
                }
            }
        }
    }

}
