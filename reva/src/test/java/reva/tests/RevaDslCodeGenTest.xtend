package reva.tests

import com.google.inject.Inject
import java.io.IOException
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.xbase.testing.CompilationTestHelper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith

@ExtendWith(InjectionExtension)
@InjectWith(RevaDslInjectorProvider)
class RevaDslCodeGenTest {

	@Inject
	extension CompilationTestHelper

	@Test
	def void test() {
		'''print "Hello, World 123!"'''.compile
	}

	def compile(CharSequence source) throws IOException {
		compile(source, [
			println(it.singleGeneratedCode)
		])
	}

}
