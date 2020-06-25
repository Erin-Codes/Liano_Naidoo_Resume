public class Project {
	String name;
	String buildType;
	String projectAddress;
	String deadline;
	String finalised;
	String completion;
	String projectNum;
	String erf;
	double totalFee;
	double totalPaid;
	// Project class attributes.

	public Project(String name, String projectNum, String buildType, String projectAddress, String erf, double totalFee, double totalPaid, String deadline,String finalised, String completion) {
		this.name = name;
		this.projectNum = projectNum;
		this.buildType = buildType;
		this.projectAddress = projectAddress;
		this.erf = erf;
		this.totalFee = totalFee;
		this.totalPaid = totalPaid;
		this.deadline = deadline;
		this.finalised = finalised;
		this.completion = completion;
	}// Project Constructor Method.

	public void setName(String projName) {
		name = projName;
	}

	public String getName() {
		return name;
	}
	// Setting and Getting the variable 'deadline'.

	public String toString() {
		String output = "\n\nProject Name: " + name;
		output += "\nProject Number: " + projectNum;
		output += "\nBuild Type: " + buildType;
		output += "\nProject Physical Address: " + projectAddress;
		output += "\nERF Number: " + erf;
		output += "\nTotal Fee Charged: " + totalFee;
		output += "\nTotal Paid By Client (To Date): " + totalPaid;
		output += "\nProject Deadline Date: " + deadline;
		output += "\nProject Finalised?: "+ finalised;
		output += "\nCompletion date: " + completion;

		return output;
	}
	// toString method.
}


