/*******************************************************************************
 * Copyright (c) 2012 Secure Software Engineering Group at EC SPRIDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors: Christian Fritz, Steven Arzt, Siegfried Rasthofer, Eric
 * Bodden, and others.
 ******************************************************************************/
package soot.jimple.infoflow.test.utilclasses;

import soot.jimple.infoflow.test.android.ConnectionManager;

public class D1static extends C1static {

    public D1static(String c) {
        super(c);
    }

    public boolean taintIt() {
        ConnectionManager cm = new ConnectionManager();
        cm.publish(field1);
        return true;
    }

}
