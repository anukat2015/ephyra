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
		km.query = new Query("Das offizielle Internet-Portal für Leipziger Bürger");
		Result result = km.doSearch()[0];
		System.out.println(result);
	}

}