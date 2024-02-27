package assignment.birds;

public class OrderedDictionary implements OrderedDictionaryADT {

    Node root;

    OrderedDictionary() {
        root = new Node();
    }

    /**
     * Returns the Node object with key k, or it returns null if such a record
     * is not in the dictionary.
     *
     * @param k
     * @return
     * @throws assignment/birds/DictionaryException.java
     */
    public Node findNode(DataKey k) throws DictionaryException {
        Node current = root;
        int comparison;
        if (root.isEmpty()) {
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
     * Returns the Record object with key k, or it returns null if such a record
     * is not in the dictionary.
     *
     * @param k
     * @return
     * @throws assignment/birds/DictionaryException.java
     */
    @Override
    public BirdRecord find(DataKey k) throws DictionaryException {
        return findNode(k).getData();
    }

    /**
     * Inserts r into the ordered dictionary. It throws a DictionaryException if
     * a record with the same key as r is already in the dictionary.
     *
     * @param r
     * @throws birds.DictionaryException
     */
    @Override
    public void insert(BirdRecord r) throws DictionaryException {
        Node current = root;
        int comparison;

        if (root.isEmpty()) {
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
     * @throws birds.DictionaryException
     */
    @Override
    public void remove(DataKey k) throws DictionaryException {
        // Write this method
    }

    /**
     * Returns the successor of k (the record from the ordered dictionary with
     * smallest key larger than k); it returns null if the given key has no
     * successor. The given key DOES NOT need to be in the dictionary.
     *
     * @param k
     * @return
     * @throws birds.DictionaryException
     */
    @Override
    public BirdRecord successor(DataKey k) throws DictionaryException{
        Node current = findNode(k);

        if(current.hasRightChild()) {
            current = current.getRightChild();
            while (current.hasLeftChild()) {
                current = current.getLeftChild();
            }

            return current.getData();
        } else {
            Node parent = current.getParent();
            while (current.hasParent() && current == parent.getRightChild()) {
                current = parent;
                parent = parent.getParent();
            }


            if (parent == null) {
                throw new DictionaryException("This record has no successor");
            }
            return parent.getData();
        }
    }

   
    /**
     * Returns the predecessor of k (the record from the ordered dictionary with
     * largest key smaller than k; it returns null if the given key has no
     * predecessor. The given key DOES NOT need to be in the dictionary.
     *
     * @param k
     * @return
     * @throws birds.DictionaryException
     */
    @Override
    public BirdRecord predecessor(DataKey k) throws DictionaryException{
        // Write this method
        return null; // change this statement
    }

    /**
     * Returns the record with smallest key in the ordered dictionary. Returns
     * null if the dictionary is empty.
     *
     * @return
     */
    @Override
    public BirdRecord smallest() throws DictionaryException{
        // Write this method
        return null; // change this statement
    }

    /*
	 * Returns the record with largest key in the ordered dictionary. Returns
	 * null if the dictionary is empty.
     */
    @Override
    public BirdRecord largest() throws DictionaryException{
        // Write this method
        return null; // change this statement
    }
      
    /* Returns true if the dictionary is empty, and true otherwise. */
    @Override
    public boolean isEmpty (){
        return root.isEmpty();
    }
}
