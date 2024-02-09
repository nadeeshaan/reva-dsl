package reva.ide.contentassist.switches;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalCreator;
import reva.ide.contentassist.providers.RevaAbstractContentProposalProvider;

public interface IContentAssistSwitch {
  default boolean createProposals(
      EObject eObject,
      Map<Class<? extends EObject>, RevaAbstractContentProposalProvider> proposalProviders,
      ContentAssistContext context,
      IIdeContentProposalAcceptor acceptor,
      IdeContentProposalCreator proposalCreator) {
    Class<? extends EObject> eObjectClass = eObject.getClass();

    List<Class<?>> interfaces =
        Stream.of(eObjectClass.getInterfaces()).filter(proposalProviders::containsKey).toList();

    if (interfaces.isEmpty()) {
      return false;
    }

    RevaAbstractContentProposalProvider proposalProvider = proposalProviders.get(interfaces.get(0));
    return proposalProvider.createProposals(context, acceptor, proposalCreator);
  }

  boolean createProposals(EObject eObject);

  boolean customProposalCreationSuccess();
}
