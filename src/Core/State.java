package Core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class State {
    private final String name;
    private Boolean isFinal = false;
    private ArrayList<Transition> transitions = new ArrayList<Transition>();

    public State(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public Boolean isFinal(){
        return isFinal;
    }

    public void setFinal(Boolean isFinal){
        this.isFinal = isFinal;
    }

    public State addTransition(State state, Character character, Character popCharacter, Character pushCharacter){
        return addTransition(state, new Edge(character, popCharacter, pushCharacter));
    }

    public State addTransition(State state, Edge... edges){
        for(Edge edge : edges){
            Transition transition = new Transition(state, edge);
            if(!transitions.contains(transition)){
                transitions.add(transition);
            }
        }
        return this;
    }

    public ArrayList<Transition> getTransitions(){
        return this.transitions;
    }

    public ArrayList<Transition> getTransitions(Character C){
        ArrayList<Transition> result = new ArrayList<Transition>();
        for(Transition transition : this.transitions){
            if(transition.getEdge().getCharacter() != null && transition.getEdge().getCharacter().equals(C)){
                result.add(transition);
            }
        }
        return result;
    }

    public Set<Transition> transitions(Character character, Character pop){
        Set<Transition> result = new HashSet<Transition>();
        for(Transition transition : this.transitions){
            Edge edge = transition.getEdge();
            if(edge.getCharacter() != null && edge.getCharacter().equals(character) && edge.getPopCharacter() != null && edge.getPopCharacter().equals(pop)){
                result.add(transition);
            }else if(edge.getCharacter() != null && edge.getCharacter().equals(character) && pop == null && edge.getPopCharacter() == null){
                result.add(transition);
            }

            if(character == null && edge.getCharacter() == null && edge.getPopCharacter() != null && edge.getPopCharacter().equals(pop)){
                result.add(transition);
            }else if( character == null && edge.getCharacter() == null && pop == null && edge.getPopCharacter() == null){
                result.add(transition);
            }
        }
        return result;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof State){
            State s = (State) o;
            return this.name.equals(s.name);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return this.name.hashCode();
    }

    @Override
    public String toString(){
        return "State: " + name;
    }
}
