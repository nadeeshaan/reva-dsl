package reva.ide.contentassist.switches;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalCreator;
import reva.ide.contentassist.providers.RevaAbstractContentProposalProvider;
import reva.ide.utils.switches.RevaDslCustomSwitch;
import reva.revaDsl.PrintExpression;
import reva.revaDsl.RepeatExpression;

public class RevaDslContentAssistSwitch extends RevaDslCustomSwitch
    implements IContentAssistSwitch {
  private final Map<Class<? extends EObject>, RevaAbstractContentProposalProvider>
      proposalProviders;

  private final ContentAssistContext context;

  private final IIdeContentProposalAcceptor acceptor;

  private final IdeContentProposalCreator proposalCreator;
  
  private boolean isCustomProposalCreationSuccessful = false;
  
  public RevaDslContentAssistSwitch(
      Map<Class<? extends EObject>, RevaAbstractContentProposalProvider> proposalProviders,
      ContentAssistContext context,
      IIdeContentProposalAcceptor acceptor,
      IdeContentProposalCreator proposalCreator) {
    this.proposalProviders = proposalProviders;
    this.context = context;
    this.acceptor = acceptor;
    this.proposalCreator = proposalCreator;
  }

  @Override
  public List<EObject> casePrintExpression(PrintExpression object) {
    isCustomProposalCreationSuccessful = createProposals(object);

    return Collections.emptyList();
  }

  @Override
  public List<EObject> caseRepeatExpression(RepeatExpression object) {
    createProposals(object);

    return Collections.emptyList();
  }

  @Override
  public boolean createProposals(EObject eObject) {
    return createProposals(eObject, proposalProviders, context, acceptor, proposalCreator);
  }

  @Override
  public boolean customProposalCreationSuccess() {
    return isCustomProposalCreationSuccessful;
  }
}
