package reva.compiler;

import org.eclipse.xtext.xbase.XExpression;
import org.eclipse.xtext.xbase.compiler.XbaseCompiler;
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable;

import reva.revaDsl.PrintExpression;

public class RevaDslCompiler extends XbaseCompiler {

	@Override
	protected void doInternalToJavaStatement(XExpression obj, ITreeAppendable a, boolean isReferenced) {
		if (obj instanceof PrintExpression) {
			doInternalToJavaStatement((PrintExpression) obj, a, isReferenced);
		} else {
			super.doInternalToJavaStatement(obj, a, isReferenced);
		}
	}

	private void doInternalToJavaStatement(PrintExpression printExpr, ITreeAppendable a, boolean isReferenced) {
		internalToJavaStatement(printExpr.getExpression(), a, true);
		newLine(a);
		a.append("System.out.println(");
		internalToJavaExpression(printExpr.getExpression(), a);
		a.append(");");
	}

	@Override
	protected boolean internalCanCompileToJavaExpression(XExpression expression, ITreeAppendable appendable) {
		if (expression instanceof PrintExpression) {
			return false;
		} else {
			return super.internalCanCompileToJavaExpression(expression, appendable);
		}
	}

	private void newLine(ITreeAppendable a) {
		a.newLine();
	}

}
