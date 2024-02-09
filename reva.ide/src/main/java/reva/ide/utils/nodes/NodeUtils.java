package reva.ide.utils.nodes;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.nodemodel.BidiTreeIterator;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.impl.HiddenLeafNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.xbase.XExpression;

import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static reva.ide.utils.nodes.NodeOffsetHelper.getEndOffset;
import static reva.ide.utils.nodes.NodeOffsetHelper.getStartOffset;

public class NodeUtils {

    public static Position getPosition(Document document, int offset) {
        int docLength = document.getContents().length();
        return offset > docLength ? document.getPosition(docLength) : document.getPosition(offset);
    }

    public static Range getRange(Document document, XExpression expression) {
        // TODO: Should we consider the token range only? Ex: anyOther() -> this will return range
        // including the parenthesis
        Position startPosition = getPosition(document, getStartOffset(expression));
        Position endPosition = getPosition(document, getEndOffset(expression));

        return new Range(startPosition, endPosition);
    }

    public static List<INode> getNodesList(ICompositeNode rootNode) {
        BidiTreeIterator<INode> treeIterator = rootNode.getAsTreeIterable().iterator();

        return StreamSupport.stream(
                        Spliterators.spliteratorUnknownSize(treeIterator, Spliterator.ORDERED), false)
                .collect(Collectors.toList());
    }

    public static Optional<INode> getNonEmptyNodeBeforeOffset(ICompositeNode rootNode, int offset) {
        List<INode> iNodeList = getNodesList(rootNode);
        Optional<INode> nodeAtOffset = getNodeAtOffset(iNodeList, offset);

        if (nodeAtOffset.isEmpty()) {
            return Optional.empty();
        }

        int nodeIndex = iNodeList.indexOf(nodeAtOffset.get()) - 1;
        INode nonEmptyNodeBeforeOffset = null;

        while (nodeIndex >= 0) {
            INode iNode = iNodeList.get(nodeIndex);

            if (iNode.getText().isBlank()) {
                nodeIndex--;
                continue;
            }

            nonEmptyNodeBeforeOffset = iNode;
            break;
        }

        return Optional.ofNullable(nonEmptyNodeBeforeOffset);
    }

    public static List<ILeafNode> getLeafNodesOf(INode compositeNode) {
        return StreamSupport.stream(compositeNode.getLeafNodes().spliterator(), false)
                .collect(Collectors.toList());
    }

    public static Optional<INode> findNonLeafNodeBeforeOffset(EObject startEObject, int offset) {
        ICompositeNode currentNode = NodeModelUtils.getNode(startEObject);

        // currentNode can be null in contexts where there are incomplete syntax. For more info, refer
        // the Java docs for NodeModelUtils.getNode
        if (currentNode == null) {
            return Optional.empty();
        }

        List<ILeafNode> filteredLeafNodes = getLeafNodesOf(currentNode);

    /*
    Traverse the leaf nodes in the reverse order to find the first non-hidden leaf node existing before the offset
     */
        for (int i = filteredLeafNodes.size() - 1; i > 0; i--) {
            ILeafNode iLeafNode = filteredLeafNodes.get(i);
            if (iLeafNode instanceof HiddenLeafNode) {
                continue;
            }
            if (iLeafNode.getEndOffset() < offset) {
                return Optional.of(iLeafNode);
            }
        }

        return Optional.empty();
    }

    public static Optional<INode> getNodeAtOffset(List<INode> iNodeList, int offset) {
        if (iNodeList.size() < 2) {
            // Because of the first node is always the root node
            return Optional.empty();
        }
        INode nodeFound = null;

        for (INode iNode : iNodeList.subList(1, iNodeList.size())) {
            if (!iNode.getText().isBlank()) {
                nodeFound = iNode;
            }

            int endOffset = iNode.getEndOffset();

            if (offset <= endOffset) {
                break;
            }
        }

        return Optional.ofNullable(nodeFound);
    }
}
