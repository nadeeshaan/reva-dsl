package reva.compiler;

import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.XBlockExpression;
import org.eclipse.xtext.xbase.XExpression;
import org.eclipse.xtext.xbase.XNumberLiteral;
import org.eclipse.xtext.xbase.compiler.XbaseCompiler;
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable;

import reva.revaDsl.PrintExpression;
import reva.revaDsl.RepeatExpression;

public class RevaDslCompiler extends XbaseCompiler {

	private static final String SPACE = " ";

	@Override
	protected void doInternalToJavaStatement(XExpression obj, ITreeAppendable a, boolean isReferenced) {
		if (obj instanceof PrintExpression) {
			doInternalToJavaStatement((PrintExpression) obj, a, isReferenced);
		} else if (obj instanceof RepeatExpression) {
			doInternalToJavaStatement((RepeatExpression) obj, a, isReferenced);
		} else {
			super.doInternalToJavaStatement(obj, a, isReferenced);
		}
	}

	private void doInternalToJavaStatement(PrintExpression printExpr, ITreeAppendable a, boolean isReferenced) {
		if (printExpr.getExpression() instanceof XBlockExpression) {
			var expr = (XBlockExpression) printExpr.getExpression();
			a = a.trace(expr, false);
			if (expr.getExpressions().isEmpty())
				return;
			if (expr.getExpressions().size() == 1) {
				internalToJavaStatement(expr.getExpressions().get(0), a, isReferenced);
				return;
			}
			if (isReferenced)
				declareSyntheticVariable(expr, a);
			boolean needsBraces = isReferenced || !bracesAreAddedByOuterStructure(expr);
			if (needsBraces) {
				a.newLine().append("{").increaseIndentation();
				a.openPseudoScope();
			}
			final EList<XExpression> expressions = expr.getExpressions();
			for (int i = 0; i < expressions.size(); i++) {
				XExpression ex = expressions.get(i);
				if (i < expressions.size() - 1) {
					internalToJavaStatement(ex, a, false);
				} else {
					internalToJavaStatement(ex, a, isReferenced);
					if (isReferenced) {
						a.newLine().append(getVarName(expr, a)).append(" = ");
						internalToConvertedExpression(ex, a, getLightweightType(expr));
						a.append(";");
					}
				}
			}
			if (needsBraces) {
				a.closeScope();
				a.decreaseIndentation().newLine().append("}");
			}
		} else {
			internalToJavaStatement(printExpr.getExpression(), a, true);
			newLine(a);
			a.append("System.out.println(");
			internalToJavaExpression(printExpr.getExpression(), a);
			a.append(");");
		}
	}

	private void doInternalToJavaStatement(RepeatExpression repeatExpression, ITreeAppendable a, boolean isReferenced) {
		var count = ((XNumberLiteral) repeatExpression.getCount()).getValue();
		newLine(a);

		a.append("for(int i = 0; i < " + count + "; i++)");
		openBlock(a);
		a.openPseudoScope();

		internalToJavaStatement(repeatExpression.getExpression(), a, true);
		if (isReferenced) {
			internalToJavaExpression(repeatExpression.getExpression(), a);
		}

		a.closeScope();
		closeBlock(a);
	}

	@Override
	protected boolean internalCanCompileToJavaExpression(XExpression expression, ITreeAppendable appendable) {
		if (expression instanceof PrintExpression //
				|| expression instanceof RepeatExpression) {
			return false;
		} else {
			return super.internalCanCompileToJavaExpression(expression, appendable);
		}
	}

	private void newLine(ITreeAppendable a) {
		a.newLine();
	}

	private void openBlock(ITreeAppendable appendable) {
		appendable.append(SPACE).append("{");
		appendable.increaseIndentation();
	}

}
