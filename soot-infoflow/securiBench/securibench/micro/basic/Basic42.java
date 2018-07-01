/**
 * @author Benjamin Livshits <livshits@cs.stanford.edu>
 * <p>
 * $Id$
 */
package securibench.micro.basic;

import securibench.micro.BasicTestCase;
import securibench.micro.MicroTestCase;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 *  @servlet description="use getInitParameterNames" 
 *  @servlet vuln_count = "1" 
 *  */
public class Basic42 extends BasicTestCase implements MicroTestCase {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletContext context = getServletConfig().getServletContext();
        Enumeration e = context.getInitParameterNames();
        while (e.hasMoreElements()) {
            String name = (String) e.nextElement();
            Object value = context.getInitParameter(name);
            PrintWriter writer = resp.getWriter();
            writer.println(value.toString());          					 /* BAD */
        }
    }

    public String getDescription() {
        return "use getInitParameterNames";
    }

    public int getVulnerabilityCount() {
        return 1;
    }
}