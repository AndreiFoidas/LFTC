package domain;

import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Scanner {

    private final SymbolTable symbolTable = new SymbolTable(50);
    private final ProgramInternalForm pif = new ProgramInternalForm();
    private final Codification codification = new Codification();

    public Scanner() {
    }

    public void scanFile(String fileName) {
        try {
            File file = new File(fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            int i=0;
            String line;
            boolean lexicallyCorrect = true;
            while((line = br.readLine()) != null){
                List<String> tokenList = this.tokenizeLine(line);
                System.out.println(tokenList);
                boolean result = this.scanLine(tokenList, i);
                lexicallyCorrect = lexicallyCorrect && result;
                i++;
            }
            if(lexicallyCorrect)
                System.out.println("Program is correct!");
            else
                System.out.println("Program has lexical errors!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> tokenizeLine(String line){
        List<String> tokens = new ArrayList<>();

        int i = 0;
        while (i < line.length()){
            if (line.charAt(i) == '"'){
                StringBuilder stringToken = new StringBuilder("\"");
                i++;
                while (i < line.length() && line.charAt(i) != '"'){
                    stringToken.append(line.charAt(i));
                    i++;
                }
                stringToken.append("\"");
                i++;

                tokens.add(stringToken.toString());
            }
            else if (line.charAt(i) == '\''){
                StringBuilder charToken = new StringBuilder("'");
                i++;
                while (i < line.length() && line.charAt(i) != '\''){
                    charToken.append(line.charAt(i));
                    i++;
                }
                charToken.append("'");
                i++;

                tokens.add(charToken.toString());
            }
            else if (this.isSeparator(String.valueOf(line.charAt(i)))){
                if (!(line.charAt(i) == ' ') && !(line.charAt(i) == '\t') && !(line.charAt(i) == '\n')){
                    tokens.add(String.valueOf(line.charAt(i)));
                }
                i++;
            }
            else if (this.isOperator(String.valueOf(line.charAt(i)))){
                if(this.isOperator(String.valueOf(line.charAt(i + 1)))){
                    tokens.add(line.charAt(i) + String.valueOf(line.charAt(i + 1)));
                    i = i + 2;
                }
                else {
                    tokens.add(String.valueOf(line.charAt(i)));
                    i++;
                }
            }
            else {
                // other tokens like reserved, constants and identifiers
                StringBuilder token = new StringBuilder(String.valueOf(line.charAt(i)));
                i++;
                while (i < line.length() && !this.isSeparator(String.valueOf(line.charAt(i)))
                        && !this.isOperator(String.valueOf(line.charAt(i)))){
                    token.append(line.charAt(i));
                    i++;
                }
                tokens.add(token.toString());
            }
        }

        return tokens;
    }

    public boolean scanLine(List<String> tokens, int line) {
        boolean lexicallyCorrect = true;
        ArrayList<String> specialCase = new ArrayList<>(Arrays.asList("(", "=", "==", "<", ">", "<=", ">=", "!="));
        String lastToken = "";
        for(int i=0; i < tokens.size(); ++i){
            String token = tokens.get(i);
            if (this.isIdentifier(token)){
                int code = this.codification.getCodes().get("identifier"); // 0
                this.symbolTable.add(token);
                Pair position = this.symbolTable.search(token);
                this.pif.add(code, position);
            }
            else if (this.isConstant(token)){
                int code = this.codification.getCodes().get("constant"); // 1
                this.pif.add(code, new Pair(-1, -1));
            }
            else if ((token.equals("-") || token.equals("+")) && (this.isNumber(tokens.get(i + 1))) &&
                    specialCase.contains(lastToken)){
                token += tokens.get(i + 1);
                i++;
                if (!token.equals("-0")){
                    int code = this.codification.getCodes().get("constant"); // 1
                    this.pif.add(code, new Pair(-1, -1));
                }
                else {
                    System.out.println("Error at line: " + line + ". Invalid token " + token);
                    lexicallyCorrect = false;
                }
            }
            else if (this.isOperator(token) || this.isSeparator(token) || this.isReservedWord(token)){
                int code = this.codification.getCodes().get(token);
                this.pif.add(code, new Pair(-1, -1));
            }
            else {
                System.out.println("Error at line: " + line + ". Invalid token " + token);
                lexicallyCorrect = false;
            }
            lastToken = token;
        }

        return lexicallyCorrect;
    }

    public boolean isSeparator(String token) {
        return this.codification.getSeparators().contains(token);
    }

    public boolean isOperator(String token){
        return this.codification.getOperators().contains(token);
    }

    public boolean isReservedWord(String token){
        return this.codification.getReservedWords().contains(token);
    }

    public boolean isIdentifier(String token){
        String pattern = "^[a-zA-Z]([a-zA-Z0-9_]*$)";
        return token.matches(pattern);
    }

    public boolean isConstant(String token){
        String number = "^([1-9][0-9]*)|0$";
        String string = "^\"[a-zA-Z0-9_.:;,?!*' ]*\"$";
        String character = "^\'[a-zA-Z0-9_.:;,?!*\" ]\'$";
        return token.matches(number) || token.matches(string) || token.matches(character);
    }

    public boolean isNumber(String token){
        String number = "^([1-9][0-9]*)|0$";
        return token.matches(number);
    }
}
