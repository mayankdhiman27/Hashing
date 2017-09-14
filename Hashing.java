
package hashing;
import java.util.*;
/**
 * 
 * @author dmayank
 */
public class Hashing {

    //Node which store key,value and next address(For Chaining of nodes)
    //Generic type
    static class Node<K ,V>{
        K key;
        V value;
        Node<K,V> next;
        
        public Node(K s,V v){
            key=s;
            value=v;
            next=null;
        }
    }
    
    //Class to represent Hash table which stores nodes.
    //Generic Type
    static class HashTable<K,V>{
        ArrayList<Node<K,V>> bucket;            //Used to store array of chains...(bucket which stores index)
        
        private int numbuckets;      //current capacity
        
        private int size;             //current size
        
        public HashTable(){         //To initialize empty chains
            bucket =new ArrayList<>();
            numbuckets=10;
            size=0;
            
            //Create empty chains
            for(int i=0;i<numbuckets;i++){
                bucket.add(null);
            }
            
        }
        
        public int size()
        {
            return size;
        }
        
        public boolean isEmpty()
        {
            return size()==0;
        }
        
        //Hash function to find index
        public int hashfn(K key){
            int hashcode=key.hashCode();         //Used to find hashcode from inbuilt hash generating function of java
            int index=hashcode % numbuckets;
            
            return index;
        }
        
        
        //Function to remove any element...Return type is V b'coz this returns the value corresponding to key removed
        public V remove(K key){
            //find hashfunction to find the index for given key
            int bucketindex=hashfn(key);
            
            //Head to index
            Node<K,V> head=bucket.get(bucketindex);
            
            Node<K,V> prev=null;
            
            while(head!=null){
                if(head.key.equals(key)){
                    break;
                }
                head=head.next;
                prev=head;
                //head=head.next;
            }
            
            if(head==null){
                return null;
            }
            
            size--;
            
            if(prev!=null){
                prev.next=head.next;
            }
            else{
                bucket.set(bucketindex,head);           //Setting null as head
            }
            
            return head.value;
            
        }
        
        public V get(K key){            //Returns value corresponding to key
            //Find bucketindex for the given key
            int bucketindex=hashfn(key);
            if(bucketindex<0){
                bucketindex*=-1;
            }
            //Node to point to head of the index
            Node<K,V> head=bucket.get(bucketindex);
            
            //Search through the chain
            while(head!=null){
                if(head.key.equals(key))
                    return head.value;
                head=head.next;
            }
            
            return null;        //If not found then return null
        }
        
        public void add(K key,V value){
            
            //Generate hash function to get bucket index
            int bucketindex=hashfn(key);
            if(bucketindex<0){                  //When bucket index becomes less than 0. b'coz when it becomes negative then 
                                                //it throws arrayIndexOutOfBoundException
                bucketindex*=-1;
            }
           // System.out.println(bucketindex);
            Node<K,V> head=bucket.get(bucketindex);
            
            //Search if value already exist...
            while(head!=null){
                if(head.key.equals(key)){           //if exists then update the value with new one
                    head.value=value;           
                    return;
                }
                head=head.next;
            }
            
            size++;
            
            //Insert in key chain
            head=bucket.get(bucketindex);
            
            //Create a new node with key, value pair
            Node<K,V> newnode=new Node<K,V>(key,value);
            
            newnode.next=head;
            
            bucket.set(bucketindex,newnode);        //set the buclet point to newnode..As it is inserted at start
            
            //Check load factor
            if((1.0*size)/numbuckets>=0.7){
                ArrayList<Node<K,V>> temp=bucket;       //Copying old bucket's element to temp bucket.
                bucket=new ArrayList<>();
                
                numbuckets=2*numbuckets;
                
                size=0;
                
                //initalize the new bucket
                for(int i=0;i<numbuckets;i++){
                    bucket.add(null);
                }
                
                //Copy data from temp to bucket
                for(Node<K,V> hn:temp){
                    while(hn!=null){
                        add(hn.key,hn.value);
                        hn=hn.next;
                    }
                }
                
            }
            
        }
        
        public void display(){
            for(int i=0;i<numbuckets;i++){
            System.out.print("Bucket "+i+" = ");
            Node<K,V> temp = bucket.get(i);

            while(temp!=null){
                System.out.print(temp.value+" -> ");
                temp=temp.next;
            }
            System.out.println();
        }
            
        }
        
        
    }
    
    public static void main(String[] args) {
        
        HashTable<String, Integer> ht=new HashTable<>();
        
        ht.add("Dhiman",100);
        ht.add("Amit",130);
        ht.add("Shubhi",20);
        ht.add("Ankit",50);
        //ht.add("Guava",120);
        ht.add("Bijay",200);
        //ht.add("Pineapple",150);
        ht.add("Anshu",80);         //If u start adding after this then as load factor reaches 0.7, so the size of bucket will double
        //ht.add("Dxcvd",90);
        //ht.remove("Mango");
        //ht.add("Litchi", 100);        //Litchi add krne me jb hashcode convert ho rha h using inbuilt hash code to 'L' k acrdng
                             
                                        //wo out of index ho jaa rha h.
        
          /*
                                        use this insertion to see collision
                                        If u want to run this then plz implement this hash function:
                                        The index for a specific string will be equal to the sum of the ASCII values of the characters modulo 599.
        ht.add("abcdef",100);
        ht.add("bcdefa",200);
        ht.add("cdefab",300);
        ht.add("defabc",400);
        */
        System.out.println(ht.size());
        
        ht.display();
        
        System.out.println(ht.get("Shubhi"));
            
    }
    
}

