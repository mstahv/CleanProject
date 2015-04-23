package CleanProject.autoCompleteComboBox;

import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.ComboBox;

/**
 * Created by Arthur on 20.04.2015.
 */
public class SuggestingComboBox extends ComboBox {

    public SuggestingComboBox(String caption) {
        super(caption);
        // the item caption mode has to be PROPERTY for the filtering to work
        setItemCaptionMode(ItemCaptionMode.PROPERTY);

        // define the property name of the Bean to use as item caption
        setItemCaptionPropertyId("name");
    }

    public SuggestingComboBox() {
        this(null);
    }

    /**
     * Overwrite the protected method
     * {ComboBox#buildFilter(String, FilteringMode)} to return a custom
     * {SuggestionFilter} which is only needed to pass the given
     * filterString on to the {@link SuggestingContainer}.
     */
    @Override
    protected Filter buildFilter(String filterString, FilteringMode filteringMode) {
        return new SuggestingContainer.SuggestionFilter(filterString);
    }


}
