package CleanProject.autoCompleteComboBox;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

/**
 * Created by Arthur on 20.04.2015.
 */
public class GenericAccessServiceImpl<T> implements GenericAccessService<T> {

    protected Class<T> entityClass;

    public static final String PERSISTENCE_UNIT = "JPAContainer";

    private static EntityManager em;


    public GenericAccessServiceImpl(final Class<T> clazz,EntityManager entityManager) {
        entityClass = clazz;
        em=entityManager;
    }

    @Override
    public List<T> searchInDataBase(String filterPrefix) {
        if ("".equals(filterPrefix) || filterPrefix == null) {
            return Collections.emptyList();
        }
        //Search in the db for the string
        StringBuilder queryString = new StringBuilder("SELECT u from ");
        queryString.append(entityClass.getSimpleName());
        queryString.append(" u WHERE u.name LIKE :filter");
        TypedQuery<T> assetsQuery = em.createQuery(queryString.toString(), entityClass);
        assetsQuery.setParameter("filter", "%"+filterPrefix+"%");
        assetsQuery.setMaxResults(30);

        List<T> result = assetsQuery.getResultList();
        return result;

    }


}
