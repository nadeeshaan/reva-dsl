/*
 * generated by Xtext 2.33.0
 */
package reva;

import org.eclipse.xtext.xbase.compiler.XbaseCompiler;
import org.eclipse.xtext.xbase.typesystem.computation.ITypeComputer;

import reva.compiler.RevaDslCompiler;
import reva.compiler.RevaDslTypeComputer;

public class RevaDslRuntimeModule extends AbstractRevaDslRuntimeModule {

	public Class<? extends XbaseCompiler> bindXbaseCompiler() {
		return RevaDslCompiler.class;
	}

	public Class<? extends ITypeComputer> bindITypeComputer() {
		return RevaDslTypeComputer.class;
	}

}
