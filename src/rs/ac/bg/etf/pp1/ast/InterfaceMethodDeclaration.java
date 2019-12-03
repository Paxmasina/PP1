// generated with ast extension for cup
// version 0.8
// 19/7/2019 21:9:14


package rs.ac.bg.etf.pp1.ast;

public class InterfaceMethodDeclaration extends InterfaceMethodDecl {

    private InterfaceMethodTypeName InterfaceMethodTypeName;
    private InterfaceMethodPart InterfaceMethodPart;

    public InterfaceMethodDeclaration (InterfaceMethodTypeName InterfaceMethodTypeName, InterfaceMethodPart InterfaceMethodPart) {
        this.InterfaceMethodTypeName=InterfaceMethodTypeName;
        if(InterfaceMethodTypeName!=null) InterfaceMethodTypeName.setParent(this);
        this.InterfaceMethodPart=InterfaceMethodPart;
        if(InterfaceMethodPart!=null) InterfaceMethodPart.setParent(this);
    }

    public InterfaceMethodTypeName getInterfaceMethodTypeName() {
        return InterfaceMethodTypeName;
    }

    public void setInterfaceMethodTypeName(InterfaceMethodTypeName InterfaceMethodTypeName) {
        this.InterfaceMethodTypeName=InterfaceMethodTypeName;
    }

    public InterfaceMethodPart getInterfaceMethodPart() {
        return InterfaceMethodPart;
    }

    public void setInterfaceMethodPart(InterfaceMethodPart InterfaceMethodPart) {
        this.InterfaceMethodPart=InterfaceMethodPart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(InterfaceMethodTypeName!=null) InterfaceMethodTypeName.accept(visitor);
        if(InterfaceMethodPart!=null) InterfaceMethodPart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(InterfaceMethodTypeName!=null) InterfaceMethodTypeName.traverseTopDown(visitor);
        if(InterfaceMethodPart!=null) InterfaceMethodPart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(InterfaceMethodTypeName!=null) InterfaceMethodTypeName.traverseBottomUp(visitor);
        if(InterfaceMethodPart!=null) InterfaceMethodPart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("InterfaceMethodDeclaration(\n");

        if(InterfaceMethodTypeName!=null)
            buffer.append(InterfaceMethodTypeName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(InterfaceMethodPart!=null)
            buffer.append(InterfaceMethodPart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [InterfaceMethodDeclaration]");
        return buffer.toString();
    }
}
