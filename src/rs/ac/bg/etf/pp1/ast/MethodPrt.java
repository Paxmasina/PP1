// generated with ast extension for cup
// version 0.8
// 19/7/2019 21:9:14


package rs.ac.bg.etf.pp1.ast;

public class MethodPrt extends MethodPart {

    private FormPars FormPars;
    private MethodVarDeclarations MethodVarDeclarations;
    private StatementList StatementList;

    public MethodPrt (FormPars FormPars, MethodVarDeclarations MethodVarDeclarations, StatementList StatementList) {
        this.FormPars=FormPars;
        if(FormPars!=null) FormPars.setParent(this);
        this.MethodVarDeclarations=MethodVarDeclarations;
        if(MethodVarDeclarations!=null) MethodVarDeclarations.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public FormPars getFormPars() {
        return FormPars;
    }

    public void setFormPars(FormPars FormPars) {
        this.FormPars=FormPars;
    }

    public MethodVarDeclarations getMethodVarDeclarations() {
        return MethodVarDeclarations;
    }

    public void setMethodVarDeclarations(MethodVarDeclarations MethodVarDeclarations) {
        this.MethodVarDeclarations=MethodVarDeclarations;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormPars!=null) FormPars.accept(visitor);
        if(MethodVarDeclarations!=null) MethodVarDeclarations.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormPars!=null) FormPars.traverseTopDown(visitor);
        if(MethodVarDeclarations!=null) MethodVarDeclarations.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormPars!=null) FormPars.traverseBottomUp(visitor);
        if(MethodVarDeclarations!=null) MethodVarDeclarations.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodPrt(\n");

        if(FormPars!=null)
            buffer.append(FormPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodVarDeclarations!=null)
            buffer.append(MethodVarDeclarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodPrt]");
        return buffer.toString();
    }
}
