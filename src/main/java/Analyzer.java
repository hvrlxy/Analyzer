import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Analyzer {

	private Integer declaration;
	private Integer idx;
	private ScopedMap sm;
	private StringBuilder indentation;

	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(args[0]));
	    StringBuilder input = new StringBuilder("");
	    while (sc.hasNext()){
	    	input.append(sc.next());
	    	input.append(" ");
	    }
	    System.out.println(new Analyzer().analyze(input.toString()));
	}
	
	/**
	 * Construct an Analyzer.
	 */
	public Analyzer(){
		this.declaration = 1;
		this.sm = new ScopedMap<String, Integer>();
		this.indentation = new StringBuilder("");
		this.idx = 0;
	}

	/**
	 * Analyze a block and return it with indentation and scope info.
	 * @param input the block to analyze
	 */
	public String analyze(String input) {
		// TODO: This dummy version produces the correct output for the
		// sample input but totally ignores its actual input -- it would
		// produce the same output for any input.  You should strip it out
		// and replace it with a real version.

		String[] alist = input.split(" ");
		// for (String a : alist){
		// 	System.out.println(a);
		// }
		return block(alist);
	}

	public String block(String[] input){
		String ind = this.indentation.toString();
		this.sm.enterScope();
		if (!input[this.idx].equals("begin")){
			throw new IllegalArgumentException("Illegal input: " + input[this.idx]);
		}
		this.idx ++;
		this.indentation.append("  ");
		String out = stmts(input);

		if (!input[this.idx].equals("end")){
			throw new IllegalArgumentException("Illegal input: " + input[this.idx]);
		}
		this.sm.exitScope();
		this.indentation.deleteCharAt(indentation.length() - 1);
		this.indentation.deleteCharAt(indentation.length() - 1);
		this.idx ++;
		return ind + "begin\n" + out + ind + "end\n";
	}

	public String stmts(String[] input){
		if (input[this.idx].equals("end")){
			//System.out.println(this.idx);
			return "";
		}
		return stmt(input) + stmts(input);
	}

	public String stmt(String[] input){
		StringBuilder out = new StringBuilder("");
		if (input[this.idx].equals("pass")){
			out.append(this.indentation);
			out.append("pass\n");
			this.idx ++;
		}

		else if (input[this.idx].equals("declare")){
			this.idx ++;
			out.append(this.indentation);
			out.append("declare ");
			out.append(input[this.idx]);
			if (!sm.isLocal(input[this.idx])){
				this.sm.put(input[this.idx], declaration);
				out.append(" {declaration " + this.declaration.toString() + "}\n");
				this.declaration ++;
			}
			else{
				out.append(" {illegal redeclaration}\n");
			}
			this.idx ++;
		}

		else if (input[this.idx].equals("use")){
			out.append(this.indentation);
			this.idx ++;
			Integer value = (Integer) this.sm.get(input[this.idx]);
			out.append("use " + input[this.idx]);
			if (value == null){
				out.append(" {illegal undeclared use}\n");
			}
			else{
				out.append(" {references declaration " + value.toString() + "}\n");
			}
			this.idx ++;
		}

		else{
			//System.out.println(input[this.idx]);
			out.append(block(input));
		}

		return out.toString();
	}

}
