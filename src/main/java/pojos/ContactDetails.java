package pojos;

import enums.SearchField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class ContactDetails {

    private Long Id;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public ContactDetails(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactDetails that = (ContactDetails) o;
        return firstName.equals(that.firstName) && lastName.equals(that.lastName) && phoneNumber.equals(that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, phoneNumber);
    }

    public String getPropertyValue(SearchField searchField) {
        if(SearchField.FIRST_NAME.equals(searchField)){
            return firstName;
        }
        else if( SearchField.LAST_NAME.equals(searchField)){
            return lastName;
        }
        else{
            return phoneNumber;
        }
    }

    public void setPropertyValue(SearchField searchField, String value) {
        if(SearchField.FIRST_NAME.equals(searchField)){
           this.setFirstName(value);
        }
        else if( SearchField.LAST_NAME.equals(searchField)){
            this.setLastName(value);
        }
        else{
           this.setPhoneNumber(value);
        }
    }
}
