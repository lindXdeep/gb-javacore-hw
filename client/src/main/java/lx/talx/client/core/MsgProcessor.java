package lx.talx.client.core;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import lx.talx.client.service.IMessageProcessor;
import lx.talx.client.utils.Log;

public class MsgProcessor implements IMessageProcessor {

  // regex pattern recipient user
  private Pattern pUser = Pattern.compile("^@[a-zA-Z]{0,64}\\s");
  private Pattern pMsg = Pattern.compile("\\s.{0,4096}");
  private Matcher m;

  @Override
  public void processMessage(String recive) {

    if (recive.matches("^@[a-zA-Z]{3,64}\\s.{0,4096}")) {
      writeFromSender(recive);
    }
  }

  @Override
  public void processCommand(String recive) {

    if (recive.startsWith("/online")) {
      showOnline(recive.substring(8));
    } else if (recive.startsWith("/status")) {
      System.out.print("\n" + recive.substring(7) + "\n\n::>");
    } else if (recive.startsWith("/ping")) {
      // ignore
    }else if (recive.startsWith("/whoami")) {
      JSONObject juser = (JSONObject) JSONValue.parse(recive.substring(7));
      System.out.println("\n  ID:       " + juser.get("id"));
      System.out.println("  Email:    " + juser.get("email"));
      System.out.println("  Username: " + "@" + juser.get("username"));
      System.out.println("  Nickname: " + juser.get("nickname"));
      System.out.print("\n::>");
    }
  }

  private void showOnline(String jsonArr) {

    JSONParser p = new JSONParser();

    System.out.println("\n\nUsers:");

    try {
      JSONArray a = (JSONArray) p.parse(jsonArr);

      for (int i = 0; i < a.size(); i++) {
        System.out.println("    @" + a.get(i) + " - online!");
      }

    } catch (ParseException e) {
      e.printStackTrace();
    }

    System.out.print("\n::>");
  }
}
