package pojos;


import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Trie {
   private Integer isWord;
   Map<Character,Trie> children;

   Map<Character,Integer> characterCountMap;

   public Trie(){
      isWord=0;
      children= new HashMap<>();
      characterCountMap= new HashMap<>();
   }

   public void insertWord(String word){
      Trie root= this;
      Map<Character,Trie> children= root.children;
      Map<Character,Integer> characterCountMap= root.characterCountMap;
      for(int i=0;i<word.length();i++){
         Character c= word.charAt(i);
        if(!children.containsKey(c)){
           children.put(c,new Trie());
        }
        characterCountMap.put(c,characterCountMap.getOrDefault(c,0)+1);
        if(i== word.length()-1){
           Trie trie= children.get(c);
           trie.setIsWord(trie.getIsWord()+1);
        }
        characterCountMap = children.get(c).getCharacterCountMap();
        children = children.get(c).getChildren();
      }
   }

   public boolean search(String word){
      Trie root= this;
      Map<Character,Trie> children= root.children;
      for(int i=0;i<word.length();i++){
         Character c= word.charAt(i);
         if(!children.containsKey(c)){
            return false;
         }
         if(i== word.length()-1){
            Trie trie= children.get(c);
            if(trie.getIsWord()>0){
               return true;
            }
         }
         children = children.get(c).getChildren();
      }
      return false;
   }

   public void deleteWord(String word){
       Trie root= this;
       if(Boolean.FALSE.equals(this.search(word) )){
           return;
       }
       Map<Character,Trie> children= root.children;
       Map<Character,Integer> characterCountMap= root.characterCountMap;
       for(int i=0;i<word.length();i++){
           Character c= word.charAt(i);
           if(!children.containsKey(c) || !characterCountMap.containsKey(c)){
               return;
           }
           characterCountMap.put(c,characterCountMap.get(c)-1);
           if(i== word.length()-1 ){
               Trie trie= children.get(c);
               trie.setIsWord(trie.getIsWord()-1);
           }
           Map<Character,Trie> temp= children;
           Map<Character,Integer> tempCount= characterCountMap;
           characterCountMap = children.get(c).getCharacterCountMap();
           children = children.get(c).getChildren();
           if(tempCount.get(c)==0){
               tempCount.remove(c);
               temp.remove(c);
           }
       }
   }

   public List<String> generatePossibleWords(String prefix){
     List<String> possibleStrings= new ArrayList<>();
       Trie root= this;
       Map<Character,Trie> children= root.children;
       for(int i=0;i<prefix.length();i++){
           Character c= prefix.charAt(i);
           if(!children.containsKey(c)){
               return null;
           }
           if(i== prefix.length()-1){
               Trie trie= children.get(c);
               root = trie;
           }
           children = children.get(c).getChildren();
       }
     populateStrings(root,possibleStrings,prefix);
     return  possibleStrings;
   }

   static void populateStrings(Trie root,List<String> solution,String s){
     if(root.getIsWord()>0){
       solution.add(new String(s));
     }
     for(Character c : root.getChildren().keySet()){
        populateStrings(root.getChildren().get(c),solution,new String(s+c ));
     }
   }
}
