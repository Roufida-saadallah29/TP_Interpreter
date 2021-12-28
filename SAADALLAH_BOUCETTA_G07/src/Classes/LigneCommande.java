package Classes;


abstract public class LigneCommande implements Evaluable {

    private Expression expression; // attribut commun entre `let` et `print`

    public Expression getExpression() { return expression; }
    public void setExpression(Expression expression) { this.expression = expression; }

    abstract public void executer();
}
