import java.io.Serializable;

/**********************************************************************
 * This class creates a double linked list of characters without a tail
 *
 * @author Dylan Vannatter and Zack Frent
 * @version Winter 2019
 *********************************************************************/
public class DoubleLinkedList<E>  {

    /** top represents the first node in the list */
    protected NodeD<E> top;

    /** cursor represents the current node in the list */
    protected NodeD<E> cursor;

    /******************************************************************
     * Constructor for the DoubleLinkedList class that sets the first
     * node to null and the current node to null
     *****************************************************************/
    public DoubleLinkedList() {
        top = null;
        cursor = null;
    }

    /******************************************************************
     * Method that gets the position of the current node
     *
     * @param position int where the element is in the list
     * @return cursor.getData() the data that's inside the element
     *****************************************************************/
    public E get(int position) {
        cursor = top;
        for (int i = 0; i < position; i++)
            cursor = cursor.getNext();
        return cursor.getData();

    }

    /******************************************************************
     * Method that returns a toString of the nodes
     *
     * @return retVal a toString of the data
     *****************************************************************/
    public String toString() {
        String retVal = "";
        NodeD<E> cur = top;
        while (cur != null) {
            retVal += cur.getData();
            cur = cur.getNext();
        }

        return retVal;
    }

    /******************************************************************
     * Method that returns the size of the double linked list
     *
     * @return count the size of the double linked
     *****************************************************************/
    public int size(){
        cursor = top;
        int count = 0;
        while(cursor != null) {
            count++;
            cursor = cursor.getNext();
        }
        return count;
    }

    /******************************************************************
     * Method that adds a user entered character into the message /
     * double link list
     *
     * @param c1 E a character in the double linked list message
     *****************************************************************/
    public void add (E c1){
        NodeD addition = new NodeD<>();
        addition.setData(c1);
        if(top == null){
            top = addition;
            addition.setNext(null);
            addition.setPrev(top);
        }
        else{
            NodeD temp = top;
            while(temp.getNext() != null){
                temp = temp.getNext();
            }
            temp.setNext(addition);
            addition.setPrev(temp);
        }

    }



    /******************************************************************
     * Method that removes a character at a specific index in the
     * double link list
     *
     * @param index int the position you want to remove a character
     *****************************************************************/
    public void remove(int index){
        if (index<0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        cursor = top;
        for (int i = 0; i < index; i++) {
            cursor = cursor.next;
        }
        if (index == 0) {
            top = cursor.next;
        } else {
            cursor.prev.next = cursor.next;
            cursor.next.prev = cursor.prev;
        }
    }



    /******************************************************************
     * Method that adds a user entered character into the message /
     * double link list at a specific index
     *
     * @param index int the position you want to add the character
     * @param data E a character in the double linked list message
     *****************************************************************/
    public void add (int index, E data) {
        if (index < 0)
            throw new IndexOutOfBoundsException();
        if (index > size() - 1)
            throw new IllegalArgumentException();
        if (top == null)
            top = new NodeD(data, null, null);
        NodeD<E> addition = new NodeD<>();
        addition.setData(data);
        if (size() == 1)
            top = addition;
        else {
            if (index == 0) {
                addition.setNext(top);
                top = addition;
            } else {
                cursor = top;
                for (int i = 0; i < index; i++) {
                    cursor = cursor.getNext();
                }
                cursor = new NodeD<E>(data,cursor,
                        cursor.getPrev());
                cursor.getNext().setPrev(cursor);
                cursor.getPrev().setNext(cursor);
            }
        }
    }

    /******************************************************************
     * Method that replaces all c1 characters with c2 characters
     *
     * @param c1 E  the character you want replaced
     * @param c2 E  the character you are replacing c1 with
     *****************************************************************/
    public void replaceAll(E c1, E c2) {
        cursor = top;
        while (cursor != null) {
            if (cursor.getData() == c1) {
                cursor.setData(c2);
            }
            cursor = cursor.getNext();
        }
    }
}


