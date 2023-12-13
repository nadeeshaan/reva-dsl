package reva.compiler;

import org.eclipse.xtext.xbase.XExpression;
import org.eclipse.xtext.xbase.typesystem.computation.ITypeComputationState;
import org.eclipse.xtext.xbase.typesystem.computation.XbaseTypeComputer;

import reva.revaDsl.PrintExpression;

public class RevaDslTypeComputer extends XbaseTypeComputer {

	@Override
	public void computeTypes(XExpression expr, ITypeComputationState state) {
		if (expr instanceof PrintExpression) {
			computeTypesOf((PrintExpression) expr, state);
		} else {
			super.computeTypes(expr, state);
		}
	}

	private void computeTypesOf(PrintExpression printExpr, ITypeComputationState state) {
		state.withNonVoidExpectation().computeTypes(printExpr.getExpression());
		state.acceptActualType(getRawTypeForName(String.class, state));
	}
}
