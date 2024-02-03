package reva.ide.utils.switches;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.util.XbaseSwitch;
import reva.ide.contentassist.providers.RevaAbstractContentProposalProvider;
import reva.revaDsl.util.RevaDslSwitch;

public class XBaseCustomSwitch extends XbaseSwitch<List<EObject>> {
  @Override
  public List<EObject> defaultCase(EObject object) {
    return Collections.emptyList();
  }
}
