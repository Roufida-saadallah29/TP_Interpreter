package Classes;

import TpExeption.LangException;

public class Fonction extends Element implements Evaluable {

    private CodeFonction fncode;
    private Expression input;

    public Fonction(CodeFonction code, Expression expr) {
        this.fncode = code;
        this.input = expr;
        super.setValeur(this.evaluer());
    }
    public CodeFonction getFncode() { return fncode; }
    public void setFncode(CodeFonction fncode) {
        this.fncode = fncode;
    }

    public Expression getInput() { return input; }
    public void setInput(Expression input) { this.input = input; }

    @Override
    public double evaluer() throws LangException {
        switch (fncode) {
            case ABS -> {
                setValeur(Math.abs(input.evaluer()));
                break;
            } 
            case COS -> {
                setValeur(Math.cos(Math.toRadians(input.evaluer())));
                break;
            }
            case LOG -> {
                setValeur(Math.log(input.evaluer()));
                break;
            }
            case SIN -> {
                setValeur(Math.sin(Math.toRadians(input.evaluer())));
                break;
            }
            case TAN -> {
                setValeur(Math.tan(Math.toRadians(input.evaluer())));
                break;
            }
            case SQRT -> {
                setValeur(Math.sqrt(input.evaluer()));
                break;
            }
            default -> {
                setValeur(input.evaluer());
            }
        }
        return getValeur();
    }
}