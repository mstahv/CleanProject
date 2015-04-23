package CleanProject.entities;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity(name = Asset.ENTITY_NAME)
public class Asset {

	public static final String ENTITY_NAME = "Asset";
	@Id
	@Column(name = Asset.ENTITY_NAME + "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Basic(optional = false)
	@Column(unique = true)
	private String name;
	@Basic(optional = false)
	@Column(unique = true)
	private String path;
	@Basic(optional = true)
	private long fileSize;
	@Basic(optional = true)
	@Lob
	private String description;
	@Enumerated(EnumType.STRING)
	@Basic(optional = false)
	private Status status;
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = AssetType.class, optional = true)
	private AssetType assetType;
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = AssetStorage.class, optional = true)
	@JoinColumn(name = "ASSETSTORAGE_ID_FK", referencedColumnName = "AssetStorageID")
	private AssetStorage assetStorage;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	/*@JoinTable(name = Asset.ENTITY_NAME + "_" + Tag.ENTITY_NAME, joinColumns = @JoinColumn(name = Asset.ENTITY_NAME
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
		final Asset other = (Asset) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AssetStorage getAssetStorage() {
		return assetStorage;
	}

	public void setAssetStorage(AssetStorage assetStorage) {
		this.assetStorage = assetStorage;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public AssetType getAssetType() {
		return assetType;
	}

	public void setAssetType(AssetType assetType) {
		this.assetType = assetType;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

    public String toString()
    {
        return name + " " + (description != null ? description : "");
    }

	// Tags
	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Asset withTags(Set<Tag> tags) {
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

	public static enum Status {
		ENABLED(1), DISABLED(0), ARCHIVED(2), DELETED(3);

		private int status;

		private Status(int status) {
			this.status = status;
		}

		@Override
		public String toString() {
			return "" + status;
		}
	}





}