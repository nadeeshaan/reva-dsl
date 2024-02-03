package reva.ide.utils.switches;

import org.eclipse.emf.ecore.EObject;
import reva.revaDsl.util.RevaDslSwitch;

import java.util.Collections;
import java.util.List;

public class RevaDslCustomSwitch extends RevaDslSwitch<List<EObject>> {
  @Override
  public List<EObject> defaultCase(EObject object) {
    return Collections.emptyList();
  }
}
