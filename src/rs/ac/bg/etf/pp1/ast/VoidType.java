// generated with ast extension for cup
// version 0.8
// 19/7/2019 21:9:14


package rs.ac.bg.etf.pp1.ast;

public class VoidType extends MethodTypeName {

    private String MethodName;

    public VoidType (String MethodName) {
        this.MethodName=MethodName;
    }

    public String getMethodName() {
        return MethodName;
    }

    public void setMethodName(String MethodName) {
        this.MethodName=MethodName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VoidType(\n");

        buffer.append(" "+tab+MethodName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VoidType]");
        return buffer.toString();
    }
}
