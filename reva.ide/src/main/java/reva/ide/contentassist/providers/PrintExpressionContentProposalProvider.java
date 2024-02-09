package reva.ide.contentassist.providers;

import com.google.inject.Inject;
import java.util.Optional;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry;
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalCreator;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalPriorities;
import org.eclipse.xtext.nodemodel.INode;
import reva.ide.contentassist.utils.ContentAssistUtils;
import reva.ide.utils.nodes.NodeUtils;
import reva.revaDsl.PrintExpression;
import reva.services.RevaDslGrammarAccess;

public class PrintExpressionContentProposalProvider extends RevaAbstractContentProposalProvider {
  @Inject RevaDslGrammarAccess grammarAccess;

  @Inject IdeContentProposalPriorities proposalPriorities;

  @Override
  protected Boolean create(
      ContentAssistContext context,
      IIdeContentProposalAcceptor acceptor,
      IdeContentProposalCreator proposalCreator) {
    ContentAssistEntry contentAssistEntry =
        ContentAssistUtils.getContentAssistEntry(
            context, "\"\"", "\"${0}\"", null, ContentAssistEntry.KIND_SNIPPET);
    acceptor.accept(contentAssistEntry, proposalPriorities.getDefaultPriority(contentAssistEntry));

    return true;
  }

  @Override
  protected boolean canCreateProposals(ContentAssistContext context) {
    EObject currentModel = context.getCurrentModel();
    if (!(currentModel instanceof PrintExpression)) {
      return false;
    }

    Optional<INode> nodeBeforeOffset =
        NodeUtils.getNonEmptyNodeBeforeOffset(context.getRootNode(), context.getOffset());

    if (nodeBeforeOffset.isEmpty()) {
      return false;
    }

    String nodeText = nodeBeforeOffset.get().getText();
    return grammarAccess.getPrintExpressionAccess().getPrintKeyword_1().getValue().equals(nodeText);
  }
}
