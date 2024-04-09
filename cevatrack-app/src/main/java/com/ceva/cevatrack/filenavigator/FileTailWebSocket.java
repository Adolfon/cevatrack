package com.ceva.cevatrack.filenavigator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ceva.framework.ejb.service.property.ConfigProperty;

@ServerEndpoint(value = "/logFiles/{filePath}")
public class FileTailWebSocket {

  @Inject
  @ConfigProperty(key = "file.navigator.directories")
  private String fileNavigatorDirectory;

  @Inject
  @ConfigProperty(key = "file.lines.display", defaultValue = "10")
  private static int numberOfLinesToDisplay;

  private static final Logger LOGGER = LoggerFactory.getLogger(FileTailWebSocket.class);

  /**
   * starts the tailing of file.
   */
  @OnOpen
  public void displayFile(@PathParam("filePath") String filePath, Session session) {

    try {
      session.getBasicRemote().sendText("This is session {}" + session.getId());
    } catch (IOException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    getTailLines(Paths.get(filePath.replace("_", File.separator))).forEach(line -> {
      try {
        synchronized (session) {
          session.getBasicRemote().sendText(line);
        }
      } catch (IOException e) {
        LOGGER.error("error while sending last 10 lines of a file");
      }
    });
    FileTailManager.subscribeTailFollow(filePath.replace("_", File.separator), session);
  }

  /**
   * Sends the message to client.
   */
  @OnMessage
  public void onMessage(String message, Session session) {
    LOGGER.debug("message Received from {} {}", session.getId(), message);
  }

  // TODO JUnit Test
  private static final List<String> getTailLines(final Path source) {
    Stream<String> stream = null;
    try {
      stream = Files.lines(source);
    } catch (IOException e) {
      LOGGER.error("error in tailFile");
    }
    {
      FileBuffer fileBuffer = new FileBuffer(numberOfLinesToDisplay);
      stream.forEach(line -> fileBuffer.collect(line));
      return fileBuffer.getLines();
    }
  }

  /**
   * This method is invoked when an error is thrown.
   */
  @OnError
  public void onError(@PathParam("filePath") String filePath, Throwable t) {
    LOGGER.error("error while closing the websocket.");
  }

  /**
   * This method is invoked when the websocket session is closed.
   */
  @OnClose
  public void onClose(@PathParam("filePath") String filePath, Session session) {
    LOGGER.debug("closing the websocket.");
  }

  public String getFileNavigatorDirectory() {
    return fileNavigatorDirectory;
  }

  public void setFileNavigatorDirectory(String fileNavigatorDirectory) {
    this.fileNavigatorDirectory = fileNavigatorDirectory;
  }

  public int getNumberOfLinesToDisplay() {
    return numberOfLinesToDisplay;
  }

  public void setNumberOfLinesToDisplay(int numberOfLinesToDisplay) {
    this.numberOfLinesToDisplay = numberOfLinesToDisplay;
  }

  private static final class FileBuffer {
    private int offset = 0;
    private final int noOfLines;
    private final String[] lines;

    /**
     * Constructor class.
     */
    public FileBuffer(int noOfLines) {
      this.noOfLines = noOfLines;
      this.lines = new String[noOfLines];
    }

    /**
     * Collects the lines of the file.
     */
    public void collect(String line) {
      lines[offset++ % noOfLines] = line;
    }

    /**
     * Collects the specified number of lines of the file.
     */
    public List<String> getLines() {
      return IntStream.range(offset < noOfLines ? 0 : offset - noOfLines, offset)
          .mapToObj(idx -> lines[idx % noOfLines]).collect(Collectors.toList());
    }
  }
}
