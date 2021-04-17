public class Test {
	public static void main(String[] args){
		ScopedMap<String, Integer> sm = new ScopedMap<String, Integer>();
		Integer a = sm.getNestingLevel();
		sm.put("x", 1);
		sm.put("y", 2);
		sm.enterScope();
		Integer b = sm.get("x");
		System.out.println(b);
	}
}
