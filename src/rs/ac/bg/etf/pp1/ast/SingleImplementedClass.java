// generated with ast extension for cup
// version 0.8
// 19/7/2019 21:9:14


package rs.ac.bg.etf.pp1.ast;

public class SingleImplementedClass extends ImplementedClassList {

    private ImplementedClass ImplementedClass;

    public SingleImplementedClass (ImplementedClass ImplementedClass) {
        this.ImplementedClass=ImplementedClass;
        if(ImplementedClass!=null) ImplementedClass.setParent(this);
    }

    public ImplementedClass getImplementedClass() {
        return ImplementedClass;
    }

    public void setImplementedClass(ImplementedClass ImplementedClass) {
        this.ImplementedClass=ImplementedClass;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ImplementedClass!=null) ImplementedClass.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ImplementedClass!=null) ImplementedClass.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ImplementedClass!=null) ImplementedClass.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleImplementedClass(\n");

        if(ImplementedClass!=null)
            buffer.append(ImplementedClass.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleImplementedClass]");
        return buffer.toString();
    }
}
