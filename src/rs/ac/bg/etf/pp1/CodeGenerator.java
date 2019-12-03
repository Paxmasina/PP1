package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;

public class CodeGenerator extends VisitorAdaptor {
	
	private int mainPc;
	private boolean eValues = false;
	
	public int getMainPc() {
		return mainPc;
	}
	
	public void visit(ProgramName programName) {
		// chr metoda
		Tab.chrObj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n + 0);
		Code.put(Code.exit);
		Code.put(Code.return_);

		// ord metoda
		Tab.ordObj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n + 0);
		Code.put(Code.exit);
		Code.put(Code.return_);

		// len metoda
		Tab.lenObj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		Code.put(Code.load_n + 0);
		Code.put(Code.arraylength);
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		Code.dataSize = Code.dataSize + 9;   // 10 tmpVars
	}
	
	@Override
	public void visit(VoidType voidType) {
		if ("main".equalsIgnoreCase(voidType.getMethodName())) {
			mainPc = Code.pc;
		}
		voidType.obj.setAdr(Code.pc);
		
		// Collect arguments and local variables.
		SyntaxNode methodNode = voidType.getParent();
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);
		
		// Generate the entry.
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(varCnt.getCount() + fpCnt.getCount());
	}
	
	public void visit(ReturnType returnType) {
		returnType.obj.setAdr(Code.pc);
		
		// Collect arguments and local variables.
		SyntaxNode methodNode = returnType.getParent();
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);
		
		// Generate the entry.
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(varCnt.getCount() + fpCnt.getCount());
	}
	
	@Override
	public void visit(MethodDeclaration MethodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(Assignment Assignment) {
		if (eValues == false)
			Code.store(Assignment.getDesignator().obj);
		eValues = false;
	}
	
	public void visit(PrintConstStmt printStmt){
		Code.loadConst(printStmt.getN2());
  	 	Code.put(printStmt.getExpr().struct == Tab.charType ? Code.bprint : Code.print);
	}
	
	public void visit(DesignatorInc designatorInc) {
		Obj o = designatorInc.getDesignator().obj;
		if(o.getKind() == Obj.Elem)
			Code.put(Code.dup2);
		Code.load(o);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(o);
	}
	
	public void visit(DesignatorDec designatorDec) {
		Obj o = designatorDec.getDesignator().obj;
		if(o.getKind() == Obj.Elem)
			Code.put(Code.dup2);
		Code.load(o);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(o);
	}
	
	@Override
	public void visit(FuncCall funcCall) {
		Obj functionObj = funcCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc; 
		Code.put(Code.call);
		Code.put2(offset);
		if(funcCall.getDesignator().obj.getType() != Tab.noType)
			Code.put(Code.pop);
	}
	
	public void visit(FuncCallFactor funcCall) {
		int offset = funcCall.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}
	
	@Override
	public void visit(PrintStatement printStmt) {
		Struct t = printStmt.getExpr().struct;
		if (t == Tab.intType || t == ModTab.boolType || t.getKind() > 6) {
			Code.loadConst(5);
			Code.put(Code.print);
		} else {
			Code.loadConst(1);
			Code.put(Code.bprint);
		}	
	}
	
	public void visit(ConstantFactor constFact) {
		Code.load(constFact.getConstValue().obj);
	}
	
	public void visit(VariableFactor varFact) {
		Code.load(varFact.getDesignator().obj);
	}
	
	public void visit(AddExpr addExpr) {
		Addop addOp = addExpr.getAddop();
		if(addOp.getClass() == Plus.class)
			Code.put(Code.add);
		else
			Code.put(Code.sub);
	}
	
	public void visit(MulTerm mulTerm) {
		Mulop mulOp = mulTerm.getMulop();
		if(mulOp.getClass() == Mul.class)
			Code.put(Code.mul);
		else if(mulOp.getClass() == Div.class)
			Code.put(Code.div);
		else
			Code.put(Code.rem);
	}
	
	public void visit(NewFactorArray newFactorArray){
		Code.put(Code.newarray);
   		Code.put(newFactorArray.getType().struct==Tab.charType ? 0 : 1);

	}
	
	public void visit(ReadStatement readStatemant) {
		if(readStatemant.getDesignator().obj.getType() == Tab.charType)
			Code.put(Code.bread);
		else
			Code.put(Code.read);
		Code.store(readStatemant.getDesignator().obj);
	}
	
	public void visit(DesignatorIdent designatorIdent) {
		if(designatorIdent.obj.getType().getKind() == Struct.Array)
			Code.load(designatorIdent.obj);
	}
	
	public void visit(TermEpxrMinus termExprMinus) {
		Code.put(Code.neg);
	}
	
	public void visit(EnumValues enumValues) {
		Collection<Obj> enumCollection = ModTab.programScope.findSymbol(enumValues.getEnumName()).getLocalSymbols();
		ArrayList<Obj> enumList = new ArrayList<Obj>(enumCollection);
		eValues = true;
		for (int i=0; i < enumList.size(); i++) {
			if (i != (enumList.size()) - 1)
				Code.put(Code.dup);
			Code.loadConst(i);
			Code.load(enumList.get(i));
			Code.put(Code.astore);
			
		}
		
	}
	
}
