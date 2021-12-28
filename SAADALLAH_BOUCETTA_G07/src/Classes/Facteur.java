package Classes;


import TpExeption.DivisionSurZeroException;

import java.util.ArrayList;

public class Facteur implements Evaluable {

    private boolean inversible;
    private ArrayList<Element> elements;

    public Facteur(boolean inversible, ArrayList<Element> elements) {
        this.inversible = inversible;
        this.elements = (ArrayList<Element>) elements.clone();
    }

    public boolean isInversible() { return inversible; }
    public void setInversible(boolean inversible) { this.inversible = inversible; }

    @Override
    public double evaluer() throws DivisionSurZeroException {
        ArrayList<Element> tmpElements = (ArrayList<Element>) elements.clone();
        double result = tmpElements.get(0).getValeur();
        tmpElements.remove(0);
        for (Element elm : tmpElements) {
            if (elm.getValeur() < 0) {
                if (result == 0) {
                    throw new DivisionSurZeroException("division sur Zero!");
                } else {
                    result = Math.pow(result, - elm.getValeur());
                    result = 1 / result;
                }
            } else {
                result = Math.pow(result, elm.getValeur());
            }
        }
        return result;
    }
}