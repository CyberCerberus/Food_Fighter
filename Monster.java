public abstract class Monster extends Character{

   public Monster(String name, int health, int attackSpeed, int attackDamage){
      super(name, health, attackSpeed, attackDamage);
   }
   
   //most basic attack
   public void attack(Hero other){
      System.out.println(this.getName() + "attacks " + other.getName());
      //yet to be fully implemented
   }
}