package info.ephyra.answerselection.filters;

import info.ephyra.search.Result;
import info.ephyra.util.StringUtils;

import java.util.ArrayList;

/**  * Removes all answers that are not DBpedia resources or wikipedia articles that get mapped to resources. So it will drop Categories and other namespaces. */
public class WikipediaResourceFilter extends Filter {
	
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
	
		for (int i = 0; i < results.length; i++)
		{
			String answer = results[i].getAnswer();			
			if((!(answer.contains("dbpedia")||answer.contains("wikipedia")))||
				answer.contains("Category")||answer.substring("http:".length()).contains(":"))
			{results[i]=null;}					
		}
		
		// return remaining results
		ArrayList<Result> noDups = new ArrayList<Result>();
		for (Result result : results)
			if (result != null) noDups.add(result);
		
		return noDups.toArray(new Result[noDups.size()]);
	}
}
