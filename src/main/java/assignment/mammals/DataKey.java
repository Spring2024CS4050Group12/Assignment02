package assignment.mammals;

public class DataKey {
	private String mammalName;
	private int mammalSize;

	// default constructor
	public DataKey() {
		this(null, 0);
	}
        
	public DataKey(String name, int size) {
		mammalName = name;
		mammalSize = size;
	}

	@Override
	public String toString() {
		return "DataKey{" +
				"mammalName='" + mammalName + '\'' +
				", mammalSize=" + mammalSize +
				'}';
	}

	public String getMammalName() {
		return mammalName;
	}

	public int getMammalSize() {
		return mammalSize;
	}

	/**
	 * Returns 0 if this DataKey is equal to k, returns -1 if this DataKey is smaller
	 * than k, and it returns 1 otherwise. 
	 */
	public int compareTo(DataKey k) {
            if (this.getMammalSize() == k.getMammalSize()) {
                int compare = this.mammalName.toLowerCase().compareTo(k.getMammalName().toLowerCase());
                if (compare == 0){
                     return 0;
                } 
                else if (compare < 0) {
                    return -1;
                }
            }
            else if(this.getMammalSize() < k.getMammalSize()){
                    return -1;
            }
            return 1;
            
	}
}
