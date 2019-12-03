// generated with ast extension for cup
// version 0.8
// 19/7/2019 21:9:14


package rs.ac.bg.etf.pp1.ast;

public class ImplementedClasses extends ImplementedClassList {

    private ImplementedClassList ImplementedClassList;
    private ImplementedClass ImplementedClass;

    public ImplementedClasses (ImplementedClassList ImplementedClassList, ImplementedClass ImplementedClass) {
        this.ImplementedClassList=ImplementedClassList;
        if(ImplementedClassList!=null) ImplementedClassList.setParent(this);
        this.ImplementedClass=ImplementedClass;
        if(ImplementedClass!=null) ImplementedClass.setParent(this);
    }

    public ImplementedClassList getImplementedClassList() {
        return ImplementedClassList;
    }

    public void setImplementedClassList(ImplementedClassList ImplementedClassList) {
        this.ImplementedClassList=ImplementedClassList;
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
        if(ImplementedClassList!=null) ImplementedClassList.accept(visitor);
        if(ImplementedClass!=null) ImplementedClass.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ImplementedClassList!=null) ImplementedClassList.traverseTopDown(visitor);
        if(ImplementedClass!=null) ImplementedClass.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ImplementedClassList!=null) ImplementedClassList.traverseBottomUp(visitor);
        if(ImplementedClass!=null) ImplementedClass.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ImplementedClasses(\n");

        if(ImplementedClassList!=null)
            buffer.append(ImplementedClassList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ImplementedClass!=null)
            buffer.append(ImplementedClass.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ImplementedClasses]");
        return buffer.toString();
    }
}
