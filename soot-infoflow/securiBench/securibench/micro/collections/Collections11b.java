/**
 * @author Benjamin Livshits <livshits@cs.stanford.edu>
 * <p>
 * $Id: Collections11b.java,v 1.1 2006/04/21 17:14:26 livshits Exp $
 */
package securibench.micro.collections;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 *  @servlet description = "simple collection deposit/retrieve" 
 *  @servlet vuln_count = "1" 
 *  */
class Collections11b {
    protected void foo(Object o, ServletResponse resp) throws IOException {
        Collection c = (Collection) o;
        String str = c.toString();
        PrintWriter writer = resp.getWriter();
        writer.println(str);                    /* BAD */
    }
}