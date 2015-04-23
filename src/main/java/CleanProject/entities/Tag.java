package CleanProject.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = Tag.ENTITY_NAME)
public class Tag {
	


	public static final String ENTITY_NAME = "Tag";
	
	/**
	 * 
	 */
	@Id
	@Column(name = Tag.ENTITY_NAME + "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	/**
     * 
     */
	@Basic(optional = false)
	@Column(unique = true)
    private String name;



    /**
     * 
     */
    //@ManyToMany(mappedBy = "tags", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @ManyToMany(mappedBy = "tags", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    /*@JoinTable(name = Tag.ENTITY_NAME + "_" + AssetStorage.ENTITY_NAME, joinColumns = @JoinColumn(name = Tag.ENTITY_NAME
	+ "ID"), inverseJoinColumns = @JoinColumn(name = AssetStorage.ENTITY_NAME + "ID"))*/
    private Set<AssetStorage> assetStorages = new HashSet<AssetStorage>();
    
    /**
     * 
     */
    
    //@ManyToMany(mappedBy = "tags", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @ManyToMany(mappedBy = "tags", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    /*@JoinTable(name = Tag.ENTITY_NAME + "_" + Asset.ENTITY_NAME, joinColumns = @JoinColumn(name = Tag.ENTITY_NAME
	+ "ID"), inverseJoinColumns = @JoinColumn(name = Asset.ENTITY_NAME + "ID"))*/
    private Set<Asset> assets = new HashSet<Asset>();
    
    /**
     * 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tag other = (Tag) obj;
        if (this.id == other.id || this.name.equals(other.name)) {
        	return true;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
    	return name.hashCode();
    }
    
    /**
	 * 
	 * @return
	 */
    public long getId() {
        return id;
    }
    
    /**
     * 
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }
    
    /**
	 * 
	 * @return
	 */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @param
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 
     * @param
     */
    public Tag withName(String name) {
        setName(name);
        return this;
    }


    public String toString()
    {
        return name;
    }

    public Set<AssetStorage> getAssetStorages() {
        return assetStorages;
    }

    public void setAssetStorages(Set<AssetStorage> assetStorages) {
        this.assetStorages = assetStorages;
    }

    public Set<Asset> getAssets() {
        return assets;
    }

    public void setAssets(Set<Asset> assets) {
        this.assets = assets;
    }

}
