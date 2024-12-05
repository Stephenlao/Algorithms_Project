package Hueristics;

// Singly Linked List-based implementation of Priority Queue
//public class LinkedListPriorityQueue<T> {
//    // Container for data and priority
//    static class Node<T> {
//        T data;
//        int priority;
//        Node<T> next;
//
//        public Node(T data, int priority) {
//            this.data = data;
//            this.priority = priority;
//            this.next = null;
//        }
//    }
//
//    private int size;
//    private Node<T> head;
//
//    public LinkedListPriorityQueue() {
//        size = 0;
//        head = null;
//    }
//
//    public int size() {
//        return size;
//    }
//
//    public boolean isEmpty() {
//        return size == 0;
//    }
//
//    // Enqueue with priority
//    public boolean enQueue(T item, int priority) {
//        Node<T> newNode = new Node<>(item, priority);
//
//        // Case 1: Empty queue, new node becomes head
//        if (head == null) {
//            head = newNode;
//        }
//        // Case 2: New node has higher priority than the current head
//        else if (priority > head.priority) {
//            newNode.next = head;
//            head = newNode;
//        }
//        // Case 3: Insert new node at the correct position based on priority
//        else {
//            Node<T> current = head;
//            while (current.next != null && current.next.priority >= priority) {
//                current = current.next;
//            }
//            newNode.next = current.next;
//            current.next = newNode;
//        }
//
//        size++;
//        return true;
//    }
//
//    // Dequeue the element with the highest priority
//    public boolean deQqueue() {
//        if (isEmpty()) {
//            return false;
//        }
//        head = head.next; // Remove the head node
//        size--;
//        return true;
//    }
//
//    // Peek the element with the highest priority (front of the queue)
//    public T peekFront() {
//        if (isEmpty()) {
//            return null;
//        }
//        return head.data;
//    }
//
//    public static void main(String[] args) {
//        LinkedListPriorityQueue<String> priorityQueue = new LinkedListPriorityQueue<>();
//
//        // Enqueue items with priorities
//        priorityQueue.enQueue("Low priority", 1);
//        priorityQueue.enQueue("High priority", 3);
//        priorityQueue.enQueue("Medium priority", 2);
//
//        // Dequeue and print items in priority order
//        while (!priorityQueue.isEmpty()) {
//            System.out.println(priorityQueue.peekFront());
//            priorityQueue.deQqueue();
//        }
//    }
//}


import java.util.LinkedList;

public class LinkedListPriorityQueue<T extends Comparable<T>> {
    private LinkedList<T> list;

    public LinkedListPriorityQueue() {
        list = new LinkedList<>();
    }

    public void add(T item) {
        int i = 0;
        while (i < list.size() && list.get(i).compareTo(item) <= 0) {
            i++;
        }
        list.add(i, item); // Insert item at the correct position based on priority
    }

    public T poll() {
        if (list.isEmpty()) {
            return null;
        }
        return list.removeFirst(); // Remove the element with the highest priority
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}