/*
	処理内容:	カテゴリー用のデータを保持するクラスのスーパークラス
			
	作成者:大野賢一朗 作成日:2022/02/10(木)
*/
package bulletinBoard;

import java.util.Objects;

public abstract class CategoryInfo {
	private int categoryId;
	private String categoryName;
	private String categoryKana;
	
	public CategoryInfo(int categoryId, String categoryName, String categoryKana) {
		this.categoryId = categoryId;
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

	@Override
	public int hashCode() {
		return Objects.hash(categoryId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoryInfo other = (CategoryInfo) obj;
		return categoryId == other.categoryId;
	}

}
