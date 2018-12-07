/**
 * Raul Barbosa 2014-11-07
 */
package primes.model;

import java.util.ArrayList;

public class PrimesBean {
	private int number;

	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public ArrayList<String> getPrimess() {
		ArrayList<String> primes = new ArrayList<String>();
		int candidate = 2;
		for(int count = 0; count < number; candidate++)
			if(isPrime(candidate)) {
				primes.add((new Integer(candidate)).toString());
				count++;
			}
		return primes;
	}
	
	private boolean isPrime(int number) {
		if((number & 1) == 0)
			return number == 2;
		for(int i = 3; number >= i*i; i += 2)
			if((number % i) == 0)
				return false;
		return true;
	}
}
