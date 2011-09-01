package com.bigdata.rdf.sparql.ast;

import java.util.Iterator;

import com.bigdata.bop.BOp;

/**
 * Base class for AST group nodes.
 */
public abstract class GroupNodeBase<E extends IGroupMemberNode> extends
        GroupMemberNodeBase<E> implements IGroupNode<E> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    interface Annotations extends GroupMemberNodeBase.Annotations {
    
        String OPTIONAL = "optional";

        boolean DEFAULT_OPTIONAL = false;

    }

    /**
     * Note: Uses the default for the "optional" annotation.
     */
    protected GroupNodeBase() {
        
    }

	protected GroupNodeBase(final boolean optional) {
		
		setOptional( optional );
		
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
    public Iterator<E> iterator() {
		
		return (Iterator) argIterator();
		
	}

	public IGroupNode<E> addChild(final E child) {
		
	    if(child == this)
	        throw new IllegalArgumentException();
	    
        addArg((BOp) child);

		child.setParent(this);
		
		return this;
		
	}
	
	public IGroupNode<E> removeChild(final E child) {

        if (child == null)
            throw new IllegalArgumentException();

        if (child == this)
            throw new IllegalArgumentException();

        if (removeArg((BOp) child))
            child.setParent(null);
		
		return this;
		
	}
	
    public boolean isEmpty() {
        
        return arity() == 0;
        
    }
    
	public int size() {
		
		return arity();
		
	}
	
	public boolean isOptional() {
		
        return getProperty(Annotations.OPTIONAL, Annotations.DEFAULT_OPTIONAL);
		
	}
	
	public void setOptional(final boolean optional) {
	    
	    setProperty(Annotations.OPTIONAL, optional);
	    
	}
	
    /**
     * {@inheritDoc}
     * <p>
     * Overridden to also clone the children and then set the parent reference
     * on the cloned children.
     */
    @Override
    public GroupNodeBase<E> clone() {

        @SuppressWarnings("unchecked")
        final GroupNodeBase<E> tmp = (GroupNodeBase<E>) super.clone();

        final int size = size();

        for (int i = 0; i < size; i++) {

            IGroupMemberNode aChild = (IGroupMemberNode) tmp.get(i);

            aChild = (IGroupMemberNode) ((ASTBase) aChild).clone();

            tmp.setArg(i, (ASTBase) aChild);

        }

        return tmp;

    }

    /**
     * Simple but robust version of to-String 
     */
    public String toString(final int indent) {
        
        final String s = indent(indent);
        
        final StringBuilder sb = new StringBuilder();

        sb.append("\n").append(s).append(getClass().getSimpleName());

        if (isOptional()) {

            sb.append("[optional]");

        }

        sb.append(" {");

        for (IQueryNode n : this) {

//            sb.append(n.toString(indent + 1)).append("\n");
            sb.append(n.toString(indent+1));

        }

//        for (IQueryNode n : this) {
//            if (!(n instanceof StatementPatternNode)) {
//                continue;
//            }
//            sb.append(n.toString(indent + 1)).append("\n");
//        }
//
//        for (IQueryNode n : this) {
//            if (!(n instanceof FilterNode)) {
//                continue;
//            }
//            sb.append(n.toString(indent + 1)).append("\n");
//        }
//
//        for (IQueryNode n : this) {
//            if (!(n instanceof UnionNode)) {
//                continue;
//            }
//            sb.append(((UnionNode) n).toString(indent + 1)).append("\n");
//        }
//
//        for (IQueryNode n : this) {
//            if (!(n instanceof JoinGroupNode)) {
//                continue;
//            }
//            sb.append(((JoinGroupNode) n).toString(indent + 1)).append("\n");
//        }
//
//        for (IQueryNode n : this) {
//            if (!(n instanceof SubqueryRoot)) {
//                continue;
//            }
//            sb.append(((SubqueryRoot) n).toString(indent + 1)).append("\n");
//        }
//
//        for (IQueryNode n : this) {
//            if (!(n instanceof AssignmentNode)) {
//                continue;
//            }
//            sb.append(((AssignmentNode) n).toString(indent + 1)).append("\n");
//        }

        sb.append("\n").append(s).append("}");

        return sb.toString();

    }

}
