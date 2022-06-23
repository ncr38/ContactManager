package service;

import enums.SearchField;
import enums.SearchType;
import lombok.NoArgsConstructor;
import pojos.ContactDetails;
import pojos.Trie;
import pojos.requests.SearchRequest;
import repository.ContactRepository;

import java.util.*;

@NoArgsConstructor
public class SearchService {
 private ContactRepository contactRepository= ContactRepository.getInstance();

 public List<ContactDetails> searchContactDetails(SearchRequest searchRequest){
   List<String> searchValues= new ArrayList<>(Collections.singletonList(searchRequest.getParameterValue()));
   List<ContactDetails> result= new ArrayList<>();
   if(searchRequest.getSearchType()==SearchType.PARTIAL){
       Map<SearchField, Trie> partialSearchTrieMap= contactRepository.getPartialSearchTrieMap();
       Trie propertyTrie= partialSearchTrieMap.get(searchRequest.getSearchField());
       List<String> possibleSearchValues= propertyTrie.generatePossibleWords(searchRequest.getParameterValue());
       searchValues.addAll(possibleSearchValues);
   }
   Map<String,List<Long>> searchFeildValueToContactDetailsMap= contactRepository.getSearchFieldToContactDetailsIdMap()
           .get(searchRequest.getSearchField());
   for(String searchValue : searchValues){
    List<Long> contactDetailsIdList=searchFeildValueToContactDetailsMap.getOrDefault(searchValue,new ArrayList<>());
    for(Long id: contactDetailsIdList){
        result.add(contactRepository.getPrimaryKeyMap().get(id));
    }
   }
   return result;
 }

}
