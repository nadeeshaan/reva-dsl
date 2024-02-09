package reva.ide.utils.nodes;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

public class NodeOffsetHelper {
    public static int getStartOffset(EObject eObject) {
        return NodeModelUtils.getNode(eObject).getOffset();
    }

    public static int getEndOffset(EObject eObject) {
        return NodeModelUtils.getNode(eObject).getEndOffset();
    }

    public static boolean isOffsetInsideEObject(EObject eObject, int offset) {
        ICompositeNode node = NodeModelUtils.getNode(eObject);

        return offset > node.getOffset() && offset < node.getEndOffset();
    }
}
