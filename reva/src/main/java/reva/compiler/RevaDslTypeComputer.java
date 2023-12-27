package reva.compiler;

import org.eclipse.xtext.xbase.XBlockExpression;
import org.eclipse.xtext.xbase.XExpression;
import org.eclipse.xtext.xbase.typesystem.computation.ITypeComputationState;
import org.eclipse.xtext.xbase.typesystem.computation.XbaseTypeComputer;
import org.eclipse.xtext.xbase.typesystem.conformance.ConformanceFlags;

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

	private void computeTypesOf(PrintExpression expr, ITypeComputationState state) {
		if (expr.getExpression() instanceof XBlockExpression) {
			var children = ((XBlockExpression) expr.getExpression()).getExpressions();
			if (children.isEmpty()) {
				for (var expectation : state.getExpectations()) {
					var expectedType = expectation.getExpectedType();
					if (expectedType != null && expectedType.isPrimitiveVoid()) {
						expectation.acceptActualType(expectedType, ConformanceFlags.CHECKED_SUCCESS);
					} else {
						expectation.acceptActualType(expectation.getReferenceOwner().newAnyTypeReference(),
								ConformanceFlags.UNCHECKED);
					}
				}
			} else {
				state.withinScope(expr);
				for (int i = 0; i < children.size() - 1; i++) {
					var expression = children.get(i);
					var expressionState = state.withoutExpectation(); // no expectation
					expressionState.computeTypes(expression);
					addLocalToCurrentScope(expression, state);
				}
				XExpression lastExpression = children.get(children.size() - 1);
				for (var expectation : state.getExpectations()) {
					var expectedType = expectation.getExpectedType();
					if (expectedType != null && expectedType.isPrimitiveVoid()) {
						var expressionState = state.withoutExpectation(); // no expectation
						expressionState.computeTypes(lastExpression);
						addLocalToCurrentScope(lastExpression, state);
						expectation.acceptActualType(getPrimitiveVoid(state), ConformanceFlags.CHECKED_SUCCESS);
					} else {
						state.computeTypes(lastExpression);
						addLocalToCurrentScope(lastExpression, state);
					}
				}
			}
		} else {
			state.withNonVoidExpectation().computeTypes(expr.getExpression());
			state.acceptActualType(getPrimitiveVoid(state));
		}
	}

	private void computeTypesOf(RepeatExpression repeat, ITypeComputationState state) {
		// Expression was already resolved
		// state.withNonVoidExpectation().computeTypes(repeat.getExpression());
		state.computeTypes(repeat.getExpression());
		state.acceptActualType(getPrimitiveVoid(state));
	}

}
