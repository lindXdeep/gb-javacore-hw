package lx.talx.client.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import lx.talx.client.service.IMessageProcessor;
import lx.talx.client.utils.Log;
import lx.talx.client.utils.Util;

public class MsgProcessor implements IMessageProcessor {

  private Path root = Paths.get("");
  private Path sptr = Paths.get(File.separator);
  private Path db = Paths.get("db");
  private Path db_root = Paths.get(new String(db.toString() + sptr));

  FileOutputStream fout;

  public MsgProcessor() {

    if (!checkDbDir()) {
      Log.log("Unable to create database for messages");
    }
  }

  @Override
  public void process(final byte[] recive) {

    System.out.println("+++++++++++");

    byte[][] msg = parseMessage(recive);

    System.out.println(Util.byteToStr(msg[0]));
    System.out.println(Util.byteToStr(msg[1]));
    System.out.println(Util.byteToStr(msg[2]));
    System.out.println(Util.byteToStr(msg[3]));


    if (Util.byteToStr(msg[2]).equals("/user")) {

      Path user = Paths.get(db_root.toString() + sptr + msg[2]);
      
      try {
        Files.createFile(user);
      } catch (IOException e) {
        e.printStackTrace();
      }


    }

   // writeToDataBase(parseMessage(recive));

  }

  private byte[][] parseMessage(byte[] recive) {

    int lengthMsg = Util.byteToInt(Arrays.copyOfRange(recive, 128, 132));

    return new byte[][] {
        // sender
        Arrays.copyOfRange(recive, 0, 64),
        // recipient
        Arrays.copyOfRange(recive, 64, 128),
        // command
        Arrays.copyOfRange(recive, 128, 148),
        // message
        Arrays.copyOfRange(recive, 152, lengthMsg + 152) };
  }

  private void writeToDataBase(byte[][] msg) {

    Path user = Paths.get(db_root.toString() + sptr + msg[0]);

  }

  private void write(String message) {
    try {
      fout.write(message.getBytes());
      fout.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private boolean checkDbDir() {
    if (Files.isReadable(root) && Files.isWritable(root)) {
      if (!Files.exists(db, LinkOption.NOFOLLOW_LINKS)
          || (Files.exists(db, LinkOption.NOFOLLOW_LINKS) && !Files.isDirectory(db, LinkOption.NOFOLLOW_LINKS))) {
        try {
          Files.createDirectory(db);
          return true;
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      return true;
    }

    Log.log("No permission to write in the current program directory");
    System.exit(0);

    return false;
  }
}
