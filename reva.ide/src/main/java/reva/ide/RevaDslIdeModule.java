/*
 * generated by Xtext 2.33.0
 */
package reva.ide;

import com.google.inject.Binder;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ide.server.contentassist.ContentAssistService;
import org.eclipse.xtext.xbase.ide.contentassist.XbaseIdeContentProposalProvider;
import reva.ide.contentassist.RevaContentProposalProvider;
import reva.ide.contentassist.providers.PrintExpressionContentProposalProvider;
import reva.ide.contentassist.providers.RevaAbstractContentProposalProvider;
import reva.revaDsl.PrintExpression;

/** Use this class to register ide components. */
public class RevaDslIdeModule extends AbstractRevaDslIdeModule {

  @Override
  public void configure(Binder binder) {
    super.configure(binder);
    bindContentAssistProviders(binder);
  }

  public Class<? extends ContentAssistService> bindContentAssistService() {
    return ContentAssistService.class;
  }

  public Class<? extends XbaseIdeContentProposalProvider> bindXbaseIdeContentProposalProvider() {
    return RevaContentProposalProvider.class;
  }

  private void bindContentAssistProviders(Binder binder) {
    MapBinder<Class<? extends EObject>, RevaAbstractContentProposalProvider>
        contentAssistProvidersBinder =
            MapBinder.newMapBinder(binder, new TypeLiteral<>() {}, new TypeLiteral<>() {});

    contentAssistProvidersBinder
        .addBinding(PrintExpression.class)
        .toInstance(new PrintExpressionContentProposalProvider());
  }
}
