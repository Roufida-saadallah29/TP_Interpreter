package Classes;


import TpExeption.NomFonctionErroneException;
import TpExeption.VariableNonDeclareeException;

import java.util.HashMap;

public class TableSymbole {
    //---------------------------*** Attributs ***---------------------------//
    private HashMap<String, Double> variables; // <nomVariable, valeur>
    private HashMap<String, CodeFonction> fonctions; // <nomFonction, son_code>

    public TableSymbole() {
        this.variables = new HashMap<>();
        this.fonctions = new HashMap<>();
    }
    //-----------------------------------------------------------------------//
    //----------------------------*** Setters ***----------------------------//
    public void addVariable(String nom, Double valeur) {
        variables.put(nom, valeur);
    }
    //---------------------------------
    public double getVariable(String nom) throws VariableNonDeclareeException {
        if (variables.containsKey(nom) == false) {
            throw new VariableNonDeclareeException(" viariable `" + nom + "` n'est pas declaree!");
        } else {
            return variables.get(nom);
        }
    }
    //---------------------------------
    public void addFonction(String nom, CodeFonction code) {
        fonctions.put(nom, code);
    }
    //---------------------------------
    public CodeFonction getFonction(String nom) throws NomFonctionErroneException {
        if (fonctions.containsKey(nom) == false) {
            throw new NomFonctionErroneException(" fonction `" + nom + "` n'existe pas!");
        } else {
            return fonctions.get(nom);
        }
    }
    //-----------------------------------------------------------------------//
}