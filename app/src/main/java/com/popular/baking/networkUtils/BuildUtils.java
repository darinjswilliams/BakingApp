package com.popular.baking.networkUtils;

import com.popular.baking.constants.Constants;
import com.popular.baking.dto.Ingredients;

import java.util.List;

public class BuildUtils {
    public static String menuBuilderForIngridents(List<Ingredients> ingredients) {

        StringBuilder mBuilder = new StringBuilder();

        for (int knt = 0; knt < ingredients.size(); knt++) {
            switch (knt) {
                case 0:
                    mBuilder.append(Constants.INGRIDENTS);
                    mBuilder.append("\n");
                    break;

                default:
                    mBuilder.append("\n");
                    break;
            }

            mBuilder.append(knt + "." + ingredients.get(knt).toString());
        }

        return mBuilder.toString();

    }
}
