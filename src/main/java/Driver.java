import enums.SearchField;
import enums.SearchType;
import pojos.ContactDetails;
import pojos.Trie;
import pojos.requests.SearchRequest;
import service.SearchService;
import service.UpdateService;

import java.util.List;

public class Driver {
    public static void main(String[] args) {
//        UpdateService updateService = new UpdateService();
//        SearchService searchService= new SearchService();
//        ContactDetails contactDetails= new ContactDetails("navin","rai","7073109806");
//        updateService.addContact(contactDetails);
//
//        System.out.print("Add Request Results: ");
//        System.out.println(contactDetails);
//
//        SearchRequest searchRequest2= new SearchRequest();
//        searchRequest2.setSearchField(SearchField.PHONE_NUMBER);
//        searchRequest2.setParameterValue("7073");
//        searchRequest2.setSearchType(SearchType.PARTIAL);
//
//        System.out.print("Search Request 2 Results: ");
//        System.out.println(searchService.searchContactDetails(searchRequest2));
//
//        ContactDetails updatedContactDetails= new ContactDetails(null, "chandra", "9006390449");
//        updatedContactDetails.setId(contactDetails.getId());
//        System.out.println("updatedContactDetails");
//
//        System.out.println(updatedContactDetails);
//        updateService.updateContact(updatedContactDetails);
//
//
//
//
//
//        SearchRequest searchRequest3= new SearchRequest();
//        searchRequest3.setSearchField(SearchField.LAST_NAME);
//        searchRequest3.setParameterValue("chand");
//        searchRequest3.setSearchType(SearchType.PARTIAL);
//
//        System.out.print("Search Request 3 Results: ");
//        System.out.println(searchService.searchContactDetails(searchRequest3));
        Trie trie = new Trie();
        trie.insertWord("chand");
        trie.insertWord("chandini");
       // trie.deleteWord("chandini");
        List<String> list =trie.generatePossibleWords("cha");

        System.out.println(list);

    }
}
