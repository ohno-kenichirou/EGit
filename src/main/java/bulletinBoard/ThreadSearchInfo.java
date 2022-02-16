package bulletinBoard;

public class ThreadSearchInfo {

	private String searchWord;
	private String match;
	private int categoryId;
	
	public ThreadSearchInfo(String searchWord, String match, int categoryId) {
		this.searchWord = searchWord;		
		this.match = match;
		this.categoryId = categoryId;
	}
	
	public String getSearchWord() {
		return searchWord;
	}
	public String getMatch() {
		return match;
	}
	public int getCategoryId() {
		return categoryId;
	}
	
	
	
}
