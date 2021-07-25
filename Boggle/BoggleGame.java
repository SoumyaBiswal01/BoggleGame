package Boggle;
import java.io.*;
import java.util.*;

public class BoggleGame {
	HashSet<String> words=new HashSet<>(); 
    static  class TrieNode
    {
        TrieNode []child;
        boolean isWord;
        TrieNode(char c)
        {
            isWord=false;
            child=new TrieNode[26];
        }
    }
    private TrieNode root;
    public BoggleGame()
    {
        root=new TrieNode('\0');
    }

    public void insert(String word)	//Inserts Word in trie
    {
        TrieNode curr=root;
        for(int i=0; i<word.length(); i++)
        {
            char c=word.charAt(i);
            if(curr.child[c-'a']==null)
                curr.child[c-'a']=new TrieNode(c);

            curr=curr.child[c-'a'];
        }
        curr.isWord=true;

    }

    void findWords(char[][] boggle)	//Finding words present in the boggle
    {
        int m=boggle.length;
        int n=boggle[0].length;
        boolean[][] visited =new boolean[m][n];
        String str = "";

        
        for (int i = 0; i < m; i++) 
        {
            for (int j = 0; j < n; j++) 
            {
                seachWord(boggle,visited,i,j,str,m,n);
            }
        }
    }
    void seachWord(char[][] boggle, boolean[][] visited, int i, int j, String str, int m, int n)	//A recursive function to search all words present in the boggle
    {
        visited[i][j]=true;
        str+=boggle[i][j];	//Marking current cell as visited and appending current character to str 
        if(search(str))		//If str is present, add it 
        {
            words.add(str);
        }

        for(int row=i-1; row<=i+1 && row<m ; row++)		//Traversing 8 adjacent cells of boggle 
        {
            for (int col = j - 1; col <= j + 1 && col < n; col++) 
            {
                if (row >= 0 && col >= 0 && !visited[row][col])
                	seachWord(boggle, visited, row, col, str, m, n);
            }
        }
        visited[i][j]=false;	
    }

    public boolean search(String word) //Returns if the word is in trie or not
    {
        TrieNode node=getNode(word);
        return node!=null && node.isWord;
    }
    
    private TrieNode getNode(String word)
    {
        TrieNode curr=root;
        for(int i=0; i<word.length(); i++)
        {
            char c=word.charAt(i);
            if(curr.child[c-'a']==null)	return null;

            curr=curr.child[c-'a'];
        }
        return curr;
    }
    
    public static void main(String[] args) {
    	BoggleGame b=new BoggleGame();
        try
        {
            FileReader fr=new FileReader("C:\\Users\\SOUMYA\\OneDrive\\Desktop\\JAVAxDSA assignments\\JAVA classes\\Java-DSA-LPU\\Project\\Day - 35, July 17\\mydictionary.txt");
            Scanner sc=new Scanner(fr);
            while (sc.hasNextLine())
            {
                String s=sc.nextLine();
                b.insert(s);
               // System.out.println(sc.nextLine());
            }
            sc.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        char[][] boggle ={ {'r','e','a','a'},
                		   {'l','s','t','g'},
                		   {'f','a','r','u'},
                		   {'e','n','h','j'} };
        
        b.findWords(boggle);
        System.out.println(b.words);
    }
}
