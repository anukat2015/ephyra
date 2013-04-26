package info.ephyra.search.searchers;

import info.ephyra.querygeneration.Query;
import info.ephyra.search.Result;

import org.junit.Test;

public class BingNewKMTest
{
	@Test
	public void testDoSearch()
	{
		BingNewKM km = new BingNewKM();
		km.query = new Query("leipzig");
		for(Result result: km.doSearch())
		{
			System.out.println(result.getAnswer());
		}
//		Result result = km.doSearch()[0];
//		System.out.println(result);
	}
}