package soot.jimple.infoflow;

import soot.jimple.infoflow.data.abstractValues.Status;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NullabillityResultManager {
    private static NullabillityResultManager INSTANCE = new NullabillityResultManager();

    private NullabillityResultManager() {
        // do nothing
    }

    public static NullabillityResultManager getIntance() {
        return INSTANCE;
    }

    public void initialize() {
        // reset Logging
        List<String> fileNames = new ArrayList<>(Arrays.asList(
                "targets/method_params_nullable_list.txt",
                "targets/result.txt",
                "targets/method_return_nullable_list.txt",
                "targets/fields_list.txt"));

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

    public void writeMethodParams(String methodSignature, List<Status> statusList) {
        try {
            // FileWriterクラスのオブジェクトを生成する
            FileWriter file = new FileWriter("targets/method_params_nullable_list.txt", true);
            // PrintWriterクラスのオブジェクトを生成する
            PrintWriter pw = new PrintWriter(new BufferedWriter(file));

            pw.append("=====================================\n");
            pw.append(methodSignature + "\n");

            for (int i = 0; i < statusList.size(); i++)
                pw.append(i + " : " + statusList.get(i).name() + "\n");

            //ファイルを閉じる
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeMethodReturn(String methodSignature) {
        try {
            // FileWriterクラスのオブジェクトを生成する
            FileWriter file = new FileWriter("targets/method_return_nullable_list.txt", true);
            // PrintWriterクラスのオブジェクトを生成する
            PrintWriter pw = new PrintWriter(new BufferedWriter(file));

            pw.append(methodSignature + "\n");

            //ファイルを閉じる
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFields(String declaringClassName, String fieldName) {
        try {

            String msg = "==============================\n";

            msg += declaringClassName + " : " + "\n";
            msg += fieldName + "\n";

            // FileWriterクラスのオブジェクトを生成する
            FileWriter file = new FileWriter("targets/fields_list.txt", true);
            // PrintWriterクラスのオブジェクトを生成する
            PrintWriter pw = new PrintWriter(new BufferedWriter(file));

            pw.append(msg);

            //ファイルを閉じる
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
