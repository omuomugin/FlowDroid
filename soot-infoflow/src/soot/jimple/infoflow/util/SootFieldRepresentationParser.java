package soot.jimple.infoflow.util;

import soot.jimple.infoflow.data.SootFieldAndClass;
import soot.util.HashMultiMap;
import soot.util.MultiMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SootFieldRepresentationParser {

    private static final SootFieldRepresentationParser instance = new SootFieldRepresentationParser();

    private Pattern patternSubsigToName = null;

    private SootFieldRepresentationParser() {

    }

    public static SootFieldRepresentationParser v() {
        return instance;
    }

    /**
     * parses a string in soot representation, for example:
     * <soot.jimple.infoflow.test.TestNoMain: java.lang.String field1>
     *
     * @param parseString The method signature to parse
     */
    public SootFieldAndClass parseSootFieldString(String parseString) {
        if (!parseString.startsWith("<") || !parseString.endsWith(">")) {
            throw new IllegalArgumentException("Illegal format of " + parseString + " (should use soot method representation)");
        }
        String name = "";
        String className = "";
        String type = "";
        Pattern pattern = Pattern.compile("<(.*?):");
        Matcher matcher = pattern.matcher(parseString);
        if (matcher.find()) {
            className = matcher.group(1);
        }
        pattern = Pattern.compile(": (.*?) ");
        matcher = pattern.matcher(parseString);
        if (matcher.find()) {
            type = matcher.group(1);
            //remove the string contents that are already found so easier regex is possible
            parseString = parseString.substring(matcher.end(1));
        }
        pattern = Pattern.compile(" (.*?)\\(");
        matcher = pattern.matcher(parseString);
        if (matcher.find()) {
            name = matcher.group(1);
        }
        return new SootFieldAndClass(name, className, type);

    }

    /*
     * Returns classname and unresolved! method names and return types and parameters
     */
    public HashMap<String, Set<String>> parseClassNames(Collection<String> methods, boolean subSignature) {
        HashMap<String, Set<String>> result = new HashMap<String, Set<String>>();
        Pattern pattern = Pattern.compile("^\\s*<(.*?):\\s*(.*?)>\\s*$");
        for (String parseString : methods) {
            //parse className:
            String className = "";
            Matcher matcher = pattern.matcher(parseString);
            if (matcher.find()) {
                className = matcher.group(1);
                String params = "";
                if (subSignature)
                    params = matcher.group(2);
                else
                    params = parseString;

                if (result.containsKey(className))
                    result.get(className).add(params);
                else {
                    Set<String> methodList = new HashSet<String>();
                    methodList.add(params);
                    result.put(className, methodList);
                }
            }
        }
        return result;
    }

    /*
     * Returns classname and unresolved! method names and return types and parameters
     */
    public MultiMap<String, String> parseClassNames2(Collection<String> methods, boolean subSignature) {
        MultiMap<String, String> result = new HashMultiMap<>();
        Pattern pattern = Pattern.compile("^\\s*<(.*?):\\s*(.*?)>\\s*$");
        for (String parseString : methods) {
            //parse className:
            String className = "";
            Matcher matcher = pattern.matcher(parseString);
            if (matcher.find()) {
                className = matcher.group(1);
                String params = "";
                if (subSignature)
                    params = matcher.group(2);
                else
                    params = parseString;
                result.put(className, params);
            }
        }
        return result;
    }

    /**
     * Parses a Soot field subsignature and returns the field name
     *
     * @param subSignature The Soot subsignature to parse
     * @return The name of the method being invoked if the given subsignature
     * could be parsed successfully, otherwise an empty string.
     */
    public String getMethodNameFromSubSignature(String subSignature) {
        if (patternSubsigToName == null) {
            Pattern pattern = Pattern.compile("^\\s*(.+)\\s+(.+)\\((.*?)\\)\\s*$");
            this.patternSubsigToName = pattern;
        }
        Matcher matcher = patternSubsigToName.matcher(subSignature);
        if (matcher.find())
            return matcher.group(2);
        return "";
    }
}
