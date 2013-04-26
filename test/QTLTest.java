import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.dllearner.algorithms.qtl.QTL;
import org.dllearner.algorithms.qtl.filters.QuestionBasedQueryTreeFilter;
import org.dllearner.core.ComponentInitException;
import org.dllearner.core.ComponentManager;
import org.dllearner.core.LearningProblemUnsupportedException;
import org.dllearner.kb.SparqlEndpointKS;
import org.dllearner.kb.sparql.SparqlEndpoint;
import org.dllearner.learningproblems.PosOnlyLP;
import org.dllearner.utilities.Helper;
import org.junit.Test;

public class QTLTest
{
	@Test
	public void testQTL() throws ComponentInitException, LearningProblemUnsupportedException
	{
		Set<String> positiveExamples = new HashSet<String>();
		positiveExamples.add("http://dbpedia.org/resource/Liverpool_F.C.");
		positiveExamples.add("http://dbpedia.org/resource/Chelsea_F.C.");
		
		ComponentManager cm = ComponentManager.getInstance();
		SparqlEndpointKS ks = new SparqlEndpointKS(SparqlEndpoint.getEndpointDBpedia());
		ks.init();
		PosOnlyLP lp = new PosOnlyLP();	
		cm.getPool().registerComponent(lp);
		lp.setPositiveExamples(Helper.getIndividualSet(positiveExamples));
		QTL qtl = new QTL(lp, ks);
		qtl.addQueryTreeFilter(new QuestionBasedQueryTreeFilter(Arrays.asList(new String[] {"soccer club","premier league"})));
		qtl.init();
		qtl.start();
		String query = qtl.getBestSPARQLQuery();
		System.out.println(query);
	}
}