package Classes;

import TpExeption.DivisionSurZeroException;

import java.util.ArrayList;

public class Terme implements Evaluable {

    private boolean negative; // indique si on doit additioner ou bien sustraire ce terme lors
                              // de l'evaluation de l'expression contenant ce terme
    private ArrayList<Facteur> facteurs; // liste de facteurs du terme

    public Terme(boolean negative, ArrayList<Facteur> facs) {
        this.negative = negative;
        this.facteurs = (ArrayList<Facteur>) facs.clone();
    }
    public boolean isNegative() {
        return negative;
    }
    public void setNegative(boolean negative) {
        this.negative = negative;
    }

    @Override
    public double evaluer() throws DivisionSurZeroException {
        double result = 1; // le resultat de l'evaluation du terme
        for (Facteur fact : facteurs) { // on parcour la liste des facteurs
            if (fact.isInversible()) { // si on doit diviser sur le facteur
                if (fact.evaluer() == 0) { // si le facteur est nul
                    throw new DivisionSurZeroException("division sur Zero!"); // on jette une exception
                }
                result /= fact.evaluer(); // s'il est pas nul on y divise
            } else {
                result *= fact.evaluer(); // sinon on y multiplie
            }
        }
        return result; // on retourne le resulatat de l'evaluation
    }
}