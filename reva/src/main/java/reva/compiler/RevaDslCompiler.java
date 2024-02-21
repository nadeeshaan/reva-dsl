package reva.compiler;

import org.eclipse.xtext.xbase.XBlockExpression;
import org.eclipse.xtext.xbase.XExpression;
import org.eclipse.xtext.xbase.XNumberLiteral;
import org.eclipse.xtext.xbase.compiler.XbaseCompiler;
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable;
import org.eclipse.xtext.xbase.typesystem.references.LightweightTypeReference;

import reva.revaDsl.PrintExpression;
import reva.revaDsl.RepeatExpression;
import reva.revaDsl.XVariableDeclaration;

public class RevaDslCompiler extends XbaseCompiler {

	private static final String SPACE = " ";

	@Override
	protected void doInternalToJavaStatement(XExpression obj, ITreeAppendable a, boolean isReferenced) {
		if (obj instanceof PrintExpression) {
			doInternalToJavaStatement((PrintExpression) obj, a, isReferenced);
		} else if (obj instanceof RepeatExpression) {
			doInternalToJavaStatement((RepeatExpression) obj, a, isReferenced);
		} else if (obj instanceof XVariableDeclaration) {
			doInternalToJavaStatement((XVariableDeclaration) obj, a, isReferenced);
		} else {
			super.doInternalToJavaStatement(obj, a, isReferenced);
		}
	}

	private void doInternalToJavaStatement(XVariableDeclaration expr, ITreeAppendable a, boolean isReferenced) {
		if (expr.getRight() != null) {
			internalToJavaStatement(expr.getRight(), a, true);
		}

		a.newLine();
		LightweightTypeReference type = appendVariableTypeAndName(expr, a);
		a.append(" = ");
		if (expr.getRight() != null) {
			internalToConvertedExpression(expr.getRight(), a, type);
		} else {
			appendDefaultLiteral(a, type);
		}
		a.append(";");
	}

	protected LightweightTypeReference appendVariableTypeAndName(XVariableDeclaration varDeclaration,
			ITreeAppendable appendable) {
		if (!varDeclaration.isWriteable()) {
			appendable.append("final ");
		}
		LightweightTypeReference type = getLightweightType(varDeclaration.getRight());
		if (type.isAny()) {
			type = getTypeForVariableDeclaration(varDeclaration.getRight());
		}
		appendable.append(type);
		appendable.append(" ");
		appendable.append(appendable.declareVariable(varDeclaration, makeJavaIdentifier(varDeclaration.getName())));
		return type;
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
