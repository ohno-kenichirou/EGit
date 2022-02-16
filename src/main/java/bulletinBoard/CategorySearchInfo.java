package bulletinBoard;

public class CategorySearchInfo {

	private String searchWord;
	private String match;
	
	public CategorySearchInfo(String searchWord, String match) {
		this.searchWord = searchWord;
		this.match = match;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public String getMatch() {
		return match;
	}
	
}
