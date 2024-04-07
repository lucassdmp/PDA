package Core;


public class Edge {
    private Character character, popCharacter, pushCharacter;

    public Edge(){}
    public Edge(Character character, Character popCharacter, Character pushCharacter) {
        this.character = character;
        this.popCharacter = popCharacter;
        this.pushCharacter = pushCharacter;
    }

    public Character getCharacter() {
        return character;
    }
    public Character getPopCharacter() {
        return popCharacter;
    }
    public Character getPushCharacter() {
        return pushCharacter;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
    public void setPopCharacter(Character popCharacter) {
        this.popCharacter = popCharacter;
    }
    public void setPushCharacter(Character pushCharacter) {
        this.pushCharacter = pushCharacter;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Edge){
            Edge e = (Edge) o;
            return this.character.equals(e.character) && this.popCharacter.equals(e.popCharacter) && this.pushCharacter.equals(e.pushCharacter);
        }
        return false;
    }

    @Override
    public int hashCode(){
        int hash = this.character != null ? this.character.hashCode() : 0;
        hash = 31 * hash + (this.popCharacter != null ? this.popCharacter.hashCode() : 0);
        hash = 31 * hash + (this.pushCharacter != null ? this.pushCharacter.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString(){
        return "(Character: " + character + " | Pop: " + popCharacter + " | Push: " + pushCharacter + ")";
    }
}