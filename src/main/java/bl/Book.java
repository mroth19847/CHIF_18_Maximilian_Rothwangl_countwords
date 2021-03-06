package bl;

import java.util.HashMap;

public class Book {

    private String inputfilename;
    private String text;

    public Book(String inputfilename, String text) {
        this.inputfilename = inputfilename;
        this.text = text;
    }
    
    public HashMap<String, Integer> countWords(){
        HashMap<String, Integer> wordcounter = new HashMap<>();
        String[] words = getFilteredText().split(" ");
        for (int i = 0; i < words.length; i++) {
            if(!wordcounter.containsKey(words[i])&&words[i] != " "){
                int counter = getWordNumber(words[i], words);
                wordcounter.put(words[i], counter);
            }
        }
        return wordcounter;
    }
    
    public String getOutputFilename(){
        String[] hStr = inputfilename.split("\\\\");
        String name =  hStr[hStr.length - 1].replace(".txt", "");
        return name + "_output.txt";
    }
    
    public int getWordNumber(String word, String[] words){
        int counter = 0;
        for (int i = 0; i < words.length; i++) {
            if(words[i].equalsIgnoreCase(word)){
                counter ++;
            }
        }
        return counter;
    }
    
    public String getFilteredText(){
        String filtered = text;
        for (int i = 0; i < filtered.length(); i++) {
            if(!(Character.isLetter(filtered.charAt(i))||Character.isWhitespace(filtered.charAt(i)))){
                filtered = filtered.replace(""+filtered.charAt(i), "");
            }
        }
        return filtered;
    }

    public String getText() {
        return text;
    }

    public String getInputfilename() {
        return inputfilename;
    }
}
