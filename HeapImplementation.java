import javax.swing.*;
import java.util.*;
import java.util.Arrays;
import java.io.File;
import java.util.Scanner;
import java.util.Random;

public class HeapImplementation {
	
	//static HashMap<Integer,Integer> checkA = new HashMap<>();
	
	//Function for the asking the users input page 
	public static int choosePage()
	{
		System.out.print("Choose page you want to change = ");
    	Scanner input = new Scanner(System.in);
    	int inputPage = input.nextInt();
    	return inputPage;
	}
	
	//Function for the asking the users input score
	public static int inputScore()
	{
		Scanner input = new Scanner(System.in);
    	System.out.print("What score you want to input = ");
    	int inputScore = input.nextInt();
    	return inputScore;
	}
	
	//Function for copy array
	public static void copyArray(int A[], int B[])
	{
		for(int i=0;i<A.length;i++)
    	{
    		A[i]=B[i];
    	}
	}
	
	//Function for swap number
	public static void swapNumber(int A[],int i, int j)
	{
		int temp = A[i];
		A[i] = A[j];
		A[j]=temp;
	}
	
	//Function for printing the array
	public static void printArray(int A[])
	{
		for(int i=0;i<A.length;i++)
    	{
    		System.out.println("Page "+ (i+1) + ": " + A[i]);
    	}
	}
	
	//Function for printing the random number
	public static int randomNumber()
	{
		Random rand = new Random();
		int  n = rand.nextInt(100) + 1;
		return n;
	}
	
	//Function for getting the answer from the user
	public static void getAnswer
	(int menu, HashMap<Integer,Integer> saveHashMap, Queue<Integer> saveQueue, 
	int[] frequencyScore, int[] timeScore, int[] numberScore, int[] paidScore, int[]totalScore)
	{
		//Get the page and the score
		int inputPage = choosePage();
    	int inputScore = inputScore();
    	
    	int total = 0;
    	inputPage = inputPage - 1;
    	
    	//copy the real score array to the copy array
   		int[] frequencyScoreCopy = new int[30];
    	int[] timeScoreCopy = new int[30];
    	int[] numberScoreCopy = new int[30];
    	int[] paidScoreCopy = new int[30];
    	copyArray(frequencyScoreCopy,frequencyScore );
		copyArray(timeScoreCopy, timeScore);
		copyArray(numberScoreCopy,numberScoreCopy);
		copyArray(paidScoreCopy, numberScoreCopy);
    	
		//Will change the score depends on the users input
    	for(int i=0;i<30;i++)
       	{
    		if(i==inputPage && menu == 1) {
    			frequencyScore[i] = inputScore;
    			HeapIncreaseKey(frequencyScoreCopy,inputPage,inputScore);
    			
    		}
    		else if (i == inputPage && menu == 2){
    			timeScore[i] = inputScore;
        		HeapIncreaseKey(timeScoreCopy,inputPage,inputScore);
    		}
    		else if(i == inputPage && menu == 3) {
    			numberScore[i] = inputScore;
        		HeapIncreaseKey(numberScoreCopy,inputPage,inputScore);
    		}
    		else if(i == inputPage && menu == 4) {
    			paidScore[i] = inputScore;
        		HeapIncreaseKey(paidScoreCopy,inputPage,inputScore);
    		}

    		//Will put the total score into the array	
    		total = frequencyScore[i] + timeScore[i] + numberScore[i] + paidScore[i];
       		totalScore[i] = total;
       		
       		saveHashMap.put(total, i);
    		
    		//Put the total into the queue
    		saveQueue.add(total);
        	saveHashMap.remove(total);
    		
    		//Using hashmap to get the page and the score
        	int page = i + 1;
    		saveHashMap.put(total,page);
    	}
	}
	
