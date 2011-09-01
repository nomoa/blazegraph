/**

Copyright (C) SYSTAP, LLC 2006-2011.  All rights reserved.

Contact:
     SYSTAP, LLC
     4501 Tower Road
     Greensboro, NC 27410
     licenses@bigdata.com

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; version 2 of the License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
/*
 * Created on Aug 17, 2011
 */

package com.bigdata.rdf.sparql.ast;

import com.bigdata.bop.IConstraint;
import com.bigdata.rdf.internal.constraints.SPARQLConstraint;
import com.bigdata.rdf.internal.impl.literal.XSDBooleanIV;
import com.bigdata.rdf.model.BigdataLiteral;

/**
 * AST node for a HAVING clause.
 * 
 * @author <a href="mailto:thompsonbry@users.sourceforge.net">Bryan Thompson</a>
 * @version $Id$
 * 
 *          TODO Implement {@link IValueExpressionMetadata} so we can report the
 *          aggregated variables across the filters which need to be
 *          materialized?
 */
public class HavingNode extends
        ValueExpressionListBaseNode<IValueExpressionNode> {

    /**
     * 
     */
    public HavingNode() {
    }

    /**
     * Return the {@link IConstraint}s for this {@link ProjectionNode}.
     */
    public IConstraint[] getConstraints() {

        final IConstraint[] exprs = new IConstraint[size()];

        int i = 0;

        for (IValueExpressionNode node : this) {

            exprs[i++] = new SPARQLConstraint<XSDBooleanIV<BigdataLiteral>>(
                    node.getValueExpression());

        }

        return exprs;

    }

    public String toString(final int indent) {

        final StringBuilder sb = new StringBuilder();

        sb.append("\n").append(indent(indent)).append("having ");

        for (IValueExpressionNode v : this) {

            sb.append(v);
            
        }

        return sb.toString();

    }

}
