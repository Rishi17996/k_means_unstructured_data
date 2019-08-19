Please read this file before running the code Wadekar_Lab1.java

In order to run the program, keep the following files in the same folder:
1. Wadekar_Lab1.java
2. Stemmer.java
3. Lab1_Data (note that the files contained in Lab1_Data are not in this folder, 
		but they are in "Lab1_Data")
4. stopwords.txt

NOTE: 
1. To print the postingsLists, uncommment line 65 "printPostingsLists();"
2. To print stopWords, uncomment the code from line 47 to line 52.

The main() method calls the method execute() and the test cases are 
included in the execute() method. It is done so that it becomes
possible to use private class level attributes which are read/ updated
by most of the methods.

The class level attributes are:
ArrayList<String> stopWords: an array list of stopwords
ArrayList<String> termList: an arraylist of terms
ArrayList<ArrayList<Integer>> postingsLists: an array list of array list
						of postings
File[] fileList: a file array of all the files read from the folder Lab1_data

The methods that are implemented in the code:
main() : the main method of Wadekar_Lab1
execute() : the method that is called from main in order to make the private 
	    class level attributes accessible
readStopWords(String fileName) : this method reads the stopwords from 
				 "stopwords.txt" and stores them in stopWords.
readFile(File fileName, int i) : this method reads the files from Lab1_Data one
				 at a time and the int i is the document number
				 assigned to it.
addToPostingsLists(ArrayList<String> pureTokens, int i) : this method adds the
				 pure (stemmed) tokens to the termList and the
				 associated document numbers to postingsLists
printPostingsLists() : this method prints the postings lists in an attractive 
		       manner.
search(String query) : 	this is the search method used to search the documnet 
			that contains the word (query). It also stemms the query
			for better search.
merge(ArrayList<Integer> postings1, ArrayList<Integer> postings2) : this method
			takes 2 postings lists as arguments and performs the merge
			function on those 2 postings lists.
orFunction(String query) : this method takes a 2-word query and splits it into 
			2 words and performs OR function on them.
decideMerge(String query) : this method takes a multi word query and decides
			if it is a 2 word query or a 2+ word query and performs 
			merge function on all the words.
printResult(ArrayList<Integer> result): takes an ArrayList<Integer> as an
			argument and prints the document names associated with 
			those integers.

