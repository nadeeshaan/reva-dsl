package reva.compiler;

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
			super.doInternalToJavaStatement(printExpr.getExpression(), a, isReferenced);
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
