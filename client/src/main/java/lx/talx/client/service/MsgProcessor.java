package lx.talx.client.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import lx.talx.client.utils.Log;

public class MsgProcessor implements IMessageProcessor {

  Path root = Paths.get("");
  Path sptr = Paths.get(File.separator);
  Path db = Paths.get("db");
  Path db_root = Paths.get(new String(db.toString() + sptr));

  Path testuser = Paths.get(db_root.toString() + sptr + "@user");

  FileOutputStream fout;

  public MsgProcessor() {

    if (checkDbDir()) {

      if (!Files.exists(testuser, LinkOption.NOFOLLOW_LINKS)) {
        try {
          Files.createFile(testuser);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      try {
        fout = new FileOutputStream(testuser.toString(), true);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }

    }
  }

  @Override
  public void process(String message) {
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

    Log.info("No permission to write in the current program directory");
    System.exit(0);

    return false;
  }
}
