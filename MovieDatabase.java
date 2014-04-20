/*
 * 숙제 채점 후 다시
 */
import java.io.*;

class Node {
	private Object genre; 
	private Object title;
	private Node next;
	private Node below;
	public Node() {
		genre = null;
		next = null;
	}
	public Node(Object newGenre, Object newTitle) {
		genre = newGenre;
		title = newTitle;
		next = null;
	}
	public Node(Object newGenre, Object newTitle, Node nextNode) {
		genre = newGenre;
		title = newTitle;
		next = nextNode;
	}
	public Object getGenre( ) {
		return genre;
	}
	public Object getTitle( ) {
		return title;
	}
	public Node getNext( ) {
		return next;
	}
	public void setNext(Node nextNode) {
		next = nextNode;
	}
}

interface ListInterface {
	public boolean isEmpty( );
	public int size( );
	public void add(int index, Object item);
	public void remove(int index);
	public Object get(int index);
	public void removeAll( );
}

interface ListInterfaceDebug {
	public void printAll( );
	public void search(String item);
	//public void orderedInsert(String item);
}

class ListReferenceBased implements ListInterface, ListInterfaceDebug {
	private Node head;
	private int numItems;
	// constructor
	public ListReferenceBased( ) {
		numItems = 0;
		head = null;
	}
	
	public void search(String item) {
		for ( Node curr = head; curr != null; curr = curr.getNext())
		{
			if ( curr.getGenre().toString().contains(item))
				System.out.println(curr.getGenre().toString());
		}
	}
	public void printAll( ) {
		for ( Node curr = head; curr != null; curr = curr.getNext())
		{
			System.out.println(curr.getGenre().toString());
		}
	}

	public void orderedInsert(String genre) {
		if (numItems == 0) {
			// 1이 들어온 후 또 1이 들어오면 이전 1이 밀린다.
			Node newNode = new Node(genre, head);
			head = newNode;
		} else {
			Node curr = head;
			Node next = curr.getNext();
			while ( next != null )
			{
				if ( next.getGenre().toString().compareTo(genre) > 0 )
					break;
				if ( next.getGenre().toString().compareTo(genre) == 0 )
					return;
				curr = curr.getNext();
				next = next.getNext();
			}
			Node prev = curr;
			Node newNode = new Node(genre, prev.getNext( ));
			prev.setNext(newNode);
		}
		numItems++;
	}
	// operations
	private Node find(int index) {
	// return reference to ith node
		Node curr = head;  // 1st node
		for (int i = 1; i < index; i++) {
			curr = curr.getNext( );
		}
		return curr;
	}
	
	public boolean isEmpty( ) { 
		return numItems == 0;
	}
	
	public int size( ) {
		return numItems;
	}
	
	public void add(int index, Object item) {
		if (index >= 1 && index <= numItems+1) {
			if (index == 1) {
				// 1이 들어온 후 또 1이 들어오면 이전 1이 밀린다.
				Node newNode = new Node(item, head);
				head = newNode;
			} else {
				Node prev = find(index - 1);
				Node newNode = new Node(item, prev.getNext( ));
				prev.setNext(newNode);
			}
			numItems++;
		} else {
			throw new RuntimeException("add");
		} 
	}
	
	public void remove(String string) {
		if ( 1 <= numItems) {		
			if (numItems == 1) {
				head = head.getNext( );
			} else {
				Node curr = head;
				Node next = curr.getNext();
				while ( next != null )
				{
					if ( next.getGenre().toString().compareTo(string) == 0 )
						break;
					curr = curr.getNext();
					next = next.getNext();
				}
				Node prev = curr;
				Node temp = prev.getNext( );
				prev.setNext(temp.getNext( ));
			}
			numItems--;
		}
	}
	public void remove(int index) {
		if (index >= 1 && index <= numItems) {
			if (index == 1) head = head.getNext( );
			else {
				Node prev = find(index - 1);
				Node curr = prev.getNext( );
				prev.setNext(curr.getNext( ));
			}
			numItems--;
		} else {
			throw new RuntimeException("remove");			
		} 
	}
	
	public Object get(int index) {
		if (index >= 1 && index <= numItems) {
			Node curr = find(index);
			return curr.getGenre( );
		} else {			
			throw new RuntimeException("get");		
		}
	}
	
	public void removeAll( ) {
		numItems = 0;
		head = null;
	}
}


public class MovieDatabase
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		ListReferenceBased list = new ListReferenceBased();
		
		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("QUIT") == 0)
					break;

				command(input, list);
			}
			catch (Exception e)
			{
				//System.out.println(e.toString());
				;
			}
		}
	}

	private static void command(String input, ListReferenceBased list)
	{
		String [] inputArray = input.split("%");
		int inputLength = inputArray.length;		
		String operation = inputArray[0].toUpperCase();

		if ( operation.contains("INSERT") && inputLength == 2 ) // insert %genre%
		{
			 //int index = Integer.parseInt(inputArray[1]);
			 String genre = inputArray[1];
			 list.orderedInsert(genre);
			 //list.add(index, genre);
		} else if ( operation.contains("DELETE") && inputLength == 2) { // DELETE %1%
			 String genre = inputArray[1];
			 list.remove(genre);
		} else if ( operation.contains("PRINT") && inputLength == 1) { // PRINT
			list.printAll();
		} else if (operation.contains("SEARCH") && inputLength == 2) {
			 String genre = inputArray[1];
			 list.search(genre);			
			;
		} else {
			;
		}
	}
}