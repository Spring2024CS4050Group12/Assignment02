package assignment.mammals;

/**
 * a class Node to represent the nodes of the binary search tree. Each node will
 * store a Record, and references to its left child, right child, and parent.
 *
 */

public class Node {
	private MammalRecord _data;
	private Node _leftChild;
	private Node _rightChild;
	private Node _parent;

	// default constructor
	public Node() {
		this(new MammalRecord()); // storing null Record objects
	}

	public Node(MammalRecord data) {
		this(data, null, null);
	}

	public Node(MammalRecord data, Node leftChild, Node rightChild) {
		_data = data;
		setLeftChild(leftChild);
		setRightChild(rightChild);
	}

	public MammalRecord getData() {
		return _data;
	}

	public void setData(MammalRecord data) {
		_data = data;
	}

	public Node getLeftChild() {
		return _leftChild;
	}

	public Node getParent() {
		return _parent;
	}

	public Node getRightChild() {
		return _rightChild;
	}

	public void setLeftChild(Node leftChild) {
		_leftChild = leftChild;
		if (leftChild != null ) //&& leftChild.hasLeftChild())
			leftChild.setParent(this);
	}


	public void setRightChild(Node rightChild) {
		_rightChild = rightChild;
		if (rightChild != null )//&& rightChild.hasRightChild())
			rightChild.setParent(this);
	}

	public void setParent(Node parent) {
		_parent = parent;
	}
	
	public boolean hasLeftChild() {
		return (_leftChild != null);
	}

	public boolean hasRightChild() {
		return (_rightChild != null);
	}
	
	public boolean hasParent()	{
		return (_parent != null);
	}
	
	public boolean isLeaf() {
		return ((_leftChild == null) && (_rightChild == null));
	}
	
	public boolean isEmpty() {
		return (_data.getDataKey() == null) ;
	}

	public Node greatestDescendant() {
		return hasRightChild()? this._rightChild.greatestDescendant() : this;
	}

	public Node leastDescendant() {
		return hasLeftChild()? this._leftChild.leastDescendant() : this;
	}

	public void replace(Node node) {
		if (!hasParent()) return;

		if (_parent.getRightChild() == this)
			_parent.setRightChild(node);
		else
			_parent.setLeftChild(node);
	}

	public Node removed() {
		if (isLeaf())
			return null;

		Node replacement;

		if (!hasLeftChild())
			replacement = _rightChild;

		else if (!hasRightChild())
			replacement = _leftChild;

		else {
			replacement = _leftChild.greatestDescendant();
			replacement.replace(null);

			replacement.setLeftChild(_leftChild);
			replacement.setRightChild(_rightChild);
		}

		replacement.setParent(_parent);

		return replacement;
	}
}
