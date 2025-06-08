/**
 * 
 */
package farmersmarket;

/**
 * This Class describes the details of a specific item from a purchase
 */
public class CartItem {
	
	private FarmerProduct product;
	private int quantity;
	
	
	
	
	/**
	 * @param productName
	 * @param price
	 * @param quantity
	 * 
	 * This method is the constructor for class CartItem.
	 */
	public CartItem(FarmerProduct product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}


	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}


	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * This method calculates the price per item based on quantity.
	 */
	
	public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
	
	/**
	 * This method allows an object FarmerProduct to be accessed in order to obtain details such as name, price or category.
	 */
	
	public FarmerProduct getProduct() {
	    return product;
	}
	
	
	
	
	
	
	
	

}
