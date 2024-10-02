
public class BinaryTree<E> {
	static class Node<E>
	{
		public E data;
		public Node<E> left;
		public Node<E> right;
		
		public Node(E data, Node<E> left, Node<E> right)
		{
			this.data = data;
			this.left = left;
			this.right = right;
		}
	}
	
	public Node<E> root;
	public int numElt;
	
	public BinaryTree()
	{
		this.root = null;
		this.numElt = 0;
	}
	
	public int height()
	{
		return height(this.root);
	}
	
	private int height(Node<E> current)
	{
		if (current == null)
			return -1;
		else
		{
			int leftHeight = height(current.left);
			int rightHeight = height(current.right);
			if (leftHeight > rightHeight)
				return leftHeight+1;
			else
				return rightHeight+1;
		}
	}
	
	public void add(E data, String directions)
	{
		if (directions.equals(""))
		{
			this.root = new Node<E>(data, null, null);
			return;
		}
		Node<E> current = this.root;
		String parentPart = directions.substring(0, directions.length()-1);
		char childPart = directions.charAt(directions.length() - 1);
		for (int i=0; i < parentPart.length(); i++)
		{
			if (parentPart.charAt(i) == 'L')
				current = current.left;
			else
				current = current.right;
		}
		if (childPart == 'L')
			current.left = new Node<E>(data, null, null);
		else
			current.right = new Node<E>(data, null, null);
			
		
	}
	
	public void addRecursive(E data, String directions)
	{
		this.root = add(data,  directions, this.root);
	}
	
	private Node<E> add(E data, String directions, Node<E> current)
	{
		if (directions.equals(""))
			return new Node<E>(data, null, null);
		if (directions.charAt(0) == 'L' && current != null)
			current.left = add(data, directions.substring(1), current.left);
		else if (directions.charAt(0) == 'R' && current != null)
			current.right = add(data, directions.substring(1), current.right);
		return current;
			
	}
	
	private String toString(Node<E> current) {
		if(current == null)
			return "";
		else {
			String answer = "";
			answer += current.data + " ";
			answer += toString(current.left);
			answer += toString(current.right);
			return answer;
		}
	}
	
	
	
	private int count(Node<E> current) {
		int count = 0;
		if(current == null)
			return count;
		
		if(current.left !=null)
			count += count(current.left);
		if(current.right != null)
			count += count(current.right);
		
		return count+1;
		
	}
	private int countRecursive() {
		return count(this.root);
	}
	
}

