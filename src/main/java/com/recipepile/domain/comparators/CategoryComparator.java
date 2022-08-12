package com.recipepile.domain.comparators;

import com.recipepile.domain.Category;

import javax.validation.constraints.NotNull;
import java.util.Comparator;

public class CategoryComparator implements Comparator<Category> {

    @Override
    public int compare(@NotNull Category o1, @NotNull Category o2) {
        if (isChildCategory(o1) && isParentCategory(o2)) {
            if (o1.getParentCategory().equals(o2))
                return 1;
            return -1;
        }
        else if(isChildCategory(o2) &&  isParentCategory(o1)) {
            if (o2.getParentCategory().equals(o1))
                return -1;
            return 1;
        }
        //Both are parent or child categories
        return 0;

    }

    private boolean isParentCategory(Category category) {
        return category.getParentCategory() == null;
    }

    private boolean isChildCategory(Category category){
        return category.getParentCategory() != null;
    }
}
