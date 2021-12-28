package Classes;

import TpExeption.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Interpreteur {
    //---------------------------*** Attributs ***---------------------------//
    private TableSymbole tableSymbole;
    private LigneCommande ligneCommande;
    private String input;
    //-----------------------------------------------------------------------//
    //---------------------------*** Constructeur ***------------------------//
    public Interpreteur() { this.tableSymbole = new TableSymbole(); }
    //-----------------------------------------------------------------------//
    //----------------------------*** Setters ***----------------------------//
    public void setInput(String input) { this.input = input; }
    //-----------------------------------------------------------------------//
    //----------------------------*** Methodes ***----------------------------//
    private String flattenExpression(String input) throws ParentheseException { // transformer a une expression sans imbriquation
        Queue<Character> file = new LinkedList<>(); // file d'attente
        String result = new String();
        if (input.contains("(")) {
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == '(') {
                    int checkParentheses = 0, indexParentheseFermante = 0;
                    for (indexParentheseFermante = i; indexParentheseFermante < input.length();
                         indexParentheseFermante++) {
                        if (input.charAt(indexParentheseFermante) == '(') {
                            checkParentheses++;
                        } else if(input.charAt(indexParentheseFermante) == ')'){
                            checkParentheses--;
                        }
                        if (checkParentheses == 0) {
                            break;
                        }
                    }
                    if (checkParentheses > 0) {
                        throw new ParentheseException("parenthese fermante manquante!");
                    } else if (checkParentheses == 0) {
                        String tmp = new String();
                        tmp += flattenExpression(input.substring(i + 1, indexParentheseFermante));
                        i = indexParentheseFermante;
                        for (Character cha : tmp.toCharArray()) {
                            file.add(cha);
                        }
                    } else {
                        throw new ParentheseException("parenthese ouvrante manquante!");
                    }
                } else {
                    file.add(input.charAt(i));
                }
            }
            String expr = new String();
            while (!file.isEmpty()) {
                expr += file.remove();
            }
            Double res = buildExpression(expr).evaluer();
            result += res.toString();
            return result;
        } else if (input.contains(")")) {
            throw new ParentheseException("parenthese ouvrante manquante!");
        } else {
            Double res = buildExpression(input).evaluer();
            result += res.toString();
            return result;
        }
    }

    private Expression buildExpression(String expr) throws LangException {
        ArrayList<Terme> termes = new ArrayList<>();
        String[] posTermes;
        if (expr.contains("(") || expr.contains(")")) {
            posTermes = flattenExpression(expr).trim().split("\\+");
        } else {
            posTermes = expr.trim().split("\\+");
        }
            for (String pos : posTermes) {
                String[] negTermes;
                if (!(pos.toLowerCase().contains("abs") ||
                        pos.toLowerCase().contains("cos") ||
                        pos.toLowerCase().contains("log") ||
                        pos.toLowerCase().contains("sin") ||
                        pos.toLowerCase().contains("sqrt") ||
                        pos.toLowerCase().contains("tan"))) { // si le terme ne contient pas des fonctions
                    negTermes = pos.trim().split("-");
                } else {
                    negTermes = pos.trim().split("\\+");
                }
                if (pos.startsWith("-")) {
                    ArrayList<String> proxyNegTermes = new ArrayList<>();
                    for (int i = 1; i < negTermes.length; i++) {
                        proxyNegTermes.add(negTermes[i]);
                    }
                    for (String neg : proxyNegTermes) {
                        Terme terme = buildTerme(neg);
                        terme.setNegative(true);
                        termes.add(terme);
                    }
                } else {
                    termes.add(buildTerme(negTermes[0]));
                    for (int i = 1; i < negTermes.length; i++) {
                        Terme terme = buildTerme(negTermes[i]);
                        terme.setNegative(true);
                        termes.add(terme);
                    }
                }
            }
        return new Expression(termes);
    }

    private Terme buildTerme(String term) throws LangException {
        ArrayList<Facteur> facts = new ArrayList<>();
        String[] mulFacteurs = term.trim().split("\\*");
        for (String mul : mulFacteurs) {
            String[] divFacteurs = mul.trim().split("/");
            if (divFacteurs.length > 1) {
                facts.add(buildFacteur(divFacteurs[0]));
                for (int i = 1; i < divFacteurs.length; i++) {
                    Facteur fact = buildFacteur(divFacteurs[i]);
                    fact.setInversible(true);
                    facts.add(fact);
                }
            } else {
                Facteur fact = buildFacteur(mul);
                facts.add(fact);
            }
        }
        return new Terme(false, facts);
    }

    private Facteur buildFacteur(String fact) throws LangException {
        ArrayList<Element> elems = new ArrayList<>();
        String[] elements = fact.trim().split("\\^");
        for (String element : elements) {
            elems.add(buildElement(element.trim()));
        }
        return new Facteur(false, elems);
    }

    private Element buildElement(String elem) throws LangException {
        if (elem.matches("^\\d+[.]?\\d*$")) { // nombre
            Nombre nombre = new Nombre();
            nombre.setValeur(Double.parseDouble(elem.trim()));
            return nombre;
        } else if (elem.toLowerCase().contains("abs") ||
                    elem.toLowerCase().contains("cos") ||
                    elem.toLowerCase().contains("log") ||
                    elem.toLowerCase().contains("sin") ||
                    elem.toLowerCase().contains("sqrt") ||
                    elem.toLowerCase().contains("tan")) { // fonction
            if (elem.toLowerCase().startsWith("abs")) {
                String param = elem.substring(3);
                Fonction func = new Fonction(CodeFonction.ABS, buildExpression(param));
                this.tableSymbole.addFonction("abs", CodeFonction.ABS);
                return func;
            } else if (elem.toLowerCase().startsWith("cos")) {
                String param = elem.substring(3);
                Fonction func = new Fonction(CodeFonction.COS, buildExpression(param));
                this.tableSymbole.addFonction("cos", CodeFonction.COS);
                return func;
            } else if (elem.toLowerCase().startsWith("log")) {
                String param = elem.substring(3);
                Fonction func = new Fonction(CodeFonction.LOG, buildExpression(param));
                this.tableSymbole.addFonction("log", CodeFonction.LOG);
                return func;
            } else if (elem.toLowerCase().startsWith("sin")) {
                String param = elem.substring(3);
                Fonction func = new Fonction(CodeFonction.SIN, buildExpression(param));
                this.tableSymbole.addFonction("sin", CodeFonction.SIN);
                return func;
            } else if (elem.toLowerCase().startsWith("sqrt")) {
                String param = elem.substring(4);
                Fonction func = new Fonction(CodeFonction.SQRT, buildExpression(param));
                this.tableSymbole.addFonction("sqrt", CodeFonction.SQRT);
                return func;
            } else if (elem.toLowerCase().startsWith("tan")) {
                String param = elem.substring(3);
                Fonction func = new Fonction(CodeFonction.TAN, buildExpression(param));
                this.tableSymbole.addFonction("tan", CodeFonction.TAN);
                return func;
            } else {
                throw new NomFonctionErroneException("nom de fonction errone!");
            }
        } else if (elem.matches("^\\(.*\\)$")) { // expression entre `()`
            String subExpr = elem.substring(1, elem.lastIndexOf(")"));
            return buildExpression(subExpr);
        } else if (elem.matches("^[A-Za-z_][A-Za-z0-9_]*$")) { // variable
            Variable variable = new Variable();
            variable.setName(elem);
            variable.setValeur(this.tableSymbole.getVariable(elem));
            return variable;
        } else {
            throw new ExpressionErroneeException("expression erronee!");
        }
    }
    //---------------------------------
    private boolean checkSyntaxe() throws LangException {
        boolean err = false;
        String exp;
        if (input.toLowerCase().startsWith("let ")) {
            int exprIndex = input.indexOf("=", 0) + 1;
            Variable variable = new Variable();
            String varName = input.substring("let ".length(), exprIndex - 1).trim();
            if (varName.matches("^[A-Za-z_][A-Za-z0-9_]*$") == false ||
                    varName.equalsIgnoreCase("print") ||
                    varName.equalsIgnoreCase("let") ||
                    varName.equalsIgnoreCase("abs") ||
                    varName.equalsIgnoreCase("cos") ||
                    varName.equalsIgnoreCase("log") ||
                    varName.equalsIgnoreCase("sin") ||
                    varName.equalsIgnoreCase("sqrt") ||
                    varName.equalsIgnoreCase("tan")) {
                err = true;
                throw new NomVariableException("nom variable non valide!");
            } else {
                variable.setName(varName);
                exp = input.substring(exprIndex).trim();
                Let commande = new Let(buildExpression(exp), variable);
                this.ligneCommande = commande;
                variable.setValeur(this.ligneCommande.evaluer());
                this.tableSymbole.addVariable(variable.getName(), variable.getValeur());
            }
        } else if (input.toLowerCase().startsWith("print ")) {
            exp = input.substring("print ".length()).trim();
            Print commande = new Print(buildExpression(exp));
            this.ligneCommande = commande;
        } else {
            err = true;
            throw new SyntaxeException("commande non reconnue!");
        }
        return err;
    }
    //---------------------------------
    public boolean checkErreur() {
        boolean stat = false;
        try {
            stat = checkSyntaxe();
        } catch (LangException e) {
            stat = true;
            System.out.println("Erreur : " + e.getMessage());
        } finally {
            return stat;
        }
    }
    //---------------------------------
    public void executerCommande() { ligneCommande.executer(); }
    //-----------------------------------------------------------------------//
}