	//Function for getting the top 10 URLs
	public static void getTop(Queue<Integer> saveQueue, HashMap<Integer,Integer> saveHashMap )
	{
		System.out.println("The Top 10 Page: ");
       	//int countF = 0;
       	int countS = 0;
       	boolean checkO = true;
       	while(checkO)
       	{
       		//remove the top queue
       		int scoreMax = saveQueue.remove();
       		
       		//Get the page by using hashmap and get the score
    		System.out.println("Score page " + (saveHashMap.get(scoreMax)) + ": " +  scoreMax);
    		//countF++;		        		
    		countS ++;
    		//If already get the top 10, stop the loop
        	if(countS==10)
        		checkO = false;
        }
	}
	
	static int heap_size;
	
	public static int parent(int i)
	{
		return i/2;
	}
	public static int left(int i)
	{
		return 2*i;
	}
	public static int right(int i)
	{
		return 2*i + 1;
	}
	
	//Function for building the max heap
	public static void BuildMaxHeap(int A[])
	{
		heap_size = A.length-1;
		for(int i=(A.length-1)/2  ; i >= 0 ; i-- )
		{
			MaxHeapify(A,i);
		}
	}
	
	//Function for max heapify
	public static void MaxHeapify(int A[], int i)
	{
		int largest;
		int l = left(i);
		int r = right(i);
		
		if(l<= heap_size && A[l]>A[i])
			largest = l;
		else
			largest = i;
		
		if(r<= heap_size && A[r]>A[largest])
			largest = r;
		
		if(largest!=i)
		{
			swapNumber(A,i,largest);
			MaxHeapify(A,largest );
			
		}
	}
	
	//Function for heap sort
	public static void HeapSort(int A[])
	{
		BuildMaxHeap(A);
		int length = A.length-1;
		for(int i=length; i>=1; i--)
		{
			swapNumber(A,0,i);
			heap_size = heap_size-1;
			MaxHeapify(A,0);
		}
	}
	
	//Function for heap increase key
	public static void HeapIncreaseKey(int A[], int i, int key)
	{
		if(key < A[i])
			System.out.println("New Key is smaller than current key\n");
		A[i] = key;
		while(i>1 && A[parent(i)] < A[i])
		{
			swapNumber(A,i,parent(i));
			i = parent(i);
		}
	}
	
	//Function for max heap insert
	public static void MaxHeapInsert(int A[], int key)
	{
		heap_size = heap_size+1;
		A[heap_size] = Integer.MIN_VALUE;
		HeapIncreaseKey(A,heap_size,key);
	}
	
	
	//Function for heap extract max
	public static int HeapExtractMax(int A[])
	{
		
		if(heap_size<0)
			System.out.println("Heap underflow\n");
		int max = A[0];
		A[0] = A[heap_size];
		heap_size = heap_size-1;
		MaxHeapify(A,0);
		return max;
	}
	
	
	//Function for get the heap maximum
	public static int HeapMaximum(int A[])
	{
		return A[0];
	}
	 
	//Function for show the menu which user want to change
 	public static void showMenuScore()
	{
		System.out.println("\nWhat score do you want to change: ");
		System.out.println("1. The frequency and location of keywords within the Web page");
		System.out.println("2. The long web page score has existed");
		System.out.println("3. The number page score that link to the page in question ");
		System.out.println("4. The web page owner has paid to Google for advertisement purpose");
		System.out.println("5. Exit \n");
	}
	
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		//Declaration of hashmap that we are going to use
		HashMap<Integer,Integer> totalScoreHash = new HashMap<Integer,Integer>();
		HashMap<Integer,Integer> manipulationScore = new HashMap<Integer,Integer>();
		//HashMap<Integer,Integer> newTotalScoreHash = new HashMap<Integer,Integer>();
		
		//Declaration of array that we are going to use
		int[] frequencyScore = new int[30];
		int[] timeScore = new int[30];
		int[] numberScore = new int[30];
		int[] paidScore = new int[30];
		int[] totalScore = new int[30];
		
		
		//Reading file for website.txt
		String token1 = "";

    	// create Scanner inFile1
    	Scanner inFile1 = new Scanner(new File("website.txt"));

