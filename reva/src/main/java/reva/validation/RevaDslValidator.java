/*
 * generated by Xtext 2.33.0
 */
package reva.validation;

import org.eclipse.xtext.xbase.XExpression;

import reva.revaDsl.PrintExpression;
import reva.revaDsl.RepeatExpression;

/**
 * This class contains custom validation rules.
 *
 * See
 * https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
public class RevaDslValidator extends AbstractRevaDslValidator {

	@Override
	protected boolean isValueExpectedRecursive(XExpression expr) {
		return expr.eContainer() instanceof PrintExpression //
				|| expr.eContainer() instanceof RepeatExpression //
				|| super.isValueExpectedRecursive(expr);
	}

}
