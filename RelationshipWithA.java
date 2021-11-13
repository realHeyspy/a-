package schoolFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;

public class RelationshipWithA {
	static Stack<CustomObject> v = new Stack<CustomObject>();
	static Stack<CustomObject> L = new Stack<CustomObject>();
	static Stack<CustomObject> memoryL = new Stack<CustomObject>();
	static Stack<CustomObject> LRemove = new Stack<CustomObject>();
	static Stack<String> WayToGoal = new Stack<String>();
	static String Goal, Start;
	static int totalCost;

	public static CustomObject dequeue(Stack<CustomObject> L) {
		memoryL = (Stack<CustomObject>) L.clone();
		memoryL.remove(0);
		if (LRemove.isEmpty()) {
			while (!L.isEmpty()) {
				LRemove.push(L.pop());
			}
		}
		L.addAll(memoryL);
		CustomObject datapopup = LRemove.pop();
		LRemove.clear();
		return datapopup;
	}

	//sort what result should in first
	public static Stack<CustomObject> sortstack(Stack<CustomObject> input) {
		Stack<CustomObject> tmpStack = new Stack<CustomObject>();
		while (!input.isEmpty()) {
			ArrayList<CustomObject> datasort = new ArrayList<CustomObject>();
			while (input.size() != 0) {
				datasort.add(input.pop());
			}
			Collections.sort(datasort, new Comparator<CustomObject>() {
				@Override
				public int compare(CustomObject lhs, CustomObject rhs) {
					// -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
					int n1 = lhs.f;
					int n2 = rhs.f;
					if (n1 > n2) {
						return 1;
					}
					if (n2 > n1) {
						return -1;
					} else {
						return 0;
					}
				}
			});
			for (int i = 0; i < datasort.size(); i++) {
				tmpStack.add(datasort.get(i));
			}
		}
		return tmpStack;
	}

	// sort input data to follow by alphabet
	public static void sortByAlphabet(ArrayList<CustomObject> input) {
		Collections.sort(input, new Comparator<CustomObject>() {
			@Override
			public int compare(CustomObject o1, CustomObject o2) {
				// TODO Auto-generated method stub
				return o1.Upper.compareTo(o2.Upper);
			}
		});
	}

	// check if this match goal
	public static boolean GoalTest(String input) {
		if (input.equals(Goal)) {
			return true;
		}
		return false;
	}

	// process data
	public static void StepProcess(ArrayList<CustomObject> inputData) {
		CustomObject getThisCharter = new CustomObject();
		getThisCharter.Upper = Start;
		int i, previous;
		i = previous = 0;
		WayToGoal.push("A");
		while (!GoalTest(getThisCharter.Upper)) {
			previous = getThisCharter.g;
			i = 0;
			CustomObject DataObject = inputData.get(i);
			while (DataObject.Upper.compareTo(getThisCharter.Upper) != 0) {
				i++;
				DataObject = inputData.get(i);
			}
			while (DataObject.Upper.compareTo(getThisCharter.Upper) == 0) {
				DataObject.g = previous + DataObject.valueUpper;
				DataObject.f = DataObject.g + DataObject.valueNode;
				v.push(DataObject);
				L.push(DataObject);
				i++;
				DataObject = inputData.get(i);
			}
			//L.addAll(memoryL);
			L = sortstack(L);
			PrintStepProcess(v, L);
			//memoryL = (Stack<CustomObject>) L.clone();
			//memoryL.remove(0);
			CustomObject clone = dequeue(L);
			getThisCharter.Upper = clone.Charter;
			WayToGoal.push(getThisCharter.Upper);
			getThisCharter.g = clone.g;
			getThisCharter.f = clone.f;
			totalCost = getThisCharter.f;
			v.clear();
		}
	}

	//Print all after one cycle complete
	public static void PrintStepProcess(Stack<CustomObject> v, Stack<CustomObject> L) {
		String listL="";
		int VSize = v.size();
		int LSize = L.size();
		CustomObject Line = v.get(0);
		System.out.println("-----------------------------------------------------------------------------");
		for (int i = 0; i < LSize; i++) {
			listL += L.get(i).Charter + L.get(i).f + ",";
		}
		System.out.printf("%-5s%-8s%-8s%-8s%-8s%-8s%-22s\n", Line.Upper, "|" + Line.Charter, "|" + Line.valueUpper,
				"|" + Line.valueNode, "|" + Line.g, "|" + Line.f, "|"+listL);
		for (int i = 1; i < VSize; i++) {
			Line = v.get(i);
			System.out.printf("%-5s%-8s%-8s%-8s%-8s%-8s%-22s\n", Line.Upper, "|" + Line.Charter, "|" + Line.valueUpper,
					"|" + Line.valueNode, "|" + Line.g, "|" + Line.f, "|");
		}
	}

	public static void main(String[] arg) {
		ArrayList<CustomObject> dataInput = new ArrayList<>();
		dataInput.add(new CustomObject(15, "G", 9, "A"));
		dataInput.add(new CustomObject(6, "E", 7, "A"));
		dataInput.add(new CustomObject(5, "D", 13, "A"));
		dataInput.add(new CustomObject(7, "C", 20, "A"));
		dataInput.add(new CustomObject(10, "K", 6, "G"));
		dataInput.add(new CustomObject(10, "K", 8, "E"));
		dataInput.add(new CustomObject(5, "D", 4, "E"));
		dataInput.add(new CustomObject(2, "H", 2, "D"));
		dataInput.add(new CustomObject(4, "I", 5, "D"));
		dataInput.add(new CustomObject(12, "F", 4, "C"));
		dataInput.add(new CustomObject(4, "I", 6, "C"));
		dataInput.add(new CustomObject(2, "H", 5, "K"));
		dataInput.add(new CustomObject(0, "B", 3, "H"));
		dataInput.add(new CustomObject(2, "H", 9, "I"));
		dataInput.add(new CustomObject(0, "B", 5, "I"));
		// sortByAlphabet(dataInput);
		Start = "A";
		Goal = "B";
		System.out.printf("%-5s%-8s%-8s%-8s%-8s%-8s%-22s\n", "TT", "|TTK", "|k(u,v)", "|h(v)", "|g(v)", "|f(v)",
				"| Danh sach L");
		StepProcess(dataInput);
		System.out.println("-----------------------------------------------------------------------------");
		System.out.print("B   | TTKT/dung, duong di ");
		while (WayToGoal.size() > 1) {
			System.out.print(WayToGoal.pop() + " <-");
		}
		System.out.print(WayToGoal.pop() + "," + " do dai " + totalCost);
	}
}
