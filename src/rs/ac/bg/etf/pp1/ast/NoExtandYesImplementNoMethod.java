// generated with ast extension for cup
// version 0.8
// 19/7/2019 21:9:14


package rs.ac.bg.etf.pp1.ast;

public class NoExtandYesImplementNoMethod extends ClassDecl {

    private ClassName ClassName;
    private ImplementedClassList ImplementedClassList;
    private VarDeclarations VarDeclarations;

    public NoExtandYesImplementNoMethod (ClassName ClassName, ImplementedClassList ImplementedClassList, VarDeclarations VarDeclarations) {
        this.ClassName=ClassName;
        if(ClassName!=null) ClassName.setParent(this);
        this.ImplementedClassList=ImplementedClassList;
        if(ImplementedClassList!=null) ImplementedClassList.setParent(this);
        this.VarDeclarations=VarDeclarations;
        if(VarDeclarations!=null) VarDeclarations.setParent(this);
    }

    public ClassName getClassName() {
        return ClassName;
    }

    public void setClassName(ClassName ClassName) {
        this.ClassName=ClassName;
    }

    public ImplementedClassList getImplementedClassList() {
        return ImplementedClassList;
    }

    public void setImplementedClassList(ImplementedClassList ImplementedClassList) {
        this.ImplementedClassList=ImplementedClassList;
    }

    public VarDeclarations getVarDeclarations() {
        return VarDeclarations;
    }

    public void setVarDeclarations(VarDeclarations VarDeclarations) {
        this.VarDeclarations=VarDeclarations;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassName!=null) ClassName.accept(visitor);
        if(ImplementedClassList!=null) ImplementedClassList.accept(visitor);
        if(VarDeclarations!=null) VarDeclarations.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassName!=null) ClassName.traverseTopDown(visitor);
        if(ImplementedClassList!=null) ImplementedClassList.traverseTopDown(visitor);
        if(VarDeclarations!=null) VarDeclarations.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassName!=null) ClassName.traverseBottomUp(visitor);
        if(ImplementedClassList!=null) ImplementedClassList.traverseBottomUp(visitor);
        if(VarDeclarations!=null) VarDeclarations.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NoExtandYesImplementNoMethod(\n");

        if(ClassName!=null)
            buffer.append(ClassName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ImplementedClassList!=null)
            buffer.append(ImplementedClassList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclarations!=null)
            buffer.append(VarDeclarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NoExtandYesImplementNoMethod]");
        return buffer.toString();
    }
}
