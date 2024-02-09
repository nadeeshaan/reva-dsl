package reva.ide.utils.switches;

import com.google.inject.Inject;
import java.util.Map;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalCreator;
import reva.ide.contentassist.providers.RevaAbstractContentProposalProvider;
import reva.ide.contentassist.switches.ContentAssistComposedSwitch;
import reva.ide.contentassist.switches.RevaDslContentAssistSwitch;
import reva.ide.contentassist.switches.XBaseContentAssistSwitch;

public class ComposedSwitchFactory {
  @Inject private IdeContentProposalCreator proposalCreator;

  @Inject
  private Map<Class<? extends EObject>, RevaAbstractContentProposalProvider> proposalProviders;

  public ContentAssistComposedSwitch getContentAssistComposedSwitch(
      IIdeContentProposalAcceptor acceptor, ContentAssistContext context) {
    XBaseContentAssistSwitch xBaseContentAssistSwitch =
        new XBaseContentAssistSwitch(proposalProviders, context, acceptor, proposalCreator);
    RevaDslContentAssistSwitch revaDslContentAssistSwitch =
        new RevaDslContentAssistSwitch(proposalProviders, context, acceptor, proposalCreator);

    return new ContentAssistComposedSwitch(xBaseContentAssistSwitch, revaDslContentAssistSwitch);
  }
}
