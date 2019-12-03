// generated with ast extension for cup
// version 0.8
// 19/7/2019 21:9:14


package rs.ac.bg.etf.pp1.ast;

public class DesignatorEnum extends Designator {

    private String enumName;
    private String enumValue;

    public DesignatorEnum (String enumName, String enumValue) {
        this.enumName=enumName;
        this.enumValue=enumValue;
    }

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName=enumName;
    }

    public String getEnumValue() {
        return enumValue;
    }

    public void setEnumValue(String enumValue) {
        this.enumValue=enumValue;
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
        buffer.append("DesignatorEnum(\n");

        buffer.append(" "+tab+enumName);
        buffer.append("\n");

        buffer.append(" "+tab+enumValue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorEnum]");
        return buffer.toString();
    }
}
