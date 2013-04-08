package info.ephyra.search.searchers;

import info.ephyra.search.Result;
import java.util.LinkedList;
import java.util.List;
import net.billylieurance.azuresearch.AbstractAzureSearchQuery.AZURESEARCH_API;
import net.billylieurance.azuresearch.AzureSearchResultSet;
import net.billylieurance.azuresearch.AzureSearchWebQuery;
import net.billylieurance.azuresearch.AzureSearchWebResult;

/**
 * <p>A <code>KnowledgeMiner</code> that deploys the Bing search engine to
 * search the Web.</p>
 * 
 * <p>It runs as a separate thread, so several queries can be performed in
 * parallel.</p>
 * 
 * <p>This class extends the class <code>KnowledgeMiner</code>.</p>
 * 
 * @author James Zhang, Jeff Chen
 * @version 2011-10-05
 */
public class BingNewKM extends KnowledgeMiner {
	/** Bing Application ID. */
//	private static final String BING_APP_ID = "1A128D5664A0CE797F3BB450785221091616619A";
	
	// remove string and uncomment BingNewKM() when moving to a public repository
	private static String BING_APP_ID = "EmiiDlkMTLjnustf0rbg4/dJq9BBPZYrwhnslD6FHL4=";
	
	/** Maximum total number of search results. */
	private static final int MAX_RESULTS_TOTAL = 100;
	/** Maximum number of search results per query. */
	private static final int MAX_RESULTS_PERQUERY = 50;

	@Override
	protected int getMaxResultsTotal() {
		return MAX_RESULTS_TOTAL;
	}

	@Override
	protected int getMaxResultsPerQuery() {
		return MAX_RESULTS_PERQUERY;
	}

	/**
	 * Returns a new instance of <code>BingKM</code>. A new instance is created
	 * for each query.
	 * 
	 * @return new instance of <code>BingKM</code>
	 */
	@Override
	public KnowledgeMiner getCopy() {
		return new BingNewKM();
	}
	
	public BingNewKM()
	{
//		System.out.println(AbstractAzureSearchQuery.AZURESEARCH_PATH);
//		System.out.println(AbstractAzureSearchQuery.AZURESEARCHWEB_PATH);
		
//		AbstractAzureSearchQuery.AZURESEARCH_PATH="/Bing/Search/v1/";
//		AbstractAzureSearchQuery.AZURESEARCHWEB_PATH="/Bing/SearchWeb/v1/";
//		 Properties props = new Properties();
//		 try
//		{
//			props.load(new FileReader("conf/searchapikeys.properties"));
////		
//		
//		} catch (IOException e)
//		{
//			throw new RuntimeException(e);
//		}
	}

	@Override
	protected Result[] doSearch() {
		AzureSearchWebQuery aq = new AzureSearchWebQuery();
		aq.setAppid(BING_APP_ID);
		aq.setQuery(query.getQueryString());
//		aq.setQuery("Oklahoma Sooners");
		aq.setBingApi(AZURESEARCH_API.BINGSEARCHWEBONLY);
		System.out.println(aq.getUrlQuery());
		System.out.println("searching for: "+aq.getQuery());
		
        
		aq.doQuery();
		AzureSearchResultSet<AzureSearchWebResult> ars = aq.getQueryResult();
		System.out.println(ars.getASRs().size());
		List<Result> results = new LinkedList<>();
		for (AzureSearchWebResult anr : ars)
		{
			System.out.println(anr.getTitle());
			Result result = new Result(anr.getUrl(),query);
			results.add(result);
		}
		

		// end new stuff *******************************
		
		// get a search client
//		BingSearchServiceClientFactory factory = BingSearchServiceClientFactory
//				.newInstance();
//		BingSearchClient client = factory.createBingSearchClient();
//
//		// configure search client
//		SearchRequestBuilder builder = client.newSearchRequestBuilder();
//		builder.withAppId(BING_APP_ID);
//		builder.withQuery(query.getQueryString());
//		builder.withSourceType(SourceType.WEB);
//		builder.withVersion("2.0");
//		builder.withMarket("en-us");
//		builder.withAdultOption(AdultOption.MODERATE);
//		builder.withSearchOption(SearchOption.ENABLE_HIGHLIGHTING);
//		builder.withWebRequestCount((long) maxResults);
//		builder.withWebRequestOffset((long) firstResult);
//		builder.withWebRequestSearchOption(WebSearchOption.DISABLE_HOST_COLLAPSING);
//		builder.withWebRequestSearchOption(WebSearchOption.DISABLE_QUERY_ALTERATIONS);
//
//		// do the actual search here, and collect the results
//		SearchResponse response = client.search(builder.getResult());
//		List<WebResult> results = response.getWeb().getResults();
//		ArrayList<String> snippets = new ArrayList<String>();
//		ArrayList<String> urls = new ArrayList<String>();
//		for (WebResult result : results) {
//			snippets.add(result.getDescription());
//			urls.add(result.getUrl());
//		}
//
//		// return results
//		return getResults(Collections.toStringArray(snippets),
//				Collections.toStringArray(urls), true);
		
			return results.toArray(new Result[0]);
	}
}
