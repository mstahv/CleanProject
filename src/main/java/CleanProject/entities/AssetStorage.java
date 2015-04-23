package CleanProject.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


//@Cacheable
@Entity(name = AssetStorage.ENTITY_NAME)
public class AssetStorage {

	
	public static final String ENTITY_NAME = "AssetStorage";
	
	public static final String QUERY_FIND_ASSETS = ENTITY_NAME + ".findAssets";
	
	public static final String QUERY_FIND_SUBSTORAGES = ENTITY_NAME + ".findSubStorages";
	
	public static enum Status {
		ENABLED(1),
		DISABLED(0),
		ARCHIVED(2),
		DELETED(3);

		private int status;

		private Status(int status) {
			this.status = status;
		}

		@Override
		public String toString() {
			return "" + status;
		}
	}
	
	@Id
	@Column(name = AssetStorage.ENTITY_NAME + "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)

	private long id;

	@Basic(optional = false)
	@Size(min = 1, max = 50, message = "Der Ordnername darf maximal 50 Zeichen enthalten.")
	@NotNull(message = "Bitte geben Sie einen Ordnernamen ein.")
	@Column(unique = true)
	private String name;

	@Basic(optional = true)
	@Column(unique = true)
	private String driver;

	@Basic(optional = true)
	@Column(unique = true)
	private String path = "";


	@ManyToOne(fetch = FetchType.EAGER, targetEntity = AssetStorage.class, optional = true)
	private AssetStorage parent;

	//@OneToMany(mappedBy = "assetStorage", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	//private Set<Asset> assets;

	@Enumerated(EnumType.STRING)
	@Basic(optional = false)
	private Status status = Status.ENABLED;
	
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	/*@JoinTable(name = AssetStorage.ENTITY_NAME + "_" + Tag.ENTITY_NAME, joinColumns = @JoinColumn(name = AssetStorage.ENTITY_NAME
	+ "ID"), inverseJoinColumns = @JoinColumn(name = Tag.ENTITY_NAME + "ID"))*/
	private Set<Tag> tags = new HashSet<Tag>();
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final AssetStorage other = (AssetStorage) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}


	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public AssetStorage getParent() {
		return parent;
	}

	public void setParent(AssetStorage parent) {
		this.parent = parent;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

    public String toString()
    {
        return name;
    }

	// Tags
	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public AssetStorage withTags(Set<Tag> tags) {
		setTags(tags);
		return this;
	}

	public boolean addToTags(Tag tag) {
		boolean success = false;
		if (tag != null && !getTags().contains(tag)) {
			getTags().add(tag);
			success = true;
		}
		return success;
	}

	public boolean removeFromTags(Tag tag) {
		boolean success = false;
		if (tag != null && getTags().contains(tag)) {
			getTags().remove(tag);
			success = true;
		}
		return success;
	}

	

}