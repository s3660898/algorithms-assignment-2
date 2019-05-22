package pathFinder;

import map.Coordinate;
import map.PathMap;

import java.util.*;

public class DijkstraPathFinder implements PathFinder
{
  private PathMap map;
  private int coordinatesExplored;

  public DijkstraPathFinder(PathMap map) {
    this.map = map;
    this.coordinatesExplored = 0;
  } // end of DijkstraPathFinder()



  @Override
  public List<Coordinate> findPath() {
    List<Coordinate> path = new ArrayList<Coordinate>();

    // the node queue, based on runningCost
    Comparator<Node> nodeComparator = (n1, n2)->{
      return n1.getRunningCost() - n2.getRunningCost();
    };
    PriorityQueue<Node> openNodes = new PriorityQueue<Node>(nodeComparator);

    List<Node> closedNodes = new ArrayList<Node>();

    // adding origin to nodes
    Node currentNode = new Node(null, map.originCells.get(0), 0);
    Node destNode    = new Node(null, map.destCells.get(0), 0);

    while(currentNode.equals(destNode) == false){

      // expanding around current node
      int r = currentNode.getCoordinate().getRow();
      int c = currentNode.getCoordinate().getColumn();

      // testing NESW neihbouring nodes 
      testCell(openNodes, closedNodes, r+1, c  , currentNode);
      testCell(openNodes, closedNodes, r  , c+1, currentNode);
      testCell(openNodes, closedNodes, r-1, c  , currentNode);
      testCell(openNodes, closedNodes, r  , c-1, currentNode);

      // get the next closest node & close current node
      if(!openNodes.isEmpty()){
        closedNodes.add(currentNode);
        currentNode = openNodes.remove();
      // exit if no nodes left to be expanded, destination inaccessable
      }else
        break;
    }

    // rebuilding path if destination is reached
    if(currentNode.equals(destNode)){
      do{
        path.add(currentNode.getCoordinate());
        currentNode = currentNode.getOrigin();
      }while(currentNode.getOrigin() != null);
      path.add(map.originCells.get(0));

      Collections.reverse(path);
    }

    return path;
  } // end of findPath()

  private void testCell(PriorityQueue<Node> openNodes, List<Node> closedNodes,
      int r, int c, Node currentNode){
    // the coordinate needs to be wrapped in a Node to make use of contains() method
    Node tempNode;

    // making sure the node is a valid location on the board
    if(map.isPassable(r, c)){
      tempNode = new Node(currentNode, new Coordinate(r, c), currentNode.getRunningCost() + 1);

      // making sure the node isn't already closed
      if(closedNodes.contains(tempNode) == false){

        // checking if the node has been opened previously
        if(openNodes.contains(tempNode) == false){
          openNodes.add(tempNode);
          this.coordinatesExplored++;
        }else{
          //Node existingNode = openNodes.get(tempNode);
          //Node existingNode = openNodes.remove(tempNode);
          Iterator<Node> openIterator = openNodes.iterator();
          Node existingNode;

          // iterating through the queue to get existing node
          do{
            existingNode = openIterator.next();
          }while(existingNode != null && existingNode.equals(tempNode) == false);

          // update the running cost and origin if new route is cheaper
          if(existingNode.getRunningCost() > tempNode.getRunningCost()){
            openNodes.remove(existingNode);

            existingNode.setRunningCost(tempNode.getRunningCost());
            existingNode.setOrigin(currentNode);

            openNodes.add(existingNode);
          }
        }

      }
    }
  }


  @Override
  public int coordinatesExplored() {
    return coordinatesExplored;
  } // end of cellsExplored()


  private class Node{
    private Node       origin;
    private Coordinate coordinate;
    private int        runningCost;

    public Node(Node origin, Coordinate coordinate, int runningCost){
      this.origin = origin;
      this.coordinate = coordinate;
      this.runningCost = runningCost;
    }

    public Coordinate getCoordinate(){return coordinate;}
    public int getRunningCost(){return runningCost;}
    public Node getOrigin(){return origin;}

    public void setRunningCost(int runningCost){this.runningCost = runningCost;}
    public void setOrigin(Node origin){this.origin = origin;}

    // overriding default equals behaviour to be true if the Node's coords are the same
    // allowing me to use the PriorityQueue.contains() method
    @Override
    public boolean equals(Object o){
      if(this == o)
        return true;

      if(o != null)
        if(this.coordinate.equals(((Node)o).getCoordinate()))
          return true;

      return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.coordinate.getRow(), this.coordinate.getColumn());
    }
  }

} // end of class DijsktraPathFinder
