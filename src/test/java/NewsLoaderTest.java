import edu.iis.mto.staticmock.*;
import edu.iis.mto.staticmock.reader.NewsReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by wojciech.pelka on 2016-04-19.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ ConfigurationLoader.class, NewsReaderFactory.class })
public class NewsLoaderTest
{
    ConfigurationLoader configurationLoader;
    NewsReader newsReader;
    NewsLoader newsLoader;
    IncomingNews incomingNews;


    @Before
    public void init()
    {
        newsLoader = new NewsLoader();
        incomingNews = new IncomingNews();
        incomingNews.add(new IncomingInfo("SubA",SubsciptionType.A));
        incomingNews.add(new IncomingInfo("NoneSub1",SubsciptionType.NONE));
        incomingNews.add(new IncomingInfo("NoneSub2",SubsciptionType.NONE));

        configurationLoader = Mockito.mock(ConfigurationLoader.class);
        Mockito.when(configurationLoader.loadConfiguration()).thenReturn(new Configuration());
        PowerMockito.mockStatic(ConfigurationLoader.class);
        PowerMockito.when(ConfigurationLoader.getInstance()).thenReturn(configurationLoader);

        newsReader = Mockito.mock(NewsReader.class);
        Mockito.when(newsReader.read()).thenReturn(new IncomingNews());

        PowerMockito.mockStatic(NewsReaderFactory.class);
        PowerMockito.when(NewsReaderFactory.getReader(Mockito.anyString())).thenReturn(newsReader);
    }

    @Test
    public void PublicContentTest()
    {
        Mockito.when(newsReader.read()).thenReturn(incomingNews);

        PublishableNews pn = newsLoader.loadNews();

        assertThat(pn.getPublicContent().size(),is(2));
    }

    @Test
    public void SubContentTest()
    {
        Mockito.when(newsReader.read()).thenReturn(incomingNews);

        PublishableNews pn = newsLoader.loadNews();

        assertThat(pn.getSubscribentContent().size(),is(1));
    }

    @Test
    public void BehaviourTest()
    {
        newsLoader.loadNews();
        Mockito.verify(configurationLoader,Mockito.times(1)).loadConfiguration();
        Mockito.verify(newsReader,Mockito.times(1)).read();
    }

}
