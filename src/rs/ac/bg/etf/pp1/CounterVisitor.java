package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.ArrayVarParamName;
import rs.ac.bg.etf.pp1.ast.MethodArrayVarDeclaration;
import rs.ac.bg.etf.pp1.ast.MethodVarDeclaration;
import rs.ac.bg.etf.pp1.ast.VarFormParam;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class CounterVisitor extends VisitorAdaptor {
	
	protected int count;
	
	public int getCount() {
		return count;
	}
	
	public static class FormParamCounter extends CounterVisitor {

		@Override
		public void visit(VarFormParam varFormParam) {
			count++;
		}	
		@Override
		public void visit(ArrayVarParamName arrayVarParam) {
			count++;
		}	
	}
	
	public static class VarCounter extends CounterVisitor {		
		
		@Override
		public void visit(MethodVarDeclaration VarDecl) {
			count++;
		}
		@Override
		public void visit(MethodArrayVarDeclaration VarDecl) {
			count++;
		}
		
	}
}
