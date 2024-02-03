package reva.ide.contentassist.utils;

import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry;

/** Contains the utilities associated with the content assist operation. */
public class ContentAssistUtils {

  /**
   * Get the content assist entry given the parameters to populate.
   *
   * @param context current content assist context
   * @param label label for the completion item
   * @param insertText to be inserted in the source, when the particular item is selected
   * @param description description to be shown in the description panel
   * @param kind of the particular proposal/ completion item
   * @return the generated {@link ContentAssistEntry}
   */
  public static ContentAssistEntry getContentAssistEntry(
      ContentAssistContext context,
      String label,
      String insertText,
      String description,
      String kind) {
    ContentAssistEntry entry = new ContentAssistEntry();
    entry.setPrefix(context.getPrefix());
    entry.setProposal(insertText);
    entry.setLabel(label);
    entry.setKind(kind);
    entry.setDescription(description);

    return entry;
  }

  /**
   * Get the content assist entry given the parameters to populate.
   *
   * @param context current content assist context
   * @param label label for the completion item
   * @param insertText to be inserted in the source, when the particular item is selected
   * @param description description to be shown in the description panel
   * @return the generated {@link ContentAssistEntry}
   */
  public static ContentAssistEntry getContentAssistEntry(
      ContentAssistContext context, String label, String insertText, String description) {
    return getContentAssistEntry(context, label, insertText, description, null);
  }
}
