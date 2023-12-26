package reva.compiler;

import org.eclipse.xtext.xbase.XExpression;
import org.eclipse.xtext.xbase.typesystem.computation.ITypeComputationState;
import org.eclipse.xtext.xbase.typesystem.computation.XbaseTypeComputer;

import reva.revaDsl.PrintExpression;
import reva.revaDsl.RepeatExpression;

public class RevaDslTypeComputer extends XbaseTypeComputer {

	@Override
	public void computeTypes(XExpression expr, ITypeComputationState state) {
		if (expr instanceof PrintExpression) {
			computeTypesOf((PrintExpression) expr, state);
		} else if (expr instanceof RepeatExpression) {
			computeTypesOf((RepeatExpression) expr, state);
		} else {
			super.computeTypes(expr, state);
		}
	}

	private void computeTypesOf(PrintExpression printExpr, ITypeComputationState state) {
//		if (printExpr.getExpression() instanceof XStringLiteral
//				&& ((XStringLiteral) printExpr.getExpression()).getValue().isEmpty()) {
//			state.addDiagnostic(new EObjectDiagnosticImpl(Severity.WARNING, //
//					null, //
//					"Print statement is empty", //
//					printExpr, //
//					RevaDslPackage.Literals.PRINT_EXPRESSION__EXPRESSION, //
//					-1, //
//					Strings.EMPTY_ARRAY));
//		}

		state.withNonVoidExpectation().computeTypes(printExpr.getExpression());
		state.acceptActualType(getPrimitiveVoid(state));
	}

	private void computeTypesOf(RepeatExpression repeat, ITypeComputationState state) {
		// Expression was already resolved
		//state.withNonVoidExpectation().computeTypes(repeat.getExpression());
		state.computeTypes(repeat.getExpression());
		state.acceptActualType(getPrimitiveVoid(state));
	}

}
