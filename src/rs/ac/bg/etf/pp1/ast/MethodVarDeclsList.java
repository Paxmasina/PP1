// generated with ast extension for cup
// version 0.8
// 19/7/2019 21:9:14


package rs.ac.bg.etf.pp1.ast;

public class MethodVarDeclsList extends MethodVarDeclList {

    private MethodVarDeclList MethodVarDeclList;
    private MethodVarDeclListPart MethodVarDeclListPart;

    public MethodVarDeclsList (MethodVarDeclList MethodVarDeclList, MethodVarDeclListPart MethodVarDeclListPart) {
        this.MethodVarDeclList=MethodVarDeclList;
        if(MethodVarDeclList!=null) MethodVarDeclList.setParent(this);
        this.MethodVarDeclListPart=MethodVarDeclListPart;
        if(MethodVarDeclListPart!=null) MethodVarDeclListPart.setParent(this);
    }

    public MethodVarDeclList getMethodVarDeclList() {
        return MethodVarDeclList;
    }

    public void setMethodVarDeclList(MethodVarDeclList MethodVarDeclList) {
        this.MethodVarDeclList=MethodVarDeclList;
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
        if(MethodVarDeclList!=null) MethodVarDeclList.accept(visitor);
        if(MethodVarDeclListPart!=null) MethodVarDeclListPart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodVarDeclList!=null) MethodVarDeclList.traverseTopDown(visitor);
        if(MethodVarDeclListPart!=null) MethodVarDeclListPart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodVarDeclList!=null) MethodVarDeclList.traverseBottomUp(visitor);
        if(MethodVarDeclListPart!=null) MethodVarDeclListPart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodVarDeclsList(\n");

        if(MethodVarDeclList!=null)
            buffer.append(MethodVarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodVarDeclListPart!=null)
            buffer.append(MethodVarDeclListPart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodVarDeclsList]");
        return buffer.toString();
    }
}
