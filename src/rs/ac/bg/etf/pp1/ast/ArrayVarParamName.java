// generated with ast extension for cup
// version 0.8
// 19/7/2019 21:9:14


package rs.ac.bg.etf.pp1.ast;

public class ArrayVarParamName extends FormParsDecl {

    private Type Type;
    private String arrayVarParamName;

    public ArrayVarParamName (Type Type, String arrayVarParamName) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.arrayVarParamName=arrayVarParamName;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getArrayVarParamName() {
        return arrayVarParamName;
    }

    public void setArrayVarParamName(String arrayVarParamName) {
        this.arrayVarParamName=arrayVarParamName;
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
        buffer.append("ArrayVarParamName(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+arrayVarParamName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ArrayVarParamName]");
        return buffer.toString();
    }
}
