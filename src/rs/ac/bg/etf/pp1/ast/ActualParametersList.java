// generated with ast extension for cup
// version 0.8
// 19/7/2019 21:9:14


package rs.ac.bg.etf.pp1.ast;

public class ActualParametersList extends ActParsList {

    private Expr Expr;
    private ActParsList ActParsList;

    public ActualParametersList (Expr Expr, ActParsList ActParsList) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.ActParsList=ActParsList;
        if(ActParsList!=null) ActParsList.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public ActParsList getActParsList() {
        return ActParsList;
    }

    public void setActParsList(ActParsList ActParsList) {
        this.ActParsList=ActParsList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
        if(ActParsList!=null) ActParsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(ActParsList!=null) ActParsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(ActParsList!=null) ActParsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActualParametersList(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActParsList!=null)
            buffer.append(ActParsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ActualParametersList]");
        return buffer.toString();
    }
}
