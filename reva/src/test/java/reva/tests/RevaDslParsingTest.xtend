/*
 * generated by Xtext 2.33.0
 */
package reva.tests

import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.xbase.XBlockExpression
import org.eclipse.xtext.xbase.testing.CompilationTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith

@ExtendWith(InjectionExtension)
@InjectWith(RevaDslInjectorProvider)
class RevaDslParsingTest {
	@Inject
	ParseHelper<XBlockExpression> parseHelper
	
	@Inject
	CompilationTestHelper compilationTestHelper;
	
	@Test
	def void loadModel() {
		println(compilationTestHelper)
		
		val result = parseHelper.parse('''
			print "Hello!"
		''')
		Assertions.assertNotNull(result)
		val errors = result.eResource.errors
		Assertions.assertTrue(errors.isEmpty, '''Unexpected errors: «errors.join(", ")»''')
	}
}