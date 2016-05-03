public abstract class Character{
   
   private String name;
   private int health, attackSpeed, attackDamage;
   
   public Character(String name, int health, int attackSpeed, int attackDamage){
      this.name = name;
      this.health = health;
      this.attackSpeed = attackSpeed;
      this.attackDamage = attackDamage;
   }
     
   //getters
   public String getName(){return this.name;}   
   public int getHealth(){return this.health;}
   public int getAttackSpeed(){return this.attackSpeed;}
   public int getAttackDamage(){return this.attackDamage;}
   
   //setters   
   public void setHealth(int health){this.health = health;}
   public void setAttackSpeed(int as){this.attackSpeed = as;}
   public void setAttackDamage(int ad){this.attackDamage = ad;}
   
   
}