// generated with ast extension for cup
// version 0.8
// 19/7/2019 21:9:14


package rs.ac.bg.etf.pp1.ast;

public class SingleMethodVarDecl extends MethodVarDeclList {

    private MethodVarDeclListPart MethodVarDeclListPart;

    public SingleMethodVarDecl (MethodVarDeclListPart MethodVarDeclListPart) {
        this.MethodVarDeclListPart=MethodVarDeclListPart;
        if(MethodVarDeclListPart!=null) MethodVarDeclListPart.setParent(this);
    }

    public MethodVarDeclListPart getMethodVarDeclListPart() {
        return MethodVarDeclListPart;
    }

    public void setMethodVarDeclListPart(MethodVarDeclListPart MethodVarDeclListPart) {
        this.MethodVarDeclListPart=MethodVarDeclListPart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodVarDeclListPart!=null) MethodVarDeclListPart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodVarDeclListPart!=null) MethodVarDeclListPart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodVarDeclListPart!=null) MethodVarDeclListPart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleMethodVarDecl(\n");

        if(MethodVarDeclListPart!=null)
            buffer.append(MethodVarDeclListPart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleMethodVarDecl]");
        return buffer.toString();
    }
}
