import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

public class animalGuessGame<E> extends BinaryTree<E> {
	public animalGuessGame()
	{
		this.root = null;
		this.numElt = 0;
	}

	/**
	 * Updates the Tree with the new question and the correct answer and the wrong answer
	 * @param question		new question we are inserting into the Tree
	 * @param directions	directions of where the question should be added
	 * @param answer		The answer which goes to the left
	 */
	public void insertQuestion(E question, String directions,E answer) {
		Node<E> current = this.root;

		if(directions.length()==0) { //If no directions we set the root
			E temp = this.root.data;
			this.root.data = question;
			this.root.left = new Node<E>(answer,null,null); //creates new Node
			this.root.right = new Node<E>(temp,null,null); //creates new Node
		}
		else { //everythng else
			
			//follow directions with loop to get to correct spot
			for (int i=0; i < directions.length(); i++)
			{
				if (directions.charAt(i) == 'L') 
					current = current.left;
				else
					current = current.right;
			}

			E temp = current.data;
			current.data = question;
			current.left = new Node<E>(answer,null,null); //create the new Nodes
			current.right = new Node<E>(temp,null,null); //create the new Node

		}

	}


	//INPUT FUNCTIONS
	//asks question inserted by the user
	public String askUserQuestion(Node<String> current, Scanner in) {
		System.out.println(current.data);
		return getYNinput(in);
	}
	// guesses what animal is
	public String askQuestion(Node<String> current, Scanner in) {
		System.out.println("Is your animal a "+ current.data);
		return getYNinput(in);
	}

	//after we find out there animal and want a new question for it
	public String getQuestion(String yesAnswer, String noAnswer, Scanner in) {
		String inp = "";
		boolean goodInput = false;
		System.out.print("Please give me a question that is yes for "+yesAnswer+" and no for "+noAnswer+": ");
		while(!goodInput) {
			inp = in.nextLine();
			if(inp.length()>0) {
				goodInput = true;
			}
			else {
				System.out.print("cant be empty input: ");
			}
		}
		return inp;
	}
	// after we guess wrong and need to learn what their animal was
	public String getAnswer(Scanner in) {
		String inp = "";
		boolean goodInput = false;
		System.out.println("Drat! What is your animal? ");
		while(!goodInput) {
			inp = in.nextLine();
			if(inp.length()>0) {
				goodInput = true;
			}
			else {
				System.out.print("cant be empty input: ");
			}
		}
		return inp;
	}

	// gets a yes or no input
	public static String getYNinput(Scanner in) {
		String inp = "";
		boolean goodInput = false;
		while(!goodInput) {
			if(in.hasNextLine()) {
				inp = in.nextLine();
				inp = inp.toLowerCase();
				if(inp.equals("y") || inp.equals("n") || inp.equals("yes")|| inp.equals("no"))goodInput = true;
				else {
					System.out.print("That is not an option, must be 'y' or 'n' or 'yes' or 'no': ");
				}
			}
			else {
				System.out.print("Empty input not permitted, must be 'y' or 'n' or 'yes' or 'no': ");
			}
		}
		return inp;
	}

	//checks if user wants to play again
	public static String playAgain(Scanner in) {
		String inp = "";
		boolean goodInput = false;
		System.out.print("Play Again? I get smarter each time! ");
		while(!goodInput) {
			if(in.hasNextLine()) {
				inp = in.nextLine();
				inp = inp.toLowerCase();
				if(inp.equals("y") || inp.equals("n") || inp.equals("yes")|| inp.equals("no"))goodInput = true;
				else {
					System.out.print("That is not an option, must be 'y' or 'n' or 'yes' or 'no': ");
				}
			}
			else {
				System.out.print("Empty input not permitted, must be 'y' or 'n' or 'yes' or 'no': ");
			}
		}
		return inp;
	}

