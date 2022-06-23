package pojos.requests;

import enums.SearchField;
import enums.SearchType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {
    private SearchType searchType;
    private SearchField searchField;
    private String parameterValue;
}
