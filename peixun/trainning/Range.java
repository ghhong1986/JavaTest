package trainning;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Sets;


public class Range {
		
	public Range(int left ,boolean  isLeftOpen, int right ,boolean isRightOpen){
		this.left  =left;
		this.right = right;
		this.kl = isLeftOpen;
		this.kr = isRightOpen;
	}
	
	private int left;
	
	private int  right;
	
	private boolean kl;
	
	private boolean kr;
	

	public boolean isInRange(int b){
		if(kl ==false && kr ==false && left <right ){
			return (left < b && right >b );
		}
		if(kl == true && kr == true ){
			return  (left <= b && right >=b );
		}
		if(kl ==true){
			return (left <= b && right > b);
		}
		if (kr == true) {
			return (left < b && right >= b);
		}
		return false;
	}
	
}
 