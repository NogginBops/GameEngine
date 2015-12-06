package town.recources;

/**
 * @author Julius Häger
 *
 */
public class RecourcePool {
	
	private float amount, capacity;
	
	/**
	 * @param capcity
	 */
	public RecourcePool(float capcity) {
		this.amount = 0;
		this.capacity = capcity;
	}
	
	/**
	 * @param amount
	 * @return
	 */
	public float addRecources(float amount){
		this.amount += amount;
		if(this.amount > capacity){
			return this.amount - (this.amount = capacity);
		}
		return 0;
	}
	
	/**
	 * @param amount
	 * @return
	 */
	public float useRecource(float amount){
		this.amount -= amount;
		if(this.amount < 0){
			return amount + this.amount + (this.amount = 0);
		}
		return amount;
	}
	
	/**
	 * @return
	 */
	public float getCurrentAmount(){
		return amount;
	}
	
	/**
	 * @return
	 */
	public float getCurrentCapacity(){
		return capacity;
	}
}
