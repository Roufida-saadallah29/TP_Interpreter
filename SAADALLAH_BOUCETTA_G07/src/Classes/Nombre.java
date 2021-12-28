package Classes;



public class Nombre extends Element implements Evaluable {

    @Override
    public double evaluer() {
        return getValeur();
    }
}
