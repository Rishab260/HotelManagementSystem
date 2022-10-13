import java.util.Properties;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
public class SentimentAnalyzer {
    static StanfordCoreNLP pipeline;
    public static void init(String[] analyzerProperties)
    {
        
        Properties properties = new Properties();
        String key = analyzerProperties[0];
        String value = analyzerProperties[1];

        properties.setProperty(key,value);
        pipeline = new StanfordCoreNLP(properties);
    }
    public static void predictSentiment(String input)
    {
        int sentimentValue;
        String sentimentName;
        Annotation annotation = pipeline.process(input);
        for(CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class))
        {
            Tree tree = sentence.get(SentimentAnnotatedTree.class);
            sentimentValue = RNNCoreAnnotations.getPredictedClass(tree);
            sentimentName = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            System.out.println(sentimentName + "\t|" + sentimentValue + "\t|" + sentence);
        }
    }
}

