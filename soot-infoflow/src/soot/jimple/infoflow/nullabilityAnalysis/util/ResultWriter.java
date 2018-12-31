package soot.jimple.infoflow.nullabilityAnalysis.util;

import soot.SootMethod;
import soot.jimple.Stmt;
import soot.jimple.infoflow.nullabilityAnalysis.Status;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultWriter {

    static String NULLABLE_PARAMS_LIST = "targets/method_params_nullable_list.txt";
    static String NULLABLE_RETURN_LIST = "targets/method_return_nullable_list.txt";
    static String NULLABLE_FIELD_LIST = "targets/fields_list.txt";
    static String NULLABLE_RESULT = "targets/result.txt";
    static String NULLABLE_RESULT_ONLY_NULLABLE = "targets/result_only_nullable.txt";
    static String SOURCE = "targets/sources.txt";
    static String TIME_RESULT = "targets/time.txt";
    static String LOG_TEXT = "targets/logger.txt";
    static String LOG_DEBUG_TEXT = "targets/debug.txt";

    public static List<String> fileNames = new ArrayList<>(Arrays.asList(
            NULLABLE_RETURN_LIST,
            NULLABLE_PARAMS_LIST,
            NULLABLE_FIELD_LIST,
            NULLABLE_RESULT,
            NULLABLE_RESULT_ONLY_NULLABLE,
            SOURCE,
            TIME_RESULT,
            LOG_TEXT,
            LOG_DEBUG_TEXT
    ));

    public static void outPutTime(long startTime, long endTime) {
        try {
            // FileWriterクラスのオブジェクトを生成する
            FileWriter file = new FileWriter(TIME_RESULT, true);
            // PrintWriterクラスのオブジェクトを生成する
            PrintWriter pw = new PrintWriter(new BufferedWriter(file));

            pw.append("Analysis time " + (endTime - startTime) / 1E9 + " seconds");

            //ファイルを閉じる
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clear() {
        try {
            for (String fileName : fileNames) {
                // FileWriterクラスのオブジェクトを生成する
                FileWriter file = new FileWriter(fileName);
                // PrintWriterクラスのオブジェクトを生成する
                PrintWriter pw = new PrintWriter(new BufferedWriter(file));

                pw.append("");

                //ファイルを閉じる
                pw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeMethodParams(String className, String methodSignature, List<Status> statusList) {
        try {
            // FileWriterクラスのオブジェクトを生成する
            FileWriter file = new FileWriter(NULLABLE_PARAMS_LIST, true);
            // PrintWriterクラスのオブジェクトを生成する
            PrintWriter pw = new PrintWriter(new BufferedWriter(file));

            pw.append("=====================================\n");
            pw.append(className + "\n");
            pw.append(methodSignature + "\n");

            for (int i = 0; i < statusList.size(); i++)
                pw.append(i + " : " + statusList.get(i).name() + "\n");

            //ファイルを閉じる
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeMethodReturn(String methodSignature) {
        try {
            // FileWriterクラスのオブジェクトを生成する
            FileWriter file = new FileWriter(NULLABLE_RETURN_LIST, true);
            // PrintWriterクラスのオブジェクトを生成する
            PrintWriter pw = new PrintWriter(new BufferedWriter(file));

            pw.append(methodSignature + "\n");

            //ファイルを閉じる
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFields(String declaringClassName, String fieldName) {
        try {

            String msg = "==============================\n";

            msg += declaringClassName + " : " + "\n";
            msg += fieldName + "\n";

            // FileWriterクラスのオブジェクトを生成する
            FileWriter file = new FileWriter(NULLABLE_FIELD_LIST, true);
            // PrintWriterクラスのオブジェクトを生成する
            PrintWriter pw = new PrintWriter(new BufferedWriter(file));

            pw.append(msg);

            //ファイルを閉じる
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeResult(String message) {
        try {
            // FileWriterクラスのオブジェクトを生成する
            FileWriter file = new FileWriter(NULLABLE_RESULT, true);
            // PrintWriterクラスのオブジェクトを生成する
            PrintWriter pw = new PrintWriter(new BufferedWriter(file));

            pw.append(message);

            //ファイルを閉じる
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeOnlyNullable(String message) {
        try {
            // FileWriterクラスのオブジェクトを生成する
            FileWriter file = new FileWriter(NULLABLE_RESULT_ONLY_NULLABLE, true);
            // PrintWriterクラスのオブジェクトを生成する
            PrintWriter pw = new PrintWriter(new BufferedWriter(file));

            pw.append(message);

            //ファイルを閉じる
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeSources(SootMethod method, Stmt stmt) {
        String message = "";

        message += method.getSignature() + " : " + stmt.toString() + "\n";

        try {
            // FileWriterクラスのオブジェクトを生成する
            FileWriter file = new FileWriter(SOURCE, true);
            // PrintWriterクラスのオブジェクトを生成する
            PrintWriter pw = new PrintWriter(new BufferedWriter(file));

            pw.append(message);

            //ファイルを閉じる
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(String log) {
        try {
            // FileWriterクラスのオブジェクトを生成する
            FileWriter file = new FileWriter(LOG_TEXT, true);
            // PrintWriterクラスのオブジェクトを生成する
            PrintWriter pw = new PrintWriter(new BufferedWriter(file));

            pw.append(log + "\n");

            //ファイルを閉じる
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void debugLog(String log) {
        try {
            // FileWriterクラスのオブジェクトを生成する
            FileWriter file = new FileWriter(LOG_DEBUG_TEXT, true);
            // PrintWriterクラスのオブジェクトを生成する
            PrintWriter pw = new PrintWriter(new BufferedWriter(file));

            pw.append(log);

            //ファイルを閉じる
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
