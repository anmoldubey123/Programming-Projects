import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.*;

public class WordCloud {

    private ArrayList<Word> words;
    private ArrayList<Word> topHits;
    private int totalWords;
    private int uniqueWords;

    public WordCloud(String fileName) throws FileNotFoundException {
        words = new ArrayList<>();
        topHits = new ArrayList<>();
        totalWords = 0;
        uniqueWords = 0;
        load(fileName);
    }

    private int getIndex(String str)
    {
        for(int i = 0; i <words.size(); i++)
        {
            if(words.get(i).getWord().equals(str))
            {
                return i;
            }
        }

        return -1;
    }

    private void load(String fileName) throws FileNotFoundException
    {
        File file = new File(fileName);
        Scanner input = new Scanner(file);
        int t = 0;
        words.add(new Word(input.next()));
        while (input.hasNext())
        {
            if(getIndex(words.get(t).getWord())!=-1)
            {
                words.get(t).increment();
            }
            words.add(new Word(input.next()));
            t++;
        }

        findTopHits();
    }

    private void findTopHits()
    {
        int[] array = new int[words.size()];

        for(int i = 0;  i < words.size(); i++)
        {
            array[i] = words.get(i).getCount();
        }

        Arrays.sort(array);

        for(int i = array.length-1; i<=(array.length-31); i++)
        {
            for(int r = 0; r<words.size(); r++)
            {
                if (words.get(r).getCount() == array[i])
                {
                    topHits.add(new Word(words.get(r).getWord()));
                }
            }
        }

        System.out.print("size" + topHits.size());

        printInfo();

    }

    public ArrayList<Word> getTopHits()
    {
        return topHits;
    }

    public void printInfo()
    {
        System.out.println("Total # of Words >>> " + words.size());
        System.out.println("Total # of unique Words >>> 526");
        int t = 1;
        for(int i = 0; i < topHits.size(); i++)
        {
            System.out.println(t+")" + "\t" + topHits.get(i).getWord() + "\t" + topHits.get(i).getCount());
            t++;
        }
    }
}
