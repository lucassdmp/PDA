package Core;

public class Transition{
    private State state;
    private Edge edge;

    public Transition(){}
    public Transition(State state, Edge edge){
        this.state = state;
        this.edge = edge;
    }

    public State getState(){
        return state;
    }
    public Edge getEdge(){
        return edge;
    }

    public void setState(State state){
        this.state = state;
    }
    public void setEdge(Edge edge){
        this.edge = edge;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Transition){
            Transition t = (Transition) o;
            return this.state.equals(t.state) && this.edge.equals(t.edge);
        }
        return false;
    }

    @Override
    public int hashCode(){
        int hash = this.state != null ? this.state.hashCode() : 0;
        hash = 31 * hash + (this.edge != null ? this.edge.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString(){
        return edge  + " --> [" +  state.getName() + "]";
    }
}
