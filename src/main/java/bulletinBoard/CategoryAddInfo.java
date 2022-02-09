package bulletinBoard;

public class CategoryAddInfo {
	private String categoryName;
	private String categoryKana;
	
	public CategoryAddInfo(String categoryName, String categoryKana) {
		this.categoryName = categoryName;
		this.categoryKana = categoryKana;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryKana() {
		return categoryKana;
	}

	public void setCategoryKana(String categoryKana) {
		this.categoryKana = categoryKana;
	}
	
}
