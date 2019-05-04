package mendelu.xkremece;

import java.util.List;

public interface IComponentFinder {
    List<Node> getNodes();
    List<List<Node>> getComponents();
    boolean addNode(String nodeName) throws NodeException;
    boolean addConnection(String firstNodeName, String secondNodeName) throws NodeException;
    Node getSingleNode(String nodeName) throws NodeException;
    boolean containsNode(Node testedNode);
    void findComponents();
    List<Node> visitAllNodes(Node searchedNode, List<Node> componentNodes);
    boolean containsNodeInList(List<Node> nodeList, Node searchedNode);
    int getComponentsQuantity();
}
