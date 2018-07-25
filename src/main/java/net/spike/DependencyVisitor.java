package net.spike;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.javascript.jscomp.NodeUtil;
import com.google.javascript.rhino.Node;

public class DependencyVisitor implements NodeUtil.Visitor {

    public List<String> importStrings = new ArrayList<>();
    public List<String> getterStrings = new ArrayList<>();
    public List<Node> importNodes = new ArrayList<>();
    public List<Node> getterNodes = new ArrayList<>();
    public Map<String, String> getterContent = new HashMap<>();
    public Node templateTextNode;

    @Override
    public void visit(Node node) {
        switch (node.getToken()) {
            case IMPORT:
                addImport(node);
                break;
            case GETTER_DEF:
                addGetter(node);
                break;
        }
    }

    private void addGetter(Node node) {
        getterNodes.add(node);
        getterStrings.add(node.getString());
        // There should be just one of these
        if (node.getString().equals("template")) {
            templateTextNode = getTextNode(node);
            String content = templateTextNode.getString();
            getterContent.put(node.getString(), content);
        }
    }

    private void addImport(Node node) {
        importNodes.add(node);
        if (node.hasChildren()) {
           Node child = getTextNode(node);
           importStrings.add(child.getString());
        }
    }

    private Node getTextNode(Node node) {
        Node child = node.getFirstChild();
        while (child.getFirstChild() != null || child.getNext() != null) {
            if (child.getNext() == null) {
                child = child.getFirstChild();
            } else {
                child = child.getNext();
            }
        }
        return child;
    }
}
