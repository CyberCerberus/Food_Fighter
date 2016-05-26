package gameCore;

public class Buff {
    private int cur, start;
    private double amount;
    
    public Buff() {
        amount = 1;
        cur = start = 0;
    }
    
    public void update(double amount, int start) {
        this.amount = amount;
        this.start = this.cur = start;

    }
    
    public double getVal() {
        if(cur == 0 || cur == start) {
            return 1.0;
        }
        else {
            return amount;
        }
    }
    
    public void passTime() {
        if(cur > 0) {
            cur--;
        }
    }
    
    public void clear() {
    	cur = 0;
    	amount = 1.0;
    	start = 0;
    }
    
}