	public static void main(String[] args) throws FileNotFoundException, IOException{
		//neccessary variables used to save the inputs and update the BinaryTree
		boolean running = true; //boolean that keeps loop going until done
		Scanner in = new Scanner(System.in); //Scanner for input
		animalGuessGame<String> game = new animalGuessGame<String>();
		String answer = "";
		boolean isAnimal = true;
		String question = "";
		String directions = "";

		
		// this arrayList is used at the end to update the text file to save results
		List<String> lines = new ArrayList<String>();
		Scanner textFile = new Scanner(new File("C:\\Users\\Work\\OneDrive - O'Leary Asphalt\\Desktop\\School\\Coding Fun\\DataStructuresProjects\\CompSci254Project3_olea\\CompSci254Project3\\src\\animalText.txt"));
            
        // Loop through the file and process each line
		while (textFile.hasNextLine())
		{
			String line = textFile.nextLine();
			if(line.charAt(0)==('-')) { 
				// in text file i have it set to its directions than a - and than the word or question
				// if the first character is a - that means its the root
				game.root = new Node<String>(line.substring(1),null,null);
			}
			else {
				for(int i = 0;i<line.length();i++) {
					if(line.charAt(i)=='-') {
						String d = line.substring(0,i);
						String data = line.substring(i+1);
						game.add(data, d);
						// gets the location of the - and than adds using the directions before the -
						// and the word or question is located after the -
					}
				}
			}
		}
		

		//Node to track during where we are
		Node<String> current = game.root;
		if(current==null) {
			game.root = new Node<String>("Dog",null,null);
			current = new Node<String>("Dog",null,null);
		}
		
		//initial think of animal statement to prompt user
		System.out.println("Think of an animal!");
		while(running) {
			if(current.left==null)// checks if we are at a child Node
			{
				isAnimal = true;
			}
			else
				isAnimal = false;
			if(isAnimal) // this means we are going to guess the animal
			{
				answer = game.askQuestion(current,in);
				if(answer.equals("y")|| answer.equals("yes")) {
					System.out.println("Yay, I got it right!");
				}
				else { //if we didnt guess correctly
					answer = game.getAnswer(in); //get correct animal guess
					question = game.getQuestion(answer,current.data,in); //get question thats yes for that and no for root
					lines.add(directions+"-"+question); // adds location of the question plus a dash to arrayList so we hacve it to update the text later
					lines.add(directions+"L-"+answer); //correct answers on left so with direction we also add an 'L'
					lines.add(directions+"R-"+current.data); //wrong answers on right so add 'R' to directions
					game.insertQuestion(question, directions, answer); //insert the question in tree

				}
			}
			else {
				// asks the user to get the question with the difference
				answer = game.askUserQuestion(current,in);
				if(answer.equals("y")|| answer.equals("yes")) {// if it is yes we move left down the tree because all yes' go to the left
					directions = directions+"L"; //update where our directions are
					current = current.left; //update current to correct spot
					continue;
				}
				else { //if the answer is no we move down the right and go to the right
					directions = directions +"R"; //update where the directions are
					current = current.right; //update current to correct spot
					continue;
				}


			}
			String yn = playAgain(in); //checks if user wants to play again
			if(yn.equals("y")|| yn.equals("yes"))
			{ //if yes we reset needed variables 
				directions = "";
				current = game.root;
				System.out.println();
				System.out.println("Think of an animal!"); //prompt user again to think of an animal
				continue;
			}
			else {
				break;
			}

		}
		
		
		PrintWriter output = new PrintWriter(new FileWriter("C:\\Users\\Work\\OneDrive - O'Leary Asphalt\\Desktop\\School\\Coding Fun\\DataStructuresProjects\\CompSci254Project3_olea\\CompSci254Project3\\src\\animalText.txt", true));
		for(int i = 0;i<lines.size();i++) { // reads all of our new data and puts it into our text file so next time we run it the data is saved
			output.println(lines.get(i));
		}
		output.close();
	}

	
}


