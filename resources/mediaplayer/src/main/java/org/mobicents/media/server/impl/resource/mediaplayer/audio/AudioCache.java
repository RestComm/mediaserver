package org.mobicents.media.server.impl.resource.mediaplayer.audio;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by achikin on 5/9/16.
 */
public interface AudioCache {
    InputStream getStream(URL uri) throws IOException;
}
