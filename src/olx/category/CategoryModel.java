package olx.category;

/**
 * @author dsumitra
 *
 */
public class CategoryModel {
	private int id;
	private String primaryCategory;
	private String subCategory;

	public CategoryModel(int id, String primaryCategory, String subCategory) {
		super();
		this.id = id;
		this.primaryCategory = primaryCategory;
		this.subCategory = subCategory;
	}

	CategoryModel() {
		super();
	};

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPrimaryCategory() {
		return primaryCategory;
	}

	public void setPrimaryCategory(String primaryCategory) {
		this.primaryCategory = primaryCategory;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	@Override
	public String toString() {
		return "CategoryModel [id=" + id + ", primaryCategory=" + primaryCategory + ", subCategory=" + subCategory
				+ "]";
	}

}
