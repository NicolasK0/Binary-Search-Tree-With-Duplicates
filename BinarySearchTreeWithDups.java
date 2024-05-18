import java.util.*;

public class BinarySearchTreeWithDups<T extends Comparable<? super T>> extends BinarySearchTree<T> {

	public BinarySearchTreeWithDups() {
		super();
	}

	public BinarySearchTreeWithDups(T rootEntry) {
		super(rootEntry);
	}

	@Override
	public boolean add(T newEntry) {
		if (isEmpty()) {
			return super.add(newEntry);
		} else {
			return addEntryHelperNonRecursive(newEntry);
		}
	}

	private boolean addEntryHelperNonRecursive(T newEntry) {
		BinaryNode<T> newNodeToAdd = new BinaryNode<>(newEntry);
		Stack<BinaryNode<T>> nodeStack = new Stack<>();
		nodeStack.push(root);

		while (!nodeStack.isEmpty()) {
			BinaryNode<T> currentRootNode = nodeStack.pop();
			int comparison = newEntry.compareTo(currentRootNode.getData());

			if (comparison <= 0 ) {  // equal or less than both put in the left subtree
				if (currentRootNode.hasLeftChild()) {
					nodeStack.push(currentRootNode.getLeftChild());
				} else {  // if currentRootNode does not have left child, node to add is added to become currentRootNode's left child
					currentRootNode.setLeftChild(newNodeToAdd);
					return true;
				}
			} else { // (comparison > 0), newEntry is greater than currentRootNode, put in right subtree
				if (currentRootNode.hasRightChild()) {
					nodeStack.push(currentRootNode.getRightChild());
				} else {  // if currentRootNode does not have right child, node to add is added to become currentRootNode's right child
					currentRootNode.setRightChild(newNodeToAdd);
					return true;
				}
			}
		}
		return false;
	}

	public int countIterative(T target) {
		int count = 0;
		BinaryNode<T> currentNode = root;

		while (currentNode != null) {
			int comparison = target.compareTo(currentNode.getData());
			if (comparison == 0) {  // found node that equals data
				count++;
				currentNode = currentNode.getLeftChild();
			}
			else if (comparison < 0) {  // target < Node
				currentNode = currentNode.getLeftChild();
			}
			else {  // target > Node
				currentNode = currentNode.getRightChild();
			}
		}
		return count;
	}


	public int countGreaterRecursive(T target) {
		return (countGreaterRecursiveHelper(target, root));
	}

	private int countGreaterRecursiveHelper(T target, BinaryNode<T> currentRoot) {
		if (currentRoot == null) {
			return 0;
		}
		int count = 0;
		int comparison = currentRoot.getData().compareTo(target);  // comparing currentRoot data to target

		if (comparison > 0) {  // currentRoot data is greater than target; increment count
			count++;
			return count + countGreaterRecursiveHelper(target, currentRoot.getLeftChild()) + countGreaterRecursiveHelper(target, currentRoot.getRightChild());
		} else {  // currentRootNode data is < than or = to target; VALUES GREATER THAN TARGET WILL NOT BE IN LEFT SUBTREE
			return count + countGreaterRecursiveHelper(target, currentRoot.getRightChild());
		}
	}

	public int countGreaterIterative(T target) {
		int count = 0;
		Stack<BinaryNode<T>> nodeStack = new Stack<BinaryNode<T>>();
		nodeStack.push(root);

		// Loop runs until node stack is empty
		while (!nodeStack.isEmpty()) {
			BinaryNode<T> currentRootNode = nodeStack.pop();
			int comparison = currentRootNode.getData().compareTo(target);  // comparing currentRootNode data to target

			if (comparison > 0) {  // currentRootNode data is greater than target; increment count
				count++;
				if (currentRootNode.hasRightChild()) {
					nodeStack.push(currentRootNode.getRightChild());
				}
				if (currentRootNode.hasLeftChild()) {
					nodeStack.push(currentRootNode.getLeftChild());
				}
			} else { // // currentRootNode data is < than or = to target; VALUES GREATER THAN TARGET WILL NOT BE IN LEFT SUBTREE
				if (currentRootNode.hasRightChild()) {  // only push right subtree since it is only one with potential greater values
					nodeStack.push(currentRootNode.getRightChild());
				}
			}
		}
		return count;
	}
			

	public int countUniqueValues() {
		HashMap<T, BinaryNode<T>> uniqueValuesMap = new HashMap<T, BinaryNode<T>>();  // use hash map to store unique values
		Stack<BinaryNode<T>> nodeStack = new Stack<BinaryNode<T>>();
		int count = 0;

		if (isEmpty()) {  // empty tree
			return count;
		}

		nodeStack.push(root);
		while (!nodeStack.isEmpty()) {
			BinaryNode<T> currentRoot = nodeStack.pop();
			T data = currentRoot.getData();
			if (!uniqueValuesMap.containsKey(data)) {  // currentRoot is unique; add to map and increment count
				uniqueValuesMap.put(data, currentRoot);
				count++;
			}
			// Now, the check for whether or not currentRoot is unique is done; push its children to stack
			if (currentRoot.hasRightChild()) {
				nodeStack.push(currentRoot.getRightChild());
			}
			if (currentRoot.hasLeftChild()) {
				nodeStack.push(currentRoot.getLeftChild());
			}
		}

		return count;
	}

}