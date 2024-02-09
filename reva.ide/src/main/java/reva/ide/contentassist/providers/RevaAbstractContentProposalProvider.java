package reva.ide.contentassist.providers;

import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalCreator;
import org.eclipse.xtext.xbase.ide.contentassist.XbaseIdeContentProposalProvider;

public abstract class RevaAbstractContentProposalProvider extends XbaseIdeContentProposalProvider {

  public Boolean createProposals(
      ContentAssistContext context,
      IIdeContentProposalAcceptor acceptor,
      IdeContentProposalCreator proposalCreator) {
    if (!canCreateProposals(context)) {
      return null;
    }

    return create(context, acceptor, proposalCreator);
  }

  protected abstract Boolean create(
      ContentAssistContext context,
      IIdeContentProposalAcceptor acceptor,
      IdeContentProposalCreator proposalCreator);

  protected abstract boolean canCreateProposals(ContentAssistContext context);
}
