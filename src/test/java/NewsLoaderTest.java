import edu.iis.mto.staticmock.Configuration;
import edu.iis.mto.staticmock.ConfigurationLoader;
import edu.iis.mto.staticmock.IncomingNews;
import edu.iis.mto.staticmock.NewsReaderFactory;
import edu.iis.mto.staticmock.reader.NewsReader;
import org.junit.Before;
import org.mockito.*;
import org.powermock.api.mockito.*;
/**
 * Created by wojciech.pelka on 2016-04-19.
 */
public class NewsLoaderTest
{
    ConfigurationLoader configurationLoader;
    NewsReaderFactory newsReaderFactory;

    @Before
    public void init()
    {
        configurationLoader = Mockito.mock(ConfigurationLoader.class);
        Mockito.when(configurationLoader.loadConfiguration()).thenReturn(new Configuration());
        PowerMockito.mockStatic(ConfigurationLoader.class);
        PowerMockito.when(ConfigurationLoader.getInstance()).thenReturn(configurationLoader);


        NewsReader newsReader = Mockito.mock(NewsReader.class);
        Mockito.when(newsReader.read()).thenReturn(new IncomingNews());

        PowerMockito.mockStatic(NewsReaderFactory.class);
        PowerMockito.when(NewsReaderFactory.getReader(Mockito.anyString())).thenReturn(newsReader);
    }


}
