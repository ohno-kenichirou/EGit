/*
	処理内容:	カテゴリー用のデータを保持するクラスのスーパークラス
			
	作成者:大野賢一朗 作成日:2022/02/10(木)
*/
package bulletinBoard;

public abstract class CategoryInfo {
	private int categoryId;
	private String categoryName;
	private String categoryKana;
	
	public CategoryInfo(int categoryId, String categoryName, String categoryKana) {
		this.categoryName = categoryName;
		this.categoryKana = categoryKana;
	}
	
	public CategoryInfo(String categoryName, String categoryKana) {
		this(0, categoryName, categoryKana);
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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
