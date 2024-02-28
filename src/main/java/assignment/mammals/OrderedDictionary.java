package assignment.mammals;

import assignment.mammals.MammalRecord;

public class OrderedDictionary implements OrderedDictionaryADT {

    Node root = null;

    /**
     * Returns the Node object with key k, or it returns null if such a record
     * is not in the dictionary.
     *
     * @param k
     * @return
     * @throws assignment/mammals/DictionaryException.java
     */
    public Node findNode(DataKey k) throws DictionaryException {
        Node current = root;
        int comparison;
        if (isEmpty()) {
            throw new DictionaryException("There is no record matches the given key");
        }

        while (true) {
            comparison = current.getData().getDataKey().compareTo(k);
            if (comparison == 0) { // key found
                return current;
            }
            if (comparison == 1) {
                if (current.getLeftChild() == null) {
                    // Key not found
                    throw new DictionaryException("There is no record matches the given key");
                }
                current = current.getLeftChild();
            } else if (comparison == -1) {
                if (current.getRightChild() == null) {
                    // Key not found
                    throw new DictionaryException("There is no record matches the given key");
                }
                current = current.getRightChild();
            }
        }
    }

    /**
     * Returns the Node object with key k, or it returns null if such a record
     * is not in the dictionary.
     * Looks for a substring of the primary key
     *
     * @param k
     * @return
     * @throws assignment/mammals/DictionaryException.java
     */
    public Node partialFindKey(DataKey k) throws DictionaryException {
        if (isEmpty()) {
            throw new DictionaryException("There is no record matches the given key");
        }

        Node current = root.leastDescendant();

        while (true) {
            if (current.getData().getDataKey().getMammalName().toLowerCase().contains(k.getMammalName().toLowerCase()))
                return current;
            try {
                current = nodeSuccessor(current);
            } catch (DictionaryException ex) {
                throw new DictionaryException("There is no record matches the given key");
            }
            if (current == null)
                throw new DictionaryException("There is no record matches the given key");
        }
    }

    /**
     * Returns the Record object with key k, or it returns null if such a record
     * is not in the dictionary.
     *
     * @param k
     * @return
     * @throws assignment/mammals/DictionaryException.java
     */
    @Override
    public MammalRecord find(DataKey k) throws DictionaryException {
        return findNode(k).getData();
    }

    /**
     * Returns the Record object with partial key k, or it returns null if such a record
     * is not in the dictionary.
     *
     * @param k
     * @return
     * @throws assignment/mammals/DictionaryException.java
     */
    public MammalRecord partialFind(DataKey k) throws DictionaryException {
        return partialFindKey(k).getData();
    }

    /**
     * Inserts r into the ordered dictionary. It throws a DictionaryException if
     * a record with the same key as r is already in the dictionary.
     *
     * @param r
     * @throws mammals.DictionaryException
     */
    @Override
    public void insert(MammalRecord r) throws DictionaryException {
        Node current = root;
        int comparison;

        if (isEmpty()) {
            root = new Node(r);
            return;
        }

        while (true) {
            comparison = current.getData().getDataKey().compareTo(r.getDataKey());

            if (comparison == 0) { // Don't insert duplicate
                return;
            } else if (comparison == 1) {
                if (!current.hasLeftChild()) {
                    Node newNode = new Node(r);
                    current.setLeftChild(newNode);
                    newNode.setParent(current);
//                    System.out.println(newNode.getData().getDataKey().toString() + " is left child of " + current.getData().getDataKey().toString());
                    return;
                } else {
                    current = current.getLeftChild();
                }
            } else {
                if (!current.hasRightChild()) {
                    Node newNode = new Node(r);
                    current.setRightChild(newNode);
                    newNode.setParent(current);
//                    System.out.println(newNode.getData().getDataKey().toString() + " is right child of " + current.getData().getDataKey().toString());
                    return;
                } else {
                    current = current.getRightChild();
                }
            }

        }
    }

    /**
     * Removes the record with Key k from the dictionary. It throws a
     * DictionaryException if the record is not in the dictionary.
     *
     * @param k
     * @throws mammals.DictionaryException
     */
    @Override
    public void remove(DataKey k) throws DictionaryException {
        Node node = findNode(k);
        if (node == root)
            root = node.removed();
        else
            node.replace(node.removed());
    }

    /**
     * Returns the successor of n (the record from the ordered dictionary with
     * smallest key larger than n); it returns null if the given key has no
     * successor.
     *
     * @param n
     * @return
     * @throws mammals.DictionaryException
     */
    public Node nodeSuccessor(Node n) throws DictionaryException{
        if(n.hasRightChild()) {
            return n.getRightChild().leastDescendant();
        } else {
            Node parent = n.getParent();
            while (n.hasParent() && n == parent.getRightChild()) {
                n = parent;
                parent = parent.getParent();
            }


            if (parent == null) {
                throw new DictionaryException("This record has no successor");
            }
            return parent;
        }
    }

    /**
     * Returns the successor of k (the record from the ordered dictionary with
     * smallest key larger than k); it returns null if the given key has no
     * successor. The given key DOES NOT need to be in the dictionary.
     *
     * @param k
     * @return
     * @throws mammals.DictionaryException
     */
    @Override
    public MammalRecord successor(DataKey k) throws DictionaryException{
        Node current = findNode(k);

        return nodeSuccessor(current).getData();
    }

    /**
     * Returns the predecessor of n (the Node from the ordered dictionary with
     * largest key smaller than n; it returns null if the given node has no
     * predecessor.
     *
     * @param n
     * @return
     * @throws mammals.DictionaryException
     */
    public Node nodePredecessor(Node n) throws DictionaryException{
        if(n.hasLeftChild()) {
            return n.getLeftChild().greatestDescendant();
        } else {
            Node parent = n.getParent();
            while (n.hasParent() && n == parent.getLeftChild()) {
                n = parent;
                parent = parent.getParent();
            }


            if (parent == null) {
                throw new DictionaryException("This record has no predecessor");
            }
            return parent;
        }
    }
   
    /**
     * Returns the predecessor of k (the record from the ordered dictionary with
     * largest key smaller than k; it returns null if the given key has no
     * predecessor. The given key DOES NOT need to be in the dictionary.
     *
     * @param k
     * @return
     * @throws mammals.DictionaryException
     */
    @Override
    public MammalRecord predecessor(DataKey k) throws DictionaryException{
        Node current = findNode(k);

        return nodePredecessor(current).getData();
    }

    /**
     * Returns the record with smallest key in the ordered dictionary. Returns
     * null if the dictionary is empty.
     *
     * @return
     */
    @Override
    public MammalRecord smallest() throws DictionaryException {
        if (isEmpty()) {
            throw new DictionaryException("Dictionary is empty!");
        }

        return root.leastDescendant().getData();
    }

    /*
	 * Returns the record with largest key in the ordered dictionary. Returns
	 * null if the dictionary is empty.
     */
    @Override
    public MammalRecord largest() throws DictionaryException{
        if (isEmpty()) {
            throw new DictionaryException("Dictionary is empty!");
        }

        return root.greatestDescendant().getData();
    }
      
    /* Returns true if the dictionary is empty, and true otherwise. */
    @Override
    public boolean isEmpty (){
        return root == null;
    }
}
