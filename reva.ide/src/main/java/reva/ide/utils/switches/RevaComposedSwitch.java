package reva.ide.utils.switches;

import java.util.Collections;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ComposedSwitch;

/**
 * Composed switch for the Reva Language. Here we consider {@link List<EObject>} as the generic
 * type. The reason for that is to iterate over all the AST EObjects. Any switch implementation
 * should provide two Switch implementations as following.
 *
 * <pre>
 *    1. Switch implementation extending from XBaseSwitch.
 *        - Use XBaseCustomSwitch to be convenient
 *    2. Switch implementation extending from RevaDslSwitch
 *        - Use RevaDslCustomSwitch to be convenient
 * </pre>
 *
 * In each of the caseXXX method, should return the list of child EObject elements to be visited
 * next.
 */
public class RevaComposedSwitch extends ComposedSwitch<List<EObject>> {
  public RevaComposedSwitch(
      XBaseCustomSwitch xBaseCustomSwitch, RevaDslCustomSwitch revaDslCustomSwitch) {
    super(List.of(xBaseCustomSwitch, revaDslCustomSwitch));
  }

  @Override
  public List<EObject> defaultCase(EObject eObject) {
    return Collections.emptyList();
  }
}
