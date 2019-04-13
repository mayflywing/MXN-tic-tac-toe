
public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		loop: for (int i = 0; i < 10; i++) { 
            for (int j = 0; j < 10; j++) { 
                for (int k = 0; k < 10; k++) { 
                    for (int h = 0; h < 10; h++) { 
                        if (h == 6) { 
                            continue loop; 
                        } 
                        System.out.println(i+" "+j+" "+" "+k+" "+h); 
                    } 
                } 
            } 
        }
	}

}
