package bulletinBoard;

public class CategoryNameDisp implements Comparable<CategoryNameDisp>{
	private int categoryId;
	private String categoryName;
	private String categoryKana;

	public CategoryNameDisp(int categoryId, String categoryName, String categoryKana) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.categoryKana = categoryKana;
	}

	public int getCategoryId() {
		return categoryId;
	}
	
	public String getCategoryName() {
		return categoryName;
	}

	public String getCategoryKana() {
		return categoryKana;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + categoryId;
		result = prime * result + ((categoryKana == null) ? 0 : categoryKana.hashCode());
		result = prime * result + ((categoryName == null) ? 0 : categoryName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoryNameDisp other = (CategoryNameDisp) obj;
		if (categoryId != other.categoryId)
			return false;
		if (categoryKana == null) {
			if (other.categoryKana != null)
				return false;
		} else if (!categoryKana.equals(other.categoryKana))
			return false;
		if (categoryName == null) {
			if (other.categoryName != null)
				return false;
		} else if (!categoryName.equals(other.categoryName))
			return false;
		return true;
	}

	@Override
	public int compareTo(CategoryNameDisp c) {
		return this.getCategoryKana().compareTo(c.getCategoryKana());
	}
	
	
	
}
