package Classes;

import java.util.ArrayList;

public class Expression extends Element implements Evaluable {

    private ArrayList<Terme> termes;

    public Expression(ArrayList<Terme> termes) { this.termes = (ArrayList<Terme>) termes.clone(); }

    public ArrayList<Terme> getTermes() { return termes; }
    public void setTermes(ArrayList<Terme> termes) { this.termes = (ArrayList<Terme>) termes.clone(); }

    @Override
    public double evaluer() {
        double result = 0;
        for (Terme trm : termes) {
            if (trm.isNegative()) {
                result -= trm.evaluer();
            } else {
                result += trm.evaluer();
            }
        }
        setValeur(result);
        return result;
    }
}