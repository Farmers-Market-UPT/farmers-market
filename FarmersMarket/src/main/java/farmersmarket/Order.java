/**
 * 
 */
package farmersmarket;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class describes the objects of type Order
 */
public class Order {
	
	private ArrayList <CartItem> items = new ArrayList <CartItem>();
	
	private String farmerEmail;
	private LocalDate orderDate;
	private double total;
	private boolean finalized = false;
	
	
	
	/**
	 * @param details
	 * @param farmerEmail
	 * @param orderDate
	 * @param total
	 * @param paid
	 * 
	 * This method is the constructor from class Order
	 */
	public Order(String farmerEmail, LocalDate orderDate) {
		this.farmerEmail = farmerEmail;
		this.orderDate = orderDate;
		
	}
	
	
	/**
	 * This method allows add new items to the cart or alter the quantity of previously added item
	 */
	public void addCartItem(CartItem cartItem) {
        for (CartItem item : items) {
            if (item.getProduct().equals(cartItem.getProduct())) {
                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
                return;
            }
        }
        items.add(cartItem);
    }
	
	
	/**
	 * This method calculate the total of an order
	 */
	public double calculateTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
	}


	/**
	 * @return the finalized
	 */
	public boolean isFinalized() {
		return finalized;
	}


	/**
	 * @param finalized the finalized to set
	 */
	public void setFinalized(boolean finalized) {
		this.finalized = finalized;
	}


	/**
	 * @return the items
	 */
	public ArrayList<CartItem> getItems() {
		return items;
	}


	/**
	 * @return the farmerEmail
	 */
	public String getFarmerEmail() {
		return farmerEmail;
	}


	/**
	 * @return the orderDate
	 */
	public LocalDate getOrderDate() {
		return orderDate;
	}


	/**
	 * @return the total
	 */
	public double getTotal() {
		return total;
	}
	
	
	
}
