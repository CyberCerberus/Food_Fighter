public class Warrior extends Hero{
   
   public Warrior(String name, int health, int as, int ad){
      super(name, health, as, ad);
   }
   
   public void ultimateAttack(Monster other){
      System.out.println(this.getName() + "Unleashes a bladestrom of death!");
      //yet to be fully implemented
   }
}