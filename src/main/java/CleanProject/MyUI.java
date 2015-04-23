package CleanProject;

import CleanProject.autoCompleteComboBox.GenericAccessServiceImpl;
import CleanProject.autoCompleteComboBox.SuggestingComboBox;
import CleanProject.autoCompleteComboBox.SuggestingContainer;
import CleanProject.entities.Asset;
import CleanProject.entities.AssetStorage;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.annotation.WebServlet;
import org.vaadin.viritin.fields.CaptionGenerator;
import org.vaadin.viritin.fields.LazyComboBox;
import org.vaadin.viritin.fields.MValueChangeEvent;
import org.vaadin.viritin.fields.MValueChangeListener;

/**
 *
 */
@Theme("mytheme")
@Widgetset("CleanProject.MyAppWidgetset")
public class MyUI extends UI {


    public static final String PERSISTENCE_UNIT = "JPAContainer";
    private static EntityManagerFactory factory;
    private static EntityManager em;
    private LazyComboBox<Asset> assetsComboBox;
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

        // Naturally move this to separate class and/or use java 8
        assetsComboBox = new LazyComboBox<>(Asset.class,
                new LazyComboBox.FilterablePagingProvider<Asset>() {

                    @Override
                    public List<Asset> findEntities(int firstRow, String filter) {
                        return assetDatabaseAccessService.searchInDataBase(
                                filter, firstRow);
                    }
                }, new LazyComboBox.FilterableCountProvider() {

                    @Override
                    public int size(String filter) {
                        return (int) assetDatabaseAccessService.countInDataBase(
                                filter);
                    }
                },
                30);
        assetsComboBox.setCaptionGenerator(new CaptionGenerator<Asset>() {

            @Override
            public String getCaption(Asset option) {
                return option.getName();
            }
        });
        
        assetsComboBox.addMValueChangeListener(new MValueChangeListener<Asset>() {

            @Override
            public void valueChange(MValueChangeEvent<Asset> event) {
                Notification.show(event.getValue().toString());
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
