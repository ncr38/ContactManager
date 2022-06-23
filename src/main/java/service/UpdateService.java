package service;

import pojos.ContactDetails;
import repository.ContactRepository;
import java.util.Objects;

public class UpdateService {
    private ContactRepository contactRepository = ContactRepository.getInstance();

    public ContactDetails addContact(ContactDetails contactDetails){
        return contactRepository.addContact(contactDetails);
    }

    public ContactDetails updateContact(ContactDetails contactDetails){
        if(Objects.nonNull(contactDetails.getId()))
        return contactRepository.updateContact(contactDetails);

        return null;
    }
}
