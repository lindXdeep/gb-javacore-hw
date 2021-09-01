package lx.talx.server.utils;

import java.net.Socket;
import java.util.Arrays;

public class Util {

  private static final String ver = "ver-0.1";

  private static StringBuilder sb;

  public static String getAddress(Socket socket) {

    sb = new StringBuilder();

    sb.append(getIp(socket));
    sb.append("--> [PORT:");
    sb.append(socket.getPort());
    sb.append("] ");

    return sb.toString();
  }

  public static String getIp(Socket socket) {

    sb = new StringBuilder();
    sb.append(" [IP:");
    sb.append(socket.getLocalAddress());
    sb.append("] ");

    sb.deleteCharAt(5);

    return sb.toString();
  }

  public static int getFreePort() {

    int min = 49152;
    int max = 65535;

    return (int) (Math.random() * (max - min) + min);
  }

  public static String getLogo() {
    return Arrays.toString(new String[] {

        "\n\t            .                 ,         \n",

        "\t   .    , _ | _. _ ._ _  _   -+- _       \n",

        "\t    \\/\\/ (/,|(_.(_)[ | )(/,   | (_)    \n",

        "\t                                         \n",

        "\t _______ _______        _     _ _     _  \n",

        "\t    |    |_____| |      |____/   \\___/  \n",

        "\t    |    |     | |_____ |    \\_ _/   \\_",

        " > messenger < \n\n", "\t\t\t".concat(Util.getVer()).concat("\n\n") });
  }

  public static String getInstruction() {
    return "\n\n Use command \"/help\" for more information about a command.\n";
  }

  public static String getVer() {
    return ver;
  }

  public static String cursor(Socket socket) {
    return "\n[".concat(socket.getInetAddress().toString().substring(1)).concat("]> ");
  }
}
