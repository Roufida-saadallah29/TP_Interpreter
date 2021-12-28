package Classes;

import TpExeption.LangException;

public class Print extends LigneCommande { // la commande `print`

    public Print(Expression expr) {
        this.setExpression(expr);
    }

    @Override
    public double evaluer() throws LangException { // on evalue la valeur de l'expression
        return getExpression().evaluer();
    }

    @Override
    public void executer() { // on execute la commande `print` en affichant la valeur de son expression
        try {
            System.out.println("La valeur est : " + evaluer());
        } catch (LangException e) { System.out.println("Erreur : " + e.getMessage()); } // en cas de probleme lors de l'evaluation
                                                           // de l'expression on affiche les exceptions
    }
}