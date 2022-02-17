/**
	処理内容:	入力チェックを行うクラス
			
	作成者:大野賢一朗 作成日:2022/02/10(木)
*/
package bulletinBoard;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class InputCheck {
	 
	  /**
	  * 指定した正規表現で文字列のチェックを実施するためのメソッド。
	  * @param target 検査対象文字列
	  * @return result 検査対象が正規表現にマッチする場合はtrue、それ以外はfalse
	  */
	  public boolean checkLogic(String target) {
		  String regex_AlphaNum = "^[A-Za-z0-9]+$" ;		// 半角英数字のみ 
		  boolean result = true;
		  if( target == null || target.isEmpty() ) return false ;
		  // 引数に指定した正規表現がtargetにマッチするか確認する
		  Pattern pat = Pattern.compile(regex_AlphaNum);	// 正規表現パターンの読み込み
		  Matcher mat = pat.matcher(target);				// パターンと検査対象文字列の照合
		  result = mat.matches();							// 照合結果をtrueかfalseで取得
		  return result;
	  }
	    
	  /**
	  * 指定した正規表現でメールアドレスのチェックを実施するためのメソッド。
	  * @param target 検査対象文字列
	  * @return result 検査対象が正規表現にマッチする場合はtrue、それ以外はfalse
	  */
	  public boolean isCheckEmail(String target) {
		  String pattern = "^[\\w!#%&'/=~`\\*\\+\\?\\{\\}\\^\\$\\-\\|]+(\\.[\\w!#%&'/=~`\\*\\+\\?\\{\\}\\^\\$\\-\\|]+)*@[\\w!#%&'/=~`\\*\\+\\?\\{\\}\\^\\$\\-\\|]+(\\.[\\w!#%&'/=~`\\*\\+\\?\\{\\}\\^\\$\\-\\|]+)*$";
		  Pattern p = Pattern.compile(pattern);
		  return p.matcher(target).find();
	  }
	  
}
