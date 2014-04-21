/*
 * 숙제 채점 후 충격 먹어서 처음부터 다시 만듬.
 * SEARCH 명령시 못찾으면 EMPTY를 빼먹었음.
 * 2014. 04. 20.(일)
 * 명경백
 */
import java.io.*;

// Copy from lecture note chapter 5.
class Node {
	private Object record; 
	private Node next;
	public Node() {
		record = null;
		next = null;
	}
	public Node(Object newRecord) {
		record = newRecord;
		next = null;
	}
	public Node(Object newRecord, Node nextNode) {
		record = newRecord;
		next = nextNode;
	}
	public Object getRecord( ) {
		return record;
	}
	public Node getNext( ) {
		return next;
	}
	public void setNext(Node nextNode) {
		next = nextNode;
	}
}

// Copy from lecture note chapter 5.
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

class MovieListReferenceBased extends ListReferenceBased {
	private ListReferenceBased list;
	MovieListReferenceBased(){
		list = new ListReferenceBased();
	}
	public void orderedInsertMovie(String genre, String title) {
		String record = genre.concat(",").concat(title);
		list.orderedInsert( record);
	}
	public void printAllMovie(){
		list.printAll();
	}
	public void searchMovie(String title) {
		list.search(title);
	}
	public void deleteMovie(String genre, String title) {
		String record = genre.concat(",").concat(title);
		list.remove(record);
	}	
}

// Copy from lecture note chapter 5.
class ListReferenceBased implements ListInterface, ListInterfaceDebug {
	private Node head;
	private Node tail;	
	private int numItems;
	// constructor
	public ListReferenceBased( ) {
		numItems = 0;
		head = new Node("head");
		tail = new Node("tail");
		head.setNext(tail);
		tail.setNext(tail);
	}
	
	public void search(String item) {
		if ( this.numItems < 1 ) {
			System.out.println("EMPTY");
			return;		
		}
		boolean searchFounded = false;
		for ( Node curr = head.getNext(); curr != tail; curr = curr.getNext())
		{
			String record = curr.getRecord().toString();
			String array[] = record.split(",");
			String genre;
			String title;
			if ( array.length == 1){
				genre = array[0];
				System.out.print("(" + genre + ",) ");			
			}
			if ( array.length == 2){
				genre = array[0];
				title = array[1];
				if ( title.contains(item)) {			
					System.out.println("(" + genre + ", " + title +")");
					searchFounded = true;
				}				
			}
		}
		if ( searchFounded == false )
			System.out.println("EMPTY");
	}
	public void printAll( ) {
		if (this.numItems < 1 ) {
			System.out.println("EMPTY");
			return;
		}

		for ( Node curr = head.getNext(); curr != tail; curr = curr.getNext())
		{
			String array[] = curr.getRecord().toString().split(",");
			if (array.length == 1) {
				String genre = array[0].toString();				
				System.out.print("(" + genre + ",) ");
			}
			else if ( array.length == 2) {
				String genre = array[0].toString();
				String title = array[1].toString();
				System.out.println("(" + genre + ", " + title +")");
			}
		}	
	}

	public void orderedInsert(String record) {
		Node curr = head;
		Node next = curr.getNext();
		while ( next != tail && record.compareTo(next.getRecord().toString()) >= 0)
		{
			if ( record.compareTo(next.getRecord().toString()) == 0)
				return;
			curr = curr.getNext();
			next = next.getNext();
		}
		Node prev = curr;
		Node newNode = new Node(record, prev.getNext( ));
		prev.setNext(newNode);
		numItems++;
	}
	// operations
	// Copy from lecture note chapter 5.	
	private Node find(int index) {
	// return reference to ith node
		Node curr = head;  // 1st node
		for (int i = 1; i < index; i++) {
			curr = curr.getNext( );
		}
		return curr;
	}
	
	// Copy from lecture note chapter 5.	
	public boolean isEmpty( ) { 
		return numItems == 0;
	}
	
	// Copy from lecture note chapter 5.	
	public int size( ) {
		return numItems;
	}
	
	// Copy from lecture note chapter 5.	
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

			Node curr = head;
			Node next = curr.getNext();
			while ( next != null )
			{
				if ( next.getRecord().toString().compareTo(string) == 0 )
					break;
				curr = curr.getNext();
				next = next.getNext();
			}
			Node prev = curr;
			Node temp = prev.getNext( );
			prev.setNext(temp.getNext( ));
			numItems--;
		}
	}
	
	// Copy from lecture note chapter 5.	
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
	
	// Copy from lecture note chapter 5.	
	public Object get(int index) {
		if (index >= 1 && index <= numItems) {
			Node curr = find(index);
			return curr.getRecord( );
		} else {			
			throw new RuntimeException("get");		
		}
	}
	
	public void removeAll( ) {
		numItems = 0;
		head = new Node();
		tail = new Node();
		head.setNext(tail);
		tail.setNext(tail);
	}
}


public class MovieDatabase
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//ListReferenceBased list = new ListReferenceBased();
		MovieListReferenceBased list = new MovieListReferenceBased();
		
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

	private static void command(String input, MovieListReferenceBased list)
	{
		String [] inputArray = input.split("%");
		int inputLength = inputArray.length;		
		String operation = inputArray[0].toUpperCase();

		if ( operation.contains("INSERT") && inputLength >= 4 ) // insert %genre% %title%
		{
			 //int index = Integer.parseInt(inputArray[1]);
			 String genre = inputArray[1];
			 String title = inputArray[3];
			 list.orderedInsertMovie(genre, title);
			 //list.orderedInsert(genre);
			 //list.add(index, genre);
		} else if ( operation.contains("DELETE") && inputLength >= 4) { // DELETE %genre% %title% 
			 String genre = inputArray[1];
			 String title = inputArray[3];
			 list.deleteMovie(genre, title);
			 //list.remove(genre);
		} else if ( operation.contains("PRINT") && inputLength >= 1) { // PRINT
			list.printAllMovie();
		} else if (operation.contains("SEARCH") && inputLength >= 2) { // SEARCH %title%
			 String title = inputArray[1];
			 list.searchMovie(title);			
		} else {
			;
		}
	}
}