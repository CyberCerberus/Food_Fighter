package main;

public abstract class LevelState extends State{
    
    private boolean cleared;
    private boolean unlocked;
    private boolean godMode;
    
    public LevelState(StateManager sm){
	super(sm);
	cleared = false;
	unlocked = false;
	godMode = false;
    }
   

   public boolean unlocked(){
       return unlocked;
   }
   
   public boolean cleared(){
       return cleared;
   }
   
   public void setCleared(boolean b){
       cleared = b;
   }
   
   public void setUnlocked(boolean b){
       unlocked = b;
   }
   
   public void toggleGodMode(){
       godMode = !godMode;
   }
   
   public boolean godMode(){
       return godMode;
   }

}
