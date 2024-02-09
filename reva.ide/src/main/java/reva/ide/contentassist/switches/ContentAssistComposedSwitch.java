package reva.ide.contentassist.switches;

import java.util.Collections;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import reva.ide.utils.switches.RevaComposedSwitch;

public class ContentAssistComposedSwitch extends RevaComposedSwitch {
  private final XBaseContentAssistSwitch xBaseContentAssistSwitch;
  private final RevaDslContentAssistSwitch revaDslContentAssistSwitch;

  public ContentAssistComposedSwitch(
      XBaseContentAssistSwitch xBaseContentAssistSwitch,
      RevaDslContentAssistSwitch revaDslContentAssistSwitch) {
    super(xBaseContentAssistSwitch, revaDslContentAssistSwitch);
    this.xBaseContentAssistSwitch = xBaseContentAssistSwitch;
    this.revaDslContentAssistSwitch = revaDslContentAssistSwitch;
  }

  @Override
  public List<EObject> doSwitch(EObject eObject) {
    List<EObject> eObjectsToVisit = super.doSwitch(eObject);

    for (EObject objectToVisit : eObjectsToVisit) {
      super.doSwitch(objectToVisit);
    }

    return Collections.emptyList();
  }

  public boolean computeCustomProposals(EObject eObject) {
    doSwitch(eObject);

    // At least one of the switches could find a custom proposal provider to compute the proposals successfully.
    return this.xBaseContentAssistSwitch.customProposalCreationSuccess()
        || this.revaDslContentAssistSwitch.customProposalCreationSuccess();
  }
}
