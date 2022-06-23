package repository;

import enums.SearchField;
import enums.SearchType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pojos.ContactDetails;
import pojos.Trie;

import java.util.*;

@Getter
@Setter
@ToString
public class ContactRepository {

    volatile private static Long counter= 0l;

    private Map<Long,ContactDetails> primaryKeyMap;

    private Map<SearchField,Map<String, List<Long>>> searchFieldToContactDetailsIdMap;

    private Map<SearchField, Trie>  partialSearchTrieMap;

    private static  ContactRepository instance= null;

    public static ContactRepository getInstance(){
        if(instance == null){
            synchronized (ContactRepository.class)
            {
                if(instance==null)
                {
                    instance = new ContactRepository();
                }

            }
        }
        return instance;
    }

    public ContactRepository() {
        this.searchFieldToContactDetailsIdMap= new HashMap<>();
        this.partialSearchTrieMap= new HashMap<>();
        this.primaryKeyMap= new HashMap<>();
        for(SearchField searchField : SearchField.values()){
            partialSearchTrieMap.put(searchField, new Trie());
            searchFieldToContactDetailsIdMap.put(searchField,new HashMap<>());
        }
    }

    public ContactDetails addContact(ContactDetails contactDetails){
        contactDetails.setId(counter++);
        primaryKeyMap.put(contactDetails.getId(),contactDetails);
        for(SearchField searchField: SearchField.values()){
            Map<String,List<Long>> searchFieldValueToContactDetailsIdMap= searchFieldToContactDetailsIdMap.get(searchField);
            List<Long> contactDetailsIdList= searchFieldValueToContactDetailsIdMap
                    .getOrDefault(contactDetails.getPropertyValue(searchField),new ArrayList<>());
            contactDetailsIdList.add(contactDetails.getId());
            searchFieldValueToContactDetailsIdMap.put(contactDetails.getPropertyValue(searchField),contactDetailsIdList);

            Trie searchFieldTrie= partialSearchTrieMap.get(searchField);
            searchFieldTrie.insertWord(contactDetails.getPropertyValue(searchField));
        }
        return contactDetails;
    }

    public ContactDetails updateContact(ContactDetails contactDetails) {
        ContactDetails initialContactDetails= primaryKeyMap.get(contactDetails.getId());

        if(initialContactDetails.equals(contactDetails)){
            return contactDetails;
        }
        for(SearchField searchField: SearchField.values()){
            if(Objects.nonNull(contactDetails.getPropertyValue(searchField)) &&
                    !contactDetails.getPropertyValue(searchField).equals(initialContactDetails.getPropertyValue(searchField))){

                updatePartialSearchTrie(searchField,initialContactDetails.getPropertyValue(searchField),contactDetails.getPropertyValue(searchField));

                Map<String,List<Long>> propertyValueToContactDetailsMap= searchFieldToContactDetailsIdMap.get(searchField);
                List<Long> initialPropertyValueToContactDetailsIdList= propertyValueToContactDetailsMap.get(initialContactDetails.getPropertyValue(searchField));
                initialPropertyValueToContactDetailsIdList.remove(contactDetails.getId());

                List<Long> finalPropertyValueToContactDetailsList = propertyValueToContactDetailsMap.getOrDefault(contactDetails.getPropertyValue(searchField),new ArrayList<>());
                finalPropertyValueToContactDetailsList.add(contactDetails.getId());
                propertyValueToContactDetailsMap.put(contactDetails.getPropertyValue(searchField), finalPropertyValueToContactDetailsList);


                initialContactDetails.setPropertyValue(searchField,contactDetails.getPropertyValue(searchField));
            }
        }

        primaryKeyMap.put(initialContactDetails.getId(),initialContactDetails);
        return initialContactDetails;
    }

    private void updatePartialSearchTrie(SearchField searchField,String initialValue, String finalValue){
        Trie trie= partialSearchTrieMap.get(searchField);
        trie.deleteWord(initialValue);
        trie.insertWord(finalValue);
    }
}
