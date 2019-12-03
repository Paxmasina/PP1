// generated with ast extension for cup
// version 0.8
// 19/7/2019 21:9:14


package rs.ac.bg.etf.pp1.ast;

public class MethodVarDecls extends MethodVarDeclarations {

    private MethodVarDeclarations MethodVarDeclarations;
    private MethodVarDecl MethodVarDecl;

    public MethodVarDecls (MethodVarDeclarations MethodVarDeclarations, MethodVarDecl MethodVarDecl) {
        this.MethodVarDeclarations=MethodVarDeclarations;
        if(MethodVarDeclarations!=null) MethodVarDeclarations.setParent(this);
        this.MethodVarDecl=MethodVarDecl;
        if(MethodVarDecl!=null) MethodVarDecl.setParent(this);
    }

    public MethodVarDeclarations getMethodVarDeclarations() {
        return MethodVarDeclarations;
    }

    public void setMethodVarDeclarations(MethodVarDeclarations MethodVarDeclarations) {
        this.MethodVarDeclarations=MethodVarDeclarations;
    }

    public MethodVarDecl getMethodVarDecl() {
        return MethodVarDecl;
    }

    public void setMethodVarDecl(MethodVarDecl MethodVarDecl) {
        this.MethodVarDecl=MethodVarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodVarDeclarations!=null) MethodVarDeclarations.accept(visitor);
        if(MethodVarDecl!=null) MethodVarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodVarDeclarations!=null) MethodVarDeclarations.traverseTopDown(visitor);
        if(MethodVarDecl!=null) MethodVarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodVarDeclarations!=null) MethodVarDeclarations.traverseBottomUp(visitor);
        if(MethodVarDecl!=null) MethodVarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodVarDecls(\n");

        if(MethodVarDeclarations!=null)
            buffer.append(MethodVarDeclarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodVarDecl!=null)
            buffer.append(MethodVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodVarDecls]");
        return buffer.toString();
    }
}
