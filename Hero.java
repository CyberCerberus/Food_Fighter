public abstract class Hero extends Character{
   
   public Hero(String name, int health, int attackSpeed, int attackDamage){
      super(name, health, attackSpeed, attackDamage);
   }
   
   //most basic attack
   public void attack(Monster other){
      System.out.println(this.getName() + "attacks " + other.getName());
      //yet to be fully implemented
   }
   public abstract void ultimateAttack(Monster other);
}