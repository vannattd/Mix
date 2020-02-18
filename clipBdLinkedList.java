/**********************************************************************
 * This class creates a clip board linked list for copy, cut and paste
 *
 * @author Dylan Vannatter and Zack Frent
 * @version Winter 2019
 *********************************************************************/
public class clipBdLinkedList {

    /** top represents the first node in the list */
    private NodeCB top;

    /** tail represents the last node in the list */
    private NodeCB tail;

    /******************************************************************
     * Constructor for the clip board linked list class that sets the
     * top node to null and the tail node to null
     *****************************************************************/
    public clipBdLinkedList() {
        tail = top = null;
    }
}
