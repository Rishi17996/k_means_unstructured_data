/**
 * import statements
 */
import java.io.*;
import java.util.*;

/**
 * This class contains the main method that runs Wadekar_Lab1.java.
 * Course:           ISTE 612: Knowledge Process Technologies
 * Name:             Wadekar, Rishi
 * Lab:              Lab #1
 * Date:             02/18/2019
 * @auhor            Rishi Wadekar
 */

class Wadekar_Lab01 {
   
   // class level attributes
   private ArrayList<String> stopWords;                  // list of stopwords
   private ArrayList<String> termList;                   // dictionary
   private ArrayList<ArrayList<Integer>> postingsLists;  // list of postingsList
   private File[] fileList;
   
   
   /**
    * This is the main method. It calls the execute method and
    * the main functionality of the program in included in the
    * execute() method.
    */
   public static void main(String[] args) {
   
      new Wadekar_Lab01().execute();
   
   }  // end method main
   
   
   /**
    * This method contains the functionality of the program.
    * It includes calling the methods to read stopwords,
    * constructing postingsLists and termlist. It also include
    * the test cases.
    */
   public void execute() {
      
      // populating arraylist with stopwords
      stopWords = readStopWords("stopwords.txt");
      /*
      // print stopWords
      for(String stopWord : stopWords) {
         System.out.println(stopWord);
      }
      */
      termList = new ArrayList<String>();
      postingsLists = new ArrayList<ArrayList<Integer>>();
      // read files
      fileList = new File("Lab1_Data").listFiles();
      try {
         int i = 1;                         // unique document number
         for(File oneFile : fileList) {
            ArrayList<String> pureTokens = readFile(oneFile, i);
            this.addToPostingsLists(pureTokens, i);
            i++;
         }
         // print postingsLists
         //printPostingsLists();
      }
      catch(Exception ex) {
         ex.printStackTrace();
      }
      // Following are the test cases
      System.out.println();
      // Test cases for search
      System.out.println("Test cases for search:");
      // 1
      search("posting");
      System.out.println();
      // 2
      search("ultiMAtEly");
      System.out.println();
      // Test cases for AND function
      System.out.println("Test cases for AND function: ");
      // 1
      System.out.println("Query: minute character");
      decideMerge("minute character");
      System.out.println();
      // 2
      System.out.println("Query: time fact");
      decideMerge("time fact");
      System.out.println();
      // Test cases for OR function
      System.out.println("Test cases for OR function: ");
      // 1
      System.out.println("Query: cool danger");
      printResult(orFunction("cool danger"));
      System.out.println();
      // 2
      System.out.println("Query: end border");
      printResult(orFunction("end border"));
      System.out.println();
      // Test cases for AND function (queries > 2)
      System.out.println("Test cases for AND function (queries > 2): ");
      // 1
      System.out.println("Query: chick owner ensures return");
      decideMerge("chick owner ensures return");
      System.out.println();
      // 2
      System.out.println("Query: shoWing AMERIcan attempts");
      decideMerge("shoWing AMERIcan attempts");
     
      // 
      // 2
      System.out.println("Query: brain virus kick");
      decideMerge("brain virus kick");
   }  // end method execute
   
   
   /**
    * This method reads the words from the stopwords file and
    * stores them in the String ArrayList of stopwords.
    * 
    * @param fileName      name of the file to be read.
    * @return              returns the arraylist of stopwords.
    * 
    */
   public ArrayList<String> readStopWords(String fileName) {
   
      ArrayList<String> stopWords = new ArrayList<String>();
      try {
         File stopWordsFile = new File(fileName);
         Scanner scan = new Scanner(stopWordsFile);
         while(scan.hasNextLine()) {
            stopWords.add(scan.nextLine().toLowerCase());
         }
      }
      catch(Exception e) {
         e.printStackTrace();
      }      
      return stopWords;
      
   }  // end method readStopWords
   
   
   /**
    * Read the file and perform the stemming function.
    * 
    * @param fileName      name of the file to be read.
    * @param i             the document number assigned to the file.
    * @return              returns the arraylist of stemmed words.
    * 
    */
   public ArrayList<String> readFile(File fileName, int i) throws IOException {
   
      ArrayList<String> stemms = new ArrayList<String>();
      ArrayList<String> tokens = new ArrayList<String>();
      try {
         String allLinesInDoc = "";
         tokens = new ArrayList<String>();
         Scanner sc = new Scanner(fileName);
         while(sc.hasNextLine()) {
            allLinesInDoc += sc.nextLine().toLowerCase();
         }
         String[] words = allLinesInDoc.trim().split("[ '.,?!:;$&%+()\\-\\*\\/\\p{Punct}\\s]+");
         for(String word : words) {
            if(!stopWords.contains(word)) {  
               if(!tokens.contains(word)) {
                  tokens.add(word);
               }
            }
         }
         String[] stringArray = tokens.toArray(new String[0]);
         // stemming
         Stemmer st = new Stemmer();
         for(String token: stringArray) {
            st.add(token.toCharArray(), token.length());
            st.stem();
            stemms.add(st.toString());
            st = new Stemmer();
         }
      }
      catch(Exception ex) {
         ex.printStackTrace();
      }
      return stemms;
      
   }  // end method readFile
   
   
   /**
    * Add the stemmed words to the postingsLists.
    * 
    * @param pureTokens    arraylist of stemmed words
    * @param i             the document number assigned to the list 
    *                      of stemmed words.
    * 
    */
   public void addToPostingsLists(ArrayList<String> pureTokens, int i) {
            
      for(String pT : pureTokens) {
         if(!termList.contains(pT)) {
            termList.add(pT);
            ArrayList<Integer> postings = new ArrayList<Integer>();
            postings.add(i);
            postingsLists.add(postings);
         }
         else {
            int index = termList.indexOf(pT);
            ArrayList<Integer> postings = postingsLists.remove(index);
            if(!postings.contains(i)) {
               postings.add(i);
            }
            postingsLists.add(index, postings);
         }
      }
   
   }  // end method addToPostingsLists
   
   
   /**
    * Print the postingsLists
    * 
    */
   public void printPostingsLists() {
   
      String outputString = new String();
      for(int i = 0; i < termList.size(); i++) {
         outputString += String.format("%-15s", termList.get(i));
         ArrayList<Integer> postings = postingsLists.get(i);
         for(int j = 0; j < postings.size(); j++) {
            outputString += postings.get(j) + "\t";
         }
         outputString += "\n";
      }
      System.out.println(outputString);
   
   }  // end method printPostingsLists
   
   
   /**
    * Search the query after stmming it. Print the result.
    * 
    * @param query         the query to be seatched
    * 
    */
   public void search(String query) {
   
      Stemmer st = new Stemmer();
      String word = query.toLowerCase();
      st.add(word.toCharArray(), word.length());
      st.stem();
      word = st.toString();
      System.out.println("You searched for: " + query);
      int termIndex = termList.indexOf(word);
      if(termIndex < 0) {
         System.out.println("Cannot find query " + query);
      }
      else {
         ArrayList<Integer> postings = postingsLists.get(termIndex);
         System.out.print("Result: ");
         for(int i: postings) {
            System.out.print(fileList[i-1].getName() + " ");
         }
      }
      System.out.println();
   }  // end method search
   
   
   /**
    * Perform the merge algorithm (AND) on the 2 postings lists passed as
    * arguments.
    * 
    * @param postings1        first postings list.
    * @param postings2        second postings list.
    * @return                 returns the arraylist of the result.
    * 
    */
   public ArrayList<Integer> merge(ArrayList<Integer> postings1, ArrayList<Integer> postings2) {
      
      ArrayList<Integer> answer = new ArrayList<Integer>();      
      int pointer1 = 0, pointer2 = 0;
      try {
         do {
            if(postings1.get(pointer1) == postings2.get(pointer2)) {
               answer.add(postings1.get(pointer1));
               pointer1++;
               pointer2++;
            }
            else if(postings1.get(pointer1) < postings2.get(pointer2)) {
               pointer1++;
            }
            else {
               pointer2++;
            }
         }while((pointer1 != postings1.size()) || (pointer2 != postings2.size()));
      }
      catch(Exception ex) {
      }
      return answer;
      
   }  // end method merge
   
   
   /**
    * Perform the OR function postings lists of the words passed as
    * arguments.
    * 
    * @param query      a biword query to perform the OR function.     
    * @return           returns the arraylist of the result.
    * 
    */
   public ArrayList<Integer> orFunction(String query) {
      
      ArrayList<String> stemmedQueryWords = new ArrayList<String>();
      Stemmer st = new Stemmer();
      String[] queryWords = query.toLowerCase().split(" ");
      for(String token : queryWords) {
         st.add(token.toCharArray(), token.length());
         st.stem();
         stemmedQueryWords.add(st.toString());
         st = new Stemmer();
      }
      ArrayList<Integer> answer = new ArrayList<Integer>();
      int termIndex1 = termList.indexOf(stemmedQueryWords.get(0));
      int termIndex2 = termList.indexOf(stemmedQueryWords.get(1));
      ArrayList<Integer> postings1 = postingsLists.get(termIndex1);
      ArrayList<Integer> postings2 = postingsLists.get(termIndex2);
      answer.addAll(postings1);
      for(int j: postings2) {
         if(!answer.contains(j)) {
            answer.add(j);
         }
      }
      return answer;
      
   }  // end method orFunction
   
   
   /**
    * Decide whether to perform the merge function on biword
    * query or 2+ word query.
    * 
    * @param query      the query to be processed.
    * 
    */
   public void decideMerge(String query) {
      
      ArrayList<String> stemmedWords = new ArrayList<String>();
      Stemmer st = new Stemmer();
      String[] queryWords = query.toLowerCase().split(" ");
      for(String token : queryWords) {
         st.add(token.toCharArray(), token.length());
         st.stem();
         stemmedWords.add(st.toString());
         st = new Stemmer();
      }
      try {
         if(stemmedWords.size() == 2) {
            int termIndex1 = termList.indexOf(stemmedWords.get(0));
            int termIndex2 = termList.indexOf(stemmedWords.get(1));
            ArrayList<Integer> postings1 = postingsLists.get(termIndex1);
            ArrayList<Integer> postings2 = postingsLists.get(termIndex2);
            printResult(merge(postings1, postings2));
         }
         else {
            ArrayList<Integer> answer = new ArrayList<Integer>();
            ArrayList<Integer> nextPostings = new ArrayList<Integer>();
            int[] termIndices = new int[stemmedWords.size()];
            int[] postingsLengths = new int[stemmedWords.size()];
            for (int i = 0; i < stemmedWords.size(); i++) {
               termIndices[i] = termList.indexOf(stemmedWords.get(i));
               postingsLengths[i] = postingsLists.get(termIndices[i]).size();
            }
            // sorting indices according to lengths of postings lists 
            // using bubble sort.
            int n = postingsLengths.length;
            for(int i = 0; i <n-1; i++) {
               for(int j = 0; j < n-i-1; j++) {
                  if(postingsLengths[j] >= postingsLengths[j+1]) {
                     int temp = postingsLengths[j];
                     postingsLengths[j] = postingsLengths[j+1];
                     postingsLengths[j+1] = temp;
                     int temp2 = termIndices[j];
                     termIndices[j] = termIndices[j+1];
                     termIndices[j+1] = temp2;
                  }
               }
            }
            answer = postingsLists.get(termIndices[0]);
            for(int i = 1; i < n; i++) {
               nextPostings = postingsLists.get(termIndices[i]);
               answer = merge(answer, nextPostings);
            }   
            if(answer.size() == 0) {
               System.out.println("No such documents found");
            }
            else {
               printResult(answer);
            }
         }
      }
      catch(Exception e) {}
      
   }  // end method decideMerge
   
   
   /**
    * Print the arraylist output of the AND and OR functions.
    * 
    * @param result     an arraylist of the result to be printed
    * 
    */
   public void printResult(ArrayList<Integer> result) {
      if (result.size() == 0) {
         System.out.println("No documents match the query.");
      }
      else {
         System.out.println("Result: ");
         for(int j : result) {
            System.out.println(fileList[j-1].getName());
         }
      }
      System.out.println();
   }  // end method printResult
   
}  // end class Wadekar_Lab01