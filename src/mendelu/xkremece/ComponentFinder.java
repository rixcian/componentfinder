package mendelu.xkremece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComponentFinder implements IComponentFinder {

    private List<Node> nodes = new ArrayList<Node>();
    private List<Node> visitedNodes = new ArrayList<Node>();
    private List<List<Node>> components = new ArrayList<List<Node>>();

    public ComponentFinder() { }

    @Override
    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public List<List<Node>> getComponents() {

        return components;
    }

    // Pridavani prvku do listu
    @Override
    public boolean addNode(String nodeName) throws NodeException {
        Node newNode = new Node(nodeName);

        if(!containsNode(newNode)) {
            nodes.add(new Node(nodeName));
            return true;
        } else {
            // Dany prvek uz v listu existuje
            throw new NodeException("Vámi zadaný prvek už v listu existuje!");
        }
    }

    // Prida spojeni mezi dvema uzly
    @Override
    public boolean addConnection(String firstNodeName, String secondNodeName) throws NodeException {

        try {
            if (containsNode(getSingleNode(firstNodeName)) && containsNode(getSingleNode(secondNodeName))) {
                getSingleNode(firstNodeName).newNeighbour(getSingleNode(secondNodeName));
                getSingleNode(secondNodeName).newNeighbour(getSingleNode(firstNodeName));
                return true;
            }
        } catch (NodeException e) {
            throw new NodeException("Jeden z Vámi zadaných prvků neexistuje!");
        }

        return false;
    }

    @Override
    public Node getSingleNode(String nodeName) throws NodeException {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getName().equals(nodeName)) {
                return nodes.get(i);
            }
        }

        // Osetrit!!
        throw new NodeException("Vámi zadaný prvek neexistuje!");
    }

    // Zjisti zda zadany uzel uz neni pridany
    @Override
    public boolean containsNode(Node testedNode) {
        for (int i = 0; i < this.nodes.size(); i++) {
            if (nodes.get(i).getName().equals(testedNode.getName())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void findComponents() {
        this.nodes.forEach((node) -> {

            if (!containsNodeInList(visitedNodes, node)) {
                visitedNodes.add(node);

                List<Node> componentNodes = new ArrayList<Node>();
                componentNodes.add(node);

                List<Node> singleComponent = visitAllNodes(node, componentNodes);
                components.add(singleComponent);
            }

        });
    }

    @Override
    public List<Node> visitAllNodes(Node searchedNode, List<Node> componentNodes) {

        searchedNode.getNeighbours().forEach((neighbour) -> {
            if (!containsNodeInList(this.visitedNodes, neighbour)) {
                this.visitedNodes.add(neighbour);
                componentNodes.add(neighbour);
                visitAllNodes(neighbour, componentNodes);
            }
        });

        return componentNodes;
    }

    @Override
    public boolean containsNodeInList(List<Node> nodeList, Node searchedNode) {
        for (int i = 0; i < nodeList.size(); i++) {
            if (nodeList.get(i).getName().equals(searchedNode.getName())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int getComponentsQuantity() {
        return components.size();
    }
}
