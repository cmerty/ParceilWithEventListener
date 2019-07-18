package parceil.model.RegistrationPurchase;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Getter
@Setter
public class Category {

    private String categoryName;

    private List<SubCategory> subCategoryList;

    public void addSubCategory(SubCategory subCategory) {
        if (isNull(subCategoryList))
            subCategoryList = new ArrayList<>();
        subCategoryList.add(subCategory);
    }
}
