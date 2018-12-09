package presto.android.gui.clients.ccfg;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;


public class CCFG {

    private static final int indentation = 2;

    private static void printlnIndented(String s, PrintStream out, int indent) {
        for (int i = 0; i < indent; i++) {
            out.print(" ");
        }
        out.println(s);
    }

    public List<Event> events;

    public CCFG() {
        this.events = new ArrayList<>();
    }

    public void setEvent(Event event) {
        this.events.add(event);
    }

    public void dumpXML(PrintStream out) {
        int indent = 0;
        printlnIndented("<Events>", out, indent);

        for (Event event : events) {
            event.dumpXML(out, indent);
        }

        printlnIndented("</Events>", out, indent);
    }

    public static class Event {

        protected String name;
        protected String type;
        protected String eventType;
        protected String declaringClass;

        public Event(String name, String type, String eventType, String declaringClass) {
            this.name = name;
            this.type = type;
            this.eventType = eventType;
            this.declaringClass = declaringClass;
        }

        public void dumpXML(PrintStream out, int indent) {
            printlnIndented(String.format("<Event name=\"%s\" type=\"%s\" eventType=\"%s\" class=\"%s\"></Event>", this.name, this.type, this.eventType, this.declaringClass), out, indent);
        }
    }
}