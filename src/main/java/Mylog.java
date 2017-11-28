
/**
 * @author linxixin@cvte.com
 * @version 1.0
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Mylog {
    public static List<String> cacheMessage = new Vector<>();

    public static void printt() {
        print(null);
    }

    public static void printt(Object objects1) {
        print(objects1);
    }

    public static void printt(Object objects1, Object objects2) {
        print(objects1, objects2);
    }

    public static void printt(Object objects1, Object objects2, Object objects3) {
        print(objects1, objects2, objects3);
    }

    public static void printt(Object objects1, Object objects2, Object objects3, Object object4) {
        print(objects1, objects2, objects3, object4);
    }

    public static void printt(Object objects1, Object objects2, Object objects3, Object object4, Object object5) {
        print(objects1, objects2, objects3, object4, object5);
    }

    public static void printt(Object objects1, Object objects2, Object objects3, Object object4, Object object5, Object object6) {
        print(objects1, objects2, objects3, object4, object5, object6);
    }


    public static void printt(Object objects1, Object objects2, Object objects3, Object object4, Object object5, Object object6, Object object7) {
        print(objects1, objects2, objects3, object4, object5, object6, object7);
    }

    public static void printt(Object objects1, Object objects2, Object objects3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        print(objects1, objects2, objects3, object4, object5, object6, object7, object8);
    }

    public static void printt(Object objects1, Object objects2, Object objects3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        print(objects1, objects2, objects3, object4, object5, object6, object7, object8, object9);
    }

    public static void printt(Object objects1, Object objects2, Object objects3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        print(objects1, objects2, objects3, object4, object5, object6, object7, object8, object9, object10);
    }

    public static void printt(Object objects1, Object objects2, Object objects3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11) {
        print(objects1, objects2, objects3, object4, object5, object6, object7, object8, object9, object10, object11);
    }

    public static void printt(Object objects1, Object objects2, Object objects3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11, Object object12) {
        print(objects1, objects2, objects3, object4, object5, object6, object7, object8, object9, object10, object11, object12);
    }

    public static void printt(Object objects1, Object objects2, Object objects3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11, Object object12, Object object13) {
        print(objects1, objects2, objects3, object4, object5, object6, object7, object8, object9, object10, object11, object12, object13);
    }

    private static File localFile = new File("E:\\mylog.txt");
    private static FileWriter localFileWriter;
    private static int                         deep                = 0;
//    private static long                        startTime           = System.currentTimeMillis() + 1000 * 60;
    private static LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue(1000000);

    static {
        try {
            localFileWriter = new FileWriter(localFile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            String poll;
            while (true) {
                try {
                    poll = linkedBlockingQueue.poll(1, TimeUnit.DAYS);
                    localFileWriter.append(poll);
                    localFileWriter.flush();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();


    }

    private static void print(Object... objects) {

        synchronized (linkedBlockingQueue) {
            if (deep >= 1) {
                return;
            }
            deep = 1;
            try {
                StringBuffer params = new StringBuffer("args: ");
                //method args
                if (objects != null && objects.length > 0) {
                    for (int i = 1; i < objects.length; i++) {
                        Object item = objects[i];
                        if (item != null) {
                            String val = "!error!";
                            try {
                                val = item.toString();
                            } catch (Exception e) {

                            }
                            params.append(i).append(":  ").append(val).append("   ");
                        } else {
                            params.append(i).append(":  null");
                        }
                    }
                }

                try {
                    final Thread thread = Thread.currentThread();
                    StringBuffer msg = new StringBuffer();
                    msg.append(LocalDateTime.now());
                    msg.append(" ");
                    msg.append(thread.getName());
                    msg.append(" ");
                    msg.append(thread.getStackTrace().length);
                    msg.append(" ");
                    msg.append(objects[0]);
                    msg.append("   ");
                    msg.append(params);
                    msg.append("\n");
                    msg.append("  "+thread.getStackTrace()[4].getClassName()+" "+thread.getStackTrace()[4].getMethodName()+"\n");
                    msg.append("  "+thread.getStackTrace()[5].getClassName()+" "+thread.getStackTrace()[5].getMethodName()+"\n");
                    msg.append("  "+thread.getStackTrace()[6].getClassName()+" "+thread.getStackTrace()[6].getMethodName()+"\n");
                    linkedBlockingQueue.put(msg.toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                deep = 0;
            }
        }
    }
}
