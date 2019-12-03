// generated with ast extension for cup
// version 0.8
// 19/7/2019 21:9:14


package rs.ac.bg.etf.pp1.ast;

public class VarFormParam extends FormParsDecl {

    private Type Type;
    private String varParamName;

    public VarFormParam (Type Type, String varParamName) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.varParamName=varParamName;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getVarParamName() {
        return varParamName;
    }

    public void setVarParamName(String varParamName) {
        this.varParamName=varParamName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarFormParam(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+varParamName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarFormParam]");
        return buffer.toString();
    }
}
