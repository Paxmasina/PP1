package rs.ac.bg.etf.pp1;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Stack;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticAnalyzer extends VisitorAdaptor {
	boolean errorDetected = false;
	int printCallCount = 0;
	Obj currentMethod = null;
	int currentEnumValue = 0;
	String currentEnumName = "";
	boolean returnFound = false;
	Struct currentType = null;
	int nVars;
	int currentNumOfParameters = 0;
	HashSet<Integer> currentEnumSet = null;
	int typeCounter = 7;

	private Stack<Struct> actuals = new Stack<>();
	private int currentMethodLevel = 0;
	Logger log = Logger.getLogger(getClass());

	public int varDeclCount = 0;

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}

	public void visit(SimpleVarDeclaration simpleVarDeclaration) {
		if (Tab.currentScope.findSymbol(simpleVarDeclaration.getVarName()) != null)
			report_error("Semanticka greska pri definiciji promenljive na liniji " + simpleVarDeclaration.getLine()
					+ ": Promenljiva vec postoji u opsegu.", null);
		else if (currentType == Tab.noType) {
			report_error("Semanticka greska pri definiciji promenljive na liniji " + simpleVarDeclaration.getLine()
					+ ": Nepostojeci tip.", null);
		} else {
			Tab.insert(Obj.Var, simpleVarDeclaration.getVarName(), currentType);
			varDeclCount++;
		}
	}

	public void visit(Program program) {
		Obj mainFunc = Tab.find("main");
		if (mainFunc.getKind() != Obj.Meth || mainFunc.getType() != Tab.noType || mainFunc.getLevel() != 0) {
			report_error("U programu je neophodno definisati metodu main bez argumenata i bez povratne vrednosti.",
					null);
		}
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(program.getProgramName().obj);
		ModTab.programScope = Tab.currentScope;
		Tab.closeScope();
	}

	public void visit(ProgramName programName) {

		programName.obj = Tab.insert(Obj.Prog, programName.getProgName(), Tab.noType);
		Tab.openScope();
	}

	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getTypeName());
		if (typeNode == Tab.noObj) {
			report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola", null);
			type.struct = Tab.noType;
			currentType = Tab.noType;
		} else {
			if (Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
				currentType = typeNode.getType();
			} else {
				report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip ", type);
				type.struct = Tab.noType;
				currentType = Tab.noType;
			}
		}
	}

	public void visit(NumConst numConst) {
		numConst.obj = new Obj(Obj.Con, "", Tab.intType);
		numConst.obj.setAdr(numConst.getNum().intValue());
	}

	public void visit(CharConst charCon) {
		charCon.obj = new Obj(Obj.Con, "", Tab.charType);
		charCon.obj.setAdr(charCon.getCha().charValue());
	}

	public void visit(BoolConst boolCon) {
		boolCon.obj = new Obj(Obj.Con, "", ModTab.boolType);
		boolCon.obj.setAdr(boolCon.getBool().booleanValue() ? 0 : 1);
	}

	public void visit(ConstDeclaration constDeclaration) {
		if (Tab.currentScope.findSymbol(constDeclaration.getConstName()) != null)
			report_error("Semanticka greska pri definiciji konstante na liniji " + constDeclaration.getLine()
					+ ": Vec postoji identifikator '" + constDeclaration.getConstName() + "' u trenutnom opsegu.",
					null); 
		else {
			Obj con = Tab.insert(Obj.Con, constDeclaration.getConstName(), currentType);
			con.setAdr(constDeclaration.getConstValue().obj.getAdr());
			if (constDeclaration.getConstValue().obj.getType() != currentType)
				report_error("Semanticka greska na liniji " + constDeclaration.getLine() + ": Greska u tipu za '"
						+ constDeclaration.getConstName() + "'.", null);
		}
	}

	public void visit(EnumName enumName) {
		if (Tab.currentScope.findSymbol(enumName.getEnumName()) != null)
			report_error("Semanticka greska pri definiciji nabrajanja na liniji " + enumName.getLine()
					+ ": Vec postoji identifikator '" + enumName.getEnumName() + "' u trenutnom opsegu.", null);
		else {
			currentEnumValue = 0;
			currentEnumSet = new HashSet<Integer>();
			Struct enumType = new Struct(typeCounter);
			typeCounter++;
			enumName.obj = Tab.insert(Obj.Type, enumName.getEnumName(), enumType);
			report_info("Definisananje enuma " + enumName.getEnumName(), enumName);
			Tab.openScope();
		}
	}

	public void visit(EnumDeclaration enumDeclaration) {
		if (Tab.currentScope.findSymbol(currentEnumName + "." + enumDeclaration.getName()) != null)
			report_error("Semanticka greska pri definiciji nabrajanja na liniji " + enumDeclaration.getLine()
					+ ": Vec postoji sa istim imenom.", null);
		else {
			Obj enumVal = new Obj(Obj.Con, enumDeclaration.getName(), Tab.intType);
			Tab.currentScope().addToLocals(enumVal);
			report_info("Dodato novo nabrajanje " + enumDeclaration.getName() + " kojem je dodeljena vrednost: "
					+ currentEnumValue + " u trenutni enum", enumDeclaration);
			enumVal.setAdr(currentEnumValue);
			currentEnumSet.add(currentEnumValue);
			currentEnumValue++;
		}
	}

	public void visit(EnumDeclarationAssign enumDeclarationAssign) {
		if (Tab.currentScope.findSymbol(currentEnumName + "." + enumDeclarationAssign.getName()) != null)
			report_error("Semanticka greska pri definiciji nabrajanja na liniji " + enumDeclarationAssign.getLine()
					+ ": Vec postoji sa istim imenom.", null);
		else {
			Obj enumVal = new Obj(Obj.Con, enumDeclarationAssign.getName(), Tab.intType);
			Tab.currentScope().addToLocals(enumVal);
			currentEnumValue = enumDeclarationAssign.getValue();
			report_info("Dodato novo nabrajanje " + enumDeclarationAssign.getName() + " kojem je dodeljena vrednost: "
					+ currentEnumValue + " u trenutni enum", enumDeclarationAssign);
			enumVal.setAdr(currentEnumValue);
			currentEnumSet.add(currentEnumValue);
			currentEnumValue++;
		}
	}
	
	public void visit(EnumDeclarations enumDecl) {
		Obj enumObj = enumDecl.getEnumName().obj;
		Tab.chainLocalSymbols(enumObj);
		report_info("Kraj definicije enuma: " + enumDecl.getEnumName().getEnumName(), enumDecl);
		Tab.closeScope();
	}

	
	public void visit(ArrayVarDeclaration arrayVarDeclaration) {
		if (Tab.currentScope.findSymbol(arrayVarDeclaration.getArrayName()) != null)
			report_error("Semanticka greska pri definiciji promenljive na liniji " + arrayVarDeclaration.getLine()
					+ ": Promenljiva vec postoji u opsegu.", null);
		else if (currentType == Tab.noType) {
			report_error("Semanticka greska pri definiciji promenljive na liniji " + arrayVarDeclaration.getLine()
					+ ": Nepostojeci tip.", null);
		} else {
			Tab.insert(Obj.Var, arrayVarDeclaration.getArrayName(), new Struct(Struct.Array, currentType));
			varDeclCount++;
		}
	}

	public void visit(MethodDeclaration methodDecl) {
		if (!returnFound && currentMethod.getType() != Tab.noType) {
			report_error("Semanticka greska na liniji " + methodDecl.getLine() + ": funkcija " + currentMethod.getName()
					+ " nema return iskaz!", null);
		}
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();

		returnFound = false;
		currentMethod = null;
	}

	public void visit(VoidType voidType) {
		Obj meth = Tab.currentScope.findSymbol(voidType.getMethodName());
		if (meth != null)
			report_error(
					"Semanticka greska pri definiciji funkcije na liniji " + voidType.getLine()
							+ ": Vec postoji identifikator '" + voidType.getMethodName() + "' u trenutnom opsegu.",
					null);
		else {
			currentMethod = Tab.insert(Obj.Meth, voidType.getMethodName(), Tab.noType);
			voidType.obj = currentMethod;
			Tab.openScope();
			currentMethod.setLevel(0);
		}
	}

	public void visit(ReturnType returnType) {
		Obj meth = Tab.currentScope.findSymbol(returnType.getMethodName());
		if (meth != null)
			report_error(
					"Semanticka greska pri definiciji funkcije na liniji " + returnType.getLine()
							+ ": Vec postoji identifikator '" + returnType.getMethodName() + "' u trenutnom opsegu.",
					null);
		else {
			currentMethod = Tab.insert(Obj.Meth, returnType.getMethodName(), returnType.getType().struct);
			returnType.obj = currentMethod;
			Tab.openScope();
			currentMethod.setLevel(0);
		}
	}

	public void visit(MethodArrayVarDeclaration methodArrayVarDeclaration) {
		if (Tab.currentScope.findSymbol(methodArrayVarDeclaration.getArrayVarName()) != null)
			report_error("Semanticka greska na liniji " + methodArrayVarDeclaration.getLine()
					+ ": Vec postoji identifikator '" + methodArrayVarDeclaration.getArrayVarName()
					+ "' u trenutnom opsegu.", null);
		else {
			Tab.insert(Obj.Var, methodArrayVarDeclaration.getArrayVarName(), new Struct(Struct.Array, currentType));
		}
	}

	public void visit(MethodVarDeclaration methodVarDeclaration) {
		if (Tab.currentScope.findSymbol(methodVarDeclaration.getVarName()) != null)
			report_error("Semanticka greska na liniji " + methodVarDeclaration.getLine()
					+ ": Vec postoji identifikator '" + methodVarDeclaration.getVarName() + "' u trenutnom opsegu.",
					null);
		else {
			Tab.insert(Obj.Var, methodVarDeclaration.getVarName(), currentType);
		}
	}

	public void visit(ArrayVarParamName ArrayVarParamName) {
		if (Tab.currentScope.findSymbol(ArrayVarParamName.getArrayVarParamName()) != null)
			report_error("Semanticka greska na liniji " + ArrayVarParamName.getLine() + ": Vec postoji identifikator '"
					+ ArrayVarParamName.getArrayVarParamName() + "' u trenutnom opsegu.", null);
		else {
			Tab.insert(Obj.Var, ArrayVarParamName.getArrayVarParamName(), new Struct(Struct.Array, currentType));
			currentMethod.setLevel(currentMethod.getLevel() + 1);
		}
	}

	public void visit(VarFormParam varFormParam) {
		if (Tab.currentScope.findSymbol(varFormParam.getVarParamName()) != null)
			report_error("Semanticka greska na liniji " + varFormParam.getLine() + ": Vec postoji identifikator '"
					+ varFormParam.getVarParamName() + "' u trenutnom opsegu.", null);
		else {
			Tab.insert(Obj.Var, varFormParam.getVarParamName(), currentType);
			currentMethod.setLevel(currentMethod.getLevel() + 1);
		}
	}

	public void visit(Assignment assignment) {
		Obj leftSide = assignment.getDesignator().obj;
		if (leftSide.getKind() != Obj.Var && leftSide.getKind() != Obj.Elem)
			report_error("Semanticka greska na liniji " + assignment.getLine()
					+ ": Izraz sa leve strane mora biti promenljiva ili element niza", null);
		if (assignment.getExpr().struct != null) {
			if (!assignableTo(assignment.getExpr().struct, assignment.getDesignator().obj.getType()))
				report_error(
						"Greska na liniji " + assignment.getLine() + " : " + " nekompatibilni tipovi u dodeli vrednosti",
						null);
			if (assignment.getExpr().struct.getKind() >= 7 && assignment.getDesignator().obj.getType().getKind() >= 7)
				if (!compatibleEnums(assignment.getExpr().struct, assignment.getDesignator().obj.getType()))
					report_error(
							"Greska na liniji " + assignment.getLine() + " : " + " nekompatibilni enumi u dodeli vrednosti",
							null);
		}
	}

	public void visit(DesignatorArray designatorArray) {
		if (designatorArray.getDesignator().obj.getType().getKind() != Struct.Array) {
			report_error("Semanticka greska na liniji " + designatorArray.getLine() + " "
					+ designatorArray.getDesignator().obj.getName() + " nije niz.", null);
		} else if (designatorArray.getExpr().struct != Tab.intType && designatorArray.getExpr().struct.getKind() < 6) {
			report_error(
					"Semanticka greska na liniji " + designatorArray.getLine() + ": Izraz u indeksu nije tipa int.",
					null);
		} else {
			designatorArray.obj = new Obj(Obj.Elem, "", designatorArray.getDesignator().obj.getType().getElemType());
			report_info("Pristup elementu niza(" + designatorArray.getLine() + ")", null);
		}
	}

	public void visit(PrintConstStmt printConstStmt) {
		Struct s = printConstStmt.getExpr().struct;
		if (s != Tab.intType && s != Tab.charType && s != ModTab.boolType && s.getKind() <= 6)
			report_error("Semanticka greska na liniji " + printConstStmt.getLine()
					+ ": Neodgovarajuci tip argumenta print funkcije.", null);
	}

	public void visit(PrintStatement printStmt) {
		Struct s = printStmt.getExpr().struct;
		if (s != Tab.intType && s != Tab.charType && s!= ModTab.boolType && s.getKind() <= 6)
			report_error("Semanticka greska na liniji " + printStmt.getLine()
					+ ": Neodgovarajuci tip argumenta print funkcije.", null);
	}

	public void visit(ReadStatement readStmt) {
		Obj desig = readStmt.getDesignator().obj;
		if (desig.getKind() != Obj.Var && desig.getKind() != Obj.Elem)
			report_error("Semanticka greska na liniji " + readStmt.getLine()
					+ ": Argument funkcije mora biti promenljiva, element niza ili polje objekta unutrasnje klase.",
					null);
		if (desig.getType() != Tab.intType && desig.getType() != Tab.charType)
			report_error("Semanticka greska na liniji " + readStmt.getLine()
					+ ": Neodgovarajuci tip argumenta read funkcije.", null);
	}

	public void visit(ReturnStatement returnStmt) {
		returnFound = true;
		if (currentMethod != null) {
			Struct currMethType = currentMethod.getType();
			if (currMethType != Tab.noType) {
				report_error("Semanticka greska: Nedostaje povratna vrednost u return naredbi.", null);
			}
		}
	}

	public void visit(ReturnExpr returnExpr) {
		returnFound = true;
		if (currentMethod != null) {
			Struct currMethType = currentMethod.getType();
			if (!currMethType.compatibleWith(returnExpr.getExpr().struct)) {
				report_error(
						"Semanticka greska na liniji " + returnExpr.getLine()
								+ ": Tip izraza u return naredbi ne slaze se sa tipom povratne vrednosti funkcije.",
						null);
			}
		}
	}

	public void visit(DesignatorIdent designatorIdent) {
		Obj obj = Tab.find(designatorIdent.getDesignatorName());
		if (obj == Tab.noObj)
			report_error("Semanticka greska na liniji " + designatorIdent.getLine() + ": Ime '"
					+ designatorIdent.getDesignatorName() + "' nije deklarisano.", null);
		else {
			designatorIdent.obj = obj;
			if (obj.getKind() == Obj.Con)
				report_info("Obradjuje se konstanta " + designatorIdent.getDesignatorName(), designatorIdent);
			else if (obj.getKind() == Obj.Var)
				if (obj.getLevel() == 0)
					report_info("Koristi se globalna promenljiva " + designatorIdent.getDesignatorName(), designatorIdent);
				else if(currentMethod != null && obj.getAdr() < currentMethod.getLevel())
					report_info("Koristi se formalni parametar " + designatorIdent.getDesignatorName(), designatorIdent);
				else if(currentMethod != null && obj.getAdr() >= currentMethod.getLevel())
					report_info("Koristi se lokalna promenljiva " + designatorIdent.getDesignatorName(), designatorIdent);
		}
	}

	public void visit(DesignatorEnum designatorEnum) {
		Obj obj = Tab.find(designatorEnum.getEnumName());
		if ((obj.getKind() != Obj.Type && obj.getType().getKind() != Struct.Enum)) 
			report_error(designatorEnum.getEnumName() + " nije enum", designatorEnum);
		else {
			Obj valueNode = new Obj(Obj.Con, designatorEnum.getEnumValue(), Tab.intType);
			if (enumContains(obj, valueNode)) 
				designatorEnum.obj = valueNode;
			else 
				report_error("Vrednost " + designatorEnum.getEnumValue() + " ne pripada enumu: "
						+ designatorEnum.getEnumName(), designatorEnum);
		}
	}

	public void visit(AddExpr addExpr) {
		Struct te = addExpr.getExpr().struct;
		Struct t = addExpr.getTerm().struct;
		if (assignableTo(t, te))
			addExpr.struct = te;
		else {
			report_error("Greska na liniji " + addExpr.getLine() + " : nekompatibilni tipovi u izrazu za sabiranje.",
					null);
			addExpr.struct = Tab.noType;
		}
	}

	public void visit(TermExprPlus termExprPlus) {
		termExprPlus.struct = termExprPlus.getTerm().struct;
	}

	public void visit(TermEpxrMinus termExprMinus) {
		termExprMinus.struct = termExprMinus.getTerm().struct;
		if (termExprMinus.struct != Tab.intType)
			report_error("Semanticka greska na liniji " + termExprMinus.getLine()
					+ ": Unarni operator '-' se moze primeniti samo nad tipom int.", null);
	}

	public void visit(MulTerm term) {
		Struct t = term.getTerm().struct;
		Struct f = term.getFactor().struct;
		if (assignableTo(t, f))
			term.struct = t;
		else {
			report_error("Semanticka greska na liniji " + term.getLine() + ": Nekompatibilni tipovi u izrazu.", null);
			term.struct = Tab.noType;
		}
	}

	public void visit(FuncCallFactor funcCall) {
		Obj func = funcCall.getDesignator().obj;
		funcCall.struct = func.getType();
		if (currentMethodLevel == 1)
			currentNumOfParameters = 0;
		if (func != Tab.noObj) {
			if (Obj.Meth != func.getKind()) {
				report_error(
						"Semanticka greska na liniji " + funcCall.getLine() + ", " + func.getName() + " nije funkcija.",
						null);
			} else if (func.getLevel() != (actuals.size() - currentNumOfParameters)) {
				report_error("Semanticka greska na liniji " + funcCall.getLine()
						+ ": Neodgovarajuci broj stvarnih argumenata prilikom poziva funkcije '" + func.getName()
						+ "'.", null);
			} else {
				int i = 0;
				for (Obj o : func.getLocalSymbols()) {
					if (++i > func.getLevel())
						break;
					Struct actual = actuals.pop();
					Struct formal = o.getType();

					if (!assignableTo(actual, formal)) {
						report_error("Semanticka greska na liniji " + funcCall.getLine()
								+ ": Neodgovarajuci tip stvarnog argumenta prilikom poziva funkcije '" + func.getName()
								+ "' za parametar '" + o.getName() + "'.", null);
					}
				}
				currentMethodLevel--;
			}
		}
	}

	public void visit(FuncCall funcCall) {
		Obj func = funcCall.getDesignator().obj;

		if (currentMethodLevel == 1)
			currentNumOfParameters = 0;
		if (func != Tab.noObj) {
			if (Obj.Meth != func.getKind()) {
				report_error(
						"Semanticka greska na liniji " + funcCall.getLine() + ", " + func.getName() + " nije funkcija.",
						null);
			} else if (func.getLevel() != (actuals.size() - currentNumOfParameters)) {
				report_error("Semanticka greska na liniji " + funcCall.getLine()
						+ ": Neodgovarajuci broj stvarnih argumenata prilikom poziva funkcije '" + func.getName()
						+ "'.", null);
			} else {
				int i = 0;
				for (Obj o : func.getLocalSymbols()) {
					if (++i > func.getLevel())
						break;
					Struct actual = actuals.pop();
					Struct formal = o.getType();
					if (!assignableTo(actual, formal)) {
						report_error("Semanticka greska na liniji " + funcCall.getLine()
								+ ": Neodgovarajuci tip stvarnog argumenta prilikom poziva funkcije '" + func.getName()
								+ "' za parametar '" + o.getName() + "'.", null);
					}
				}
				currentMethodLevel--;
			}
		}
	}

	public void visit(DesignatorDec dec) {
		if (dec.getDesignator().obj.getKind() != Obj.Var && dec.getDesignator().obj.getKind() != Obj.Elem)
			report_error("Semanticka greska na liniji " + dec.getLine()
					+ ": Izraz sa leve strane mora biti promenljiva ili element niza", null);
		if (dec.getDesignator().obj.getType() != Tab.intType) {
			report_error("Semanticka greska na liniji " + dec.getLine()
					+ ": Operator '++' se moze primeniti samo nad promenljivom tipa int.", null);
		}
	}

	public void visit(DesignatorInc inc) {
		if (inc.getDesignator().obj.getKind() != Obj.Var && inc.getDesignator().obj.getKind() != Obj.Elem)
			report_error("Semanticka greska na liniji " + inc.getLine()
					+ ": Izraz sa leve strane mora biti promenljiva ili element niza", null);
		if (inc.getDesignator().obj.getType() != Tab.intType) {
			report_error("Semanticka greska na liniji " + inc.getLine()
					+ ": Operator '++' se moze primeniti samo nad promenljivom tipa int.", null);
		}
	}

	public void visit(SingleFactor singleFactor) {
		singleFactor.struct = singleFactor.getFactor().struct;
	}

	public void visit(VariableFactor var) {
		var.struct = var.getDesignator().obj.getType();
	}

	public void visit(ExprFactor exprFactor) {
		exprFactor.struct = exprFactor.getExpr().struct;
	}

	public void visit(ConstantFactor constant) {
		constant.struct = constant.getConstValue().obj.getType();
	}

	public void visit(RelopFact relopFact) {
		Struct s1 = relopFact.getExpr().struct;
		Struct s2 = relopFact.getExpr1().struct;
		if (!assignableTo(s1, s2)) {
			report_error("Semanticka greska na liniji " + relopFact.getLine()
					+ ": Nekompatibilni izrazi u relacionom izrazu.", null);
		}
		if (s1.getKind() >= 6 && s2.getKind() >= 6)
			if (!compatibleEnums(s1, s2))
				report_error("Semanticka greska na liniji " + relopFact.getLine()
				+ ": Nekompatibilni izrazi u relacionom izrazu.", null);
	}

	public void visit(NewFactorArray newFactorArray) {
		if (Tab.find(newFactorArray.getType().getTypeName()) == null)
			report_error("Nepostojeci tip na liniji " + newFactorArray.getLine(), null);
		else {
			newFactorArray.struct = new Struct(Struct.Array, newFactorArray.getType().struct);
		}
	}

	public void visit(SingleActualParameter singleActualParameter) {
		actuals.push(singleActualParameter.getExpr().struct);
	}

	public void visit(ActualParametersList actualParametersList) {
		actuals.push(actualParametersList.getExpr().struct);
	}

	public void visit(StartOfFunc startOfFunc) {
		if (startOfFunc.getParent().getClass() == FuncCall.class)
			report_info("Poziv funkcije " + ((FuncCall) startOfFunc.getParent()).getDesignator().obj.getName()
					+ " na liniji " + startOfFunc.getParent().getLine(), null);
		if (startOfFunc.getParent().getClass() == FuncCallFactor.class)
			report_info("Poziv funkcije " + ((FuncCallFactor) startOfFunc.getParent()).getDesignator().obj.getName()
					+ " na liniji " + startOfFunc.getParent().getLine(), null);
		currentNumOfParameters = actuals.size();
		currentMethodLevel++;
	}
	
	private boolean enumContains(Obj enumObj, Obj designator) {
		Optional<Obj> result = enumObj.getLocalSymbols().stream()
				.filter(value -> value.getName().equals(designator.getName()))
				.findFirst();
		if (result.isEmpty()) {
			return false;			
		}
		int foundObjValue = result.get().getAdr();
		designator.setAdr(foundObjValue);
		return true;
	}
	
	public static boolean assignableTo(Struct destination, Struct source) {
		return source.assignableTo(destination) || (destination == Tab.intType && source.getKind() >= 6) || (source == Tab.intType && destination.getKind() >= 6);
	}
	
	public static boolean compatibleEnums(Struct destination, Struct source) {
		return source.getKind() == destination.getKind();
	}
	
	public boolean passed() {
		return !errorDetected;
	}
	
	public void visit(EnumValues enumValues) {
		Obj obj = Tab.find(enumValues.getEnumName());
		if ((obj.getKind() != Obj.Type && obj.getType().getKind() != Struct.Enum)) 
			report_error(enumValues.getEnumName() + " nije enum", enumValues);
	}
}