package CleanProject.entities;

import javax.persistence.*;

@Entity(name = AssetType.ENTITY_NAME)
public class AssetType {

	public static final String ENTITY_NAME = "AssetType";

	
	@Id
	@Column(name = AssetType.ENTITY_NAME + "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Enumerated(EnumType.STRING)
	@Basic(optional = false)
	private Type name;
	
	@Basic(optional = true)
	@Column(unique = true)
	private String iconPath;
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final AssetType other = (AssetType) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	public Type getName() {
		return name;
	}

	public void setName(Type name) {
		this.name = name;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}


	public static enum Type {
		IMAGE(0),
		MOVIE(1),
		FORM(2),
		THREESIXTY(3);

		private int assetType;

		private Type(int assetType) {
			this.assetType = assetType;
		}

		@Override
		public String toString() {
			return "" + assetType;
		}
	}

}