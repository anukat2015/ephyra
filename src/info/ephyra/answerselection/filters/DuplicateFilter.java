package info.ephyra.answerselection.filters;

import info.ephyra.search.Result;
import info.ephyra.util.StringUtils;

import java.util.ArrayList;

/**
 * <p>The <code>DuplicateFilter</code> drops duplicate results. Results are
 * considered equal if <code>StringUtils.equalsCommonNorm()</code> is true for
 * the answer strings. In this case the result with the higher score is kept
 * and its score is incremented by the score of the other result.</p>
 * 
 * <p>This class extends the class <code>Filter</code>.</p>
 * 
 * @author Nico Schlaefer
 * @version 2006-06-28
 */
public class DuplicateFilter extends Filter {
	/**
	 * Results with an answer string equal to one of these forbidden strings are
	 * dropped.
	 */
	private String[] forbidden = new String[0];
	
	/**
	 * Adds forbidden answer strings.
	 * 
	 * @param forbidden forbidden answers
	 */
	public void addForbiddenAnswers(String[] forbidden) {
		this.forbidden = forbidden;
	}
	
	/** in case the String is a url, only return the last part (after the last occurrence of "/" and "#") */
	String urlSuffix(String s)
	{
		if(s==null) {return null;}
		return s.substring(Math.max(s.lastIndexOf('/'), s.lastIndexOf('/')));
	}
	/**
	 * Filters duplicate results and increments the scores of the remaining
	 * results by the scores of the dropped results.
	 * 
	 * @param results array of <code>Result</code> objects
	 * @return array of <code>Result</code> objects without duplicates
	 */
	public Result[] apply(Result[] results) {
		// sort results by their scores in descending order
		results = (new ScoreSorterFilter()).apply(results);
		
		// drop results with forbidden answer strings
		for (String as : forbidden)
			for (int i = 0; i < results.length; i++) {
				if (results[i] == null ||
					results[i].getScore() == Float.POSITIVE_INFINITY) continue;
				if (results[i].getScore() == Float.NEGATIVE_INFINITY) break;
				
				if (StringUtils.equalsCommonNorm(urlSuffix(as), urlSuffix(results[i].getAnswer())))
					results[i] = null;
			}
		
		// drop duplicates
		for (int i = 0; i < results.length - 1; i++) {
			if (results[i] == null ||
				results[i].getScore() == Float.POSITIVE_INFINITY) continue;
			if (results[i].getScore() == Float.NEGATIVE_INFINITY) break;
			
			for (int j = i + 1; j < results.length; j++) {
				if (results[j] == null ||
					results[j].getScore() == Float.POSITIVE_INFINITY) continue;
				if (results[j].getScore() == Float.NEGATIVE_INFINITY) break;
				
				if (StringUtils.equalsCommonNorm(urlSuffix(results[i].getAnswer()), // if answer is a url, only get the last part
						urlSuffix(results[j].getAnswer()))) {
					// increment score of higher-scored result
					results[i].incScore(results[j].getScore());
					// drop lower-scored result
					results[j] = null;
				}
			}
		}
		
		// return remaining results
		ArrayList<Result> noDups = new ArrayList<Result>();
		for (Result result : results)
			if (result != null) noDups.add(result);
		
		return noDups.toArray(new Result[noDups.size()]);
	}
}
