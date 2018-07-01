/**
 * @author Benjamin Livshits <livshits@cs.stanford.edu>
 * <p>
 * $Id: Pred2.java,v 1.4 2006/04/04 20:00:40 livshits Exp $
 */
package securibench.micro.pred;

import securibench.micro.BasicTestCase;
import securibench.micro.MicroTestCase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 *  @servlet description="simple correlated tests" 
 *  @servlet vuln_count = "0" 
 *  */
public class Pred2 extends BasicTestCase implements MicroTestCase {
    private static final String FIELD_NAME = "name";

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean choice = new Random().nextBoolean();
        String name = "abc";

        if (choice) {
            name = req.getParameter(FIELD_NAME);
        }

        if (choice) {
            PrintWriter writer = resp.getWriter();
            writer.println(name);              /* BAD */
        }
    }

    public String getDescription() {
        return "simple correlated tests";
    }

    public int getVulnerabilityCount() {
        return 1;
    }
}