    	List<String> temps = new ArrayList<String>();

    	//Get the 30 URLs from the website txt
    	while (inFile1.hasNext()) {
      	// find next line
    		token1 = inFile1.next();
    		temps.add(token1);
    	}
    	
    	inFile1.close();
    	//Move the list into the array
    	String[] tempsArray = temps.toArray(new String[0]);
    	
    	//Output the website
    	for(int i=0;i<tempsArray.length-1;i++)
    	{
    		System.out.println("Page "+ (i+1) + ": " + tempsArray[i]);
    	}
		
    	PriorityQueue<Integer> maxPQ = new PriorityQueue<Integer>(Collections.reverseOrder());
    	PriorityQueue<Integer> test = new PriorityQueue<Integer>(Collections.reverseOrder());

    	int numberPage=1;
    	//Put a random number to the array as the score
    	for(int i=0;i<tempsArray.length-1;i++)
    	{
    		frequencyScore[i] = randomNumber();
    		timeScore[i] = randomNumber();
    		numberScore[i] = randomNumber();
    		paidScore[i] = randomNumber();
    		
    		int total = frequencyScore[i] + timeScore[i] + numberScore[i] + paidScore[i];
    		int divide =0;
    		//The hashmap try to manipulate the score, 
    		if(manipulationScore.containsKey(total)){
    			while(manipulationScore.containsKey(total)){
    				total++;
    			}
    			divide = total/4;
    			frequencyScore[i] = divide; 
    			timeScore[i] = divide; 
    			numberScore[i] = divide; 
    			paidScore[i] = divide;
    		}
    		else
    			manipulationScore.put(total, 1);
    		
    		totalScore[i] = total;
    		totalScoreHash.put(total,numberPage);
    		numberPage++;
    		//maxPQ.add(total);  
    	}
    	
    	//Print the score
    	System.out.println("\nBased on The frequency and location of keywords within the Web page: ");
    	printArray(frequencyScore);
    	
    	System.out.println("\nBased on How long the Web page has existed: ");
    	printArray(timeScore);
    	
    	System.out.println("\nBased on The number of other Web pages that link to the page in question: ");
    	printArray(numberScore);
    	    	
    	System.out.println("\nBased on How much the web page owner has paid to Google for advertisement purpose: ");
    	printArray(paidScore);
    	
    	
    	System.out.println("\nTotal score: ");
    	printArray(totalScore);
    	
    	int[] newArr = new int[30];
       	int[] testArr = new int[30];
       	//int[] sideArr = new int[30];

    	
    	copyArray(newArr,totalScore);
    	
    	copyArray(testArr,totalScore);
    	//copyArray(sideArr,totalScore);
    	
    	//Sorting the heap 
    	HeapSort(newArr);
    	//HeapSort(sideArr);
    	
    	BuildMaxHeap(testArr);
    	
    	
    	System.out.println("\nAfter Sorting, The Total Score: ");
    	for(int i=0;i<newArr.length;i++)
    	{
    		System.out.println("Score page " + totalScoreHash.get(newArr[i]) + ": " +  newArr[i]);
    	}
    	
    	
    	//Extract the max, then output
		for(int i=0;i<newArr.length-1;i++)
		{
			test.add(HeapExtractMax(testArr));
		}
    	System.out.print("\n");
   		getTop(test,totalScoreHash);
    	
    	//Get the users input
    	boolean testWhile = true;
    	while(testWhile)
    	{
	    	showMenuScore();
	    	Scanner inputM = new Scanner(System.in);
	    	System.out.print("Choose option = ");
	    	int inputMenu = inputM.nextInt();
	    	
	    	if(inputMenu == 5){
	    		break;
	    	}
	    	//Function to get the score
	    	getAnswer(inputMenu, totalScoreHash, test, frequencyScore, timeScore, numberScore, paidScore, totalScore);
		    
	    	//Output the top 10 URLs
	   		getTop(test,totalScoreHash);
	   		
    	}//while loop
    	
	}//main

}
