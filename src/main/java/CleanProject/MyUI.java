package CleanProject;

import CleanProject.autoCompleteComboBox.GenericAccessServiceImpl;
import CleanProject.autoCompleteComboBox.SuggestingComboBox;
import CleanProject.autoCompleteComboBox.SuggestingContainer;
import CleanProject.entities.Asset;
import CleanProject.entities.AssetStorage;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Property;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.annotation.WebServlet;

/**
 *
 */
@Theme("mytheme")
@Widgetset("CleanProject.MyAppWidgetset")
public class MyUI extends UI {


    public static final String PERSISTENCE_UNIT = "JPAContainer";
    private static EntityManagerFactory factory;
    private static EntityManager em;
    private SuggestingComboBox assetsComboBox;
    private SuggestingComboBox  assetsStorageComboBox;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        em = factory.createEntityManager();
        ensureDemoData();

        //Service for Combobox, needs Enitity Manager
        final GenericAccessServiceImpl<Asset> assetDatabaseAccessService = new GenericAccessServiceImpl(Asset.class,em);
        final GenericAccessServiceImpl<AssetStorage> assetStorageDatabaseAccessService = new GenericAccessServiceImpl(AssetStorage.class,em);

        // Container für Combobox, need DaoService
        final SuggestingContainer<Asset> assetContainer = new SuggestingContainer<Asset>(Asset.class, assetDatabaseAccessService);
        final SuggestingContainer<AssetStorage> assetStorageContainer = new SuggestingContainer<AssetStorage>(AssetStorage.class, assetStorageDatabaseAccessService);



        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);



        assetsComboBox = new SuggestingComboBox();
        assetsComboBox.setContainerDataSource(assetContainer);
        assetsComboBox.setImmediate(true);
        assetsComboBox.setItemCaptionPropertyId("name");
        assetsComboBox.setFilteringMode(FilteringMode.CONTAINS);
        assetsComboBox.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                // tell the custom container that a value has been selected. This is necessary to ensure that the
                // selected value is displayed by the ComboBox
                assetContainer.setSelectedBean(event.getProperty().getValue());
            }
        });



        assetsStorageComboBox = new SuggestingComboBox();
        assetsStorageComboBox.setContainerDataSource(assetStorageContainer);
        assetsStorageComboBox.setFilteringMode(FilteringMode.CONTAINS);
        assetsStorageComboBox.setItemCaptionPropertyId("name");
        assetsStorageComboBox.setImmediate(true);

        Button button = new Button("Zeige Selektierte Objekte");
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                if (assetsComboBox.getValue() != null)
                    layout.addComponent(new Label(((Asset) assetsComboBox.getValue()).getName()));
                if (assetsStorageComboBox.getValue() != null)
                    layout.addComponent(new Label(((AssetStorage) assetsStorageComboBox.getValue()).getName()));
            }
        });







        HorizontalLayout horizontalLayout = new HorizontalLayout();

        horizontalLayout.addComponent(button);
        horizontalLayout.addComponent(assetsComboBox);
        horizontalLayout.addComponent(assetsStorageComboBox);
        layout.addComponent(horizontalLayout);


    }

    protected void ensureDemoData() {
        em.getTransaction().begin();
        Asset asset = new Asset();
        asset.setName("Foo");
        em.persist(asset);
        asset = new Asset();
        asset.setName("Foo2");
        em.persist(asset);
        asset = new Asset();
        asset.setName("Foo3");
        em.persist(asset);
        asset = new Asset();
        asset.setName("Bar");
        em.persist(asset);
        asset = new Asset();
        asset.setName("Car");
        em.persist(asset);
        em.getTransaction().commit();
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
