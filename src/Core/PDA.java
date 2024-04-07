package Core;

import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;

public class PDA {
    public State q0;
    public final Stack<Character> stack = new Stack<>();
    public boolean log = false;
    
    public PDA(State q0){
        this.q0 = q0;
    }

    public boolean addOnStack(Character character){
        if (character != null){
            stack.push(character);
            return true;
        }
        return false;
    }

    public boolean removeFromStack(Character character){
        if(character != null && character == stack.peek()){
            stack.pop();
            return true;
        }else if(character == null){
            return true;
        }
        return false;
    }

    public boolean run(String string){
        char[] characters = string.toCharArray();
        int currentCharacterPosition = 0;
        State currentState = q0;
        Transition transition;
        ArrayList<Transition> currentTransitions;

        currentTransitions = currentState.getTransitions();
        transition = currentTransitions.get(0);
        addOnStack(transition.getEdge().getPushCharacter());
        currentState = transition.getState();

        while(currentCharacterPosition < characters.length){
            currentTransitions = currentState.getTransitions(characters[currentCharacterPosition]);
            // System.out.println(currentTransitions);
            if(currentTransitions.isEmpty()){
                if(log){
                    System.out.println("No transition found for '" + characters[currentCharacterPosition] + "' at state [" + currentState.getName() + "]\n\n");
                }
                return currentState.isFinal();
            }{
                for(Transition t : currentTransitions){
                    if(log){
                        System.out.println("Transition: " + t + " at state [" + currentState.getName() + "]\n\n");
                    }
                    if(!removeFromStack(t.getEdge().getPopCharacter())){
                        if(log){
                            System.out.println("Failed to remove from stack: " + t.getEdge().getPopCharacter()+"\n\n");
                        }
                        return currentState.isFinal();
                    }else{
                        addOnStack(t.getEdge().getPushCharacter());
                        currentState = t.getState();
                        break;
                    }
                }
                currentCharacterPosition++;
            }
        }
        if(log){
            System.out.println("Stack: " + stack);
        }

        if(currentCharacterPosition == characters.length && !stack.isEmpty() && stack.peek() == '$'){
            currentTransitions = new ArrayList<>(currentState.transitions(null, '$'));
            System.out.println(currentTransitions);
            if(currentTransitions.isEmpty()){
                if(log){
                    System.out.println("No transition found for '$' at state [" + currentState.getName() + "]\n\n");
                }
                return currentState.isFinal();
            }else{
                transition = currentTransitions.get(0);
                if(log){
                    System.out.println("Transition: " + transition + " at state [" + currentState.getName() + "]\n\n");
                }
                removeFromStack(transition.getEdge().getPopCharacter());
                currentState = transition.getState();
            }
        }
        return currentState.isFinal() && stack.isEmpty();
    }

    public void setLog(){
        this.log = !this.log;
        System.out.println("Log: " + log);
    }
}
