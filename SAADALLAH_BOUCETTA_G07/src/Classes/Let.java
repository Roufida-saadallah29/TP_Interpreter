package Classes;

import TpExeption.LangException;

public class Let extends LigneCommande { // la commande `let`

    private Variable variable;

    public Variable getVariable() { return variable; }
    public void setVariable(Variable variable) { this.variable = variable; }

    public Let(Expression expr, Variable var) {
        this.variable = var;
        this.setExpression(expr);
    }

    @Override
    public double evaluer() throws LangException {
        return getExpression().evaluer();
    }

    @Override
    public void executer() {
        try {
            this.variable.setValeur(evaluer());
            System.out.println("Ok");
        } catch (LangException e) { System.out.println("Erreur : " + e.getMessage()); }
    }
}