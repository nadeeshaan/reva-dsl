/*
 * generated by Xtext 2.33.0
 */
package reva.ide;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.xtext.util.Modules2;
import reva.RevaDslRuntimeModule;
import reva.RevaDslStandaloneSetup;

/**
 * Initialization support for running Xtext languages as language servers.
 */
public class RevaDslIdeSetup extends RevaDslStandaloneSetup {

	@Override
	public Injector createInjector() {
		return Guice.createInjector(Modules2.mixin(new RevaDslRuntimeModule(), new RevaDslIdeModule()));
	}
	
}
