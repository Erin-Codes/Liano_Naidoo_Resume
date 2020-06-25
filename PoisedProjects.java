import java.io.File;
import java.util.Date;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Liano Naidoo
 *
 */
public class PoisedProjects {
    /**
     *
     * @param args the command line arguments - unused.
     */

    public static void main(String[] args) {
        // Initializing Different Object Arrays (to be expanded later).
        Project[] projectArray = new Project[0];
        Entity[] contractorArray = new Entity[0];
        Entity[] customerArray = new Entity[0];
        Entity[] architectArray = new Entity[0];
        File file = new File("newProjects.txt");

        //Create the file
        try {
            if (file.createNewFile()) {
                System.out.println("New Text File is created!");
            } else {
                System.out.println("File already exists.");
            }
        }catch(IOException e){
            System.out.println("An IO error occured.");
        }


        // Try-catch block to print an error for incorrect input provided by user.
        try {
            // Displays menu and Prompts user to select an option.
            Scanner input0 = new Scanner(System.in);
            int option = 0;
            while (option != 8) {
                // Creating a filereader for "newProjects.txt" using the Scanner and FileReader Class as well as a new ArrayList.
                Scanner fileReader = new Scanner(new FileReader("newProjects.txt"));
                ArrayList <String> projectsList = new ArrayList<>();

                /* Using a while loop containing a nested if-statement to store each project from "newProjects.txt"
                 * to as an element to "projectsList".*/
                String adder = "";
                int counter = -1;
                while (fileReader.hasNext()) {
                    // Incrementing control variables.
                    counter ++;
                    adder += fileReader.nextLine()+"\n";
                    // Stores the string of 28 lines(Individual Project)
                    if (counter > 0 && counter % 28 == 0) {
                        projectsList.add(adder.replace(",",""));
                        // "Resetting" control variables once the while loop has iterated over 28 lines.
                        adder = "";
                        counter = 0;
                    }
                }
                System.out.println("\nChoose one of the options below (NUMBER ONLY) to continue:\n\n " +
                        "1 - Add New Project\n " +
                        "2 - Edit Due Date\n " +
                        "3 - Change Total Amount of Fee Paid to Date\n " +
                        "4 - Change Contractor Details\n " +
                        "5 - Finalize an exisitng project\n " +
                        "6 - Display incomplete projects\n " +
                        "7 - Display overdue projecs\n " +
                        "8 - Exit\n");

                option = input0.nextInt();
                if (option == 1) {
                    projectArray = expandProject(projectArray);
                    contractorArray = expandEntity(contractorArray);
                    customerArray = expandEntity(customerArray);
                    architectArray = expandEntity(architectArray);
                    // Using user defined methods to increase each Object Array by one element.

                    System.out.println("Create New Project.\n");

                    Entity newCustomer = getCustomer();
                    customerArray[customerArray.length - 1] = newCustomer;
                    String substituteSurname = customerArray[0].name;
                    // Stores objects in object arrays.

                    Entity newContractor = getContractor();
                    contractorArray[contractorArray.length - 1] = newContractor;
                    //Stores objects in object arrays.

                    Entity newArchitect = getArchitect();
                    architectArray[architectArray.length - 1] = newArchitect;
                    // Stores objects in object arrays.

                    Project newProject = getProject();
                    projectArray[projectArray.length - 1] = newProject;
                    // Provides a substitute name if no name was given as a project name.
                    for (int i = 0; i < projectArray.length; i++) {
                        if (projectArray[i].name.isEmpty() )
                            projectArray[i].setName(projectArray[0].buildType +" "+ substituteSurname);
                    }
                    projectArray[projectArray.length - 1] = newProject;
                    // Stores Object in object array.

                    /*Creating a new File object and a for-loop that appends each array to 'newProjects.txt', using the
                     user defined arrayToFile() function. */
                    File projectFile = new File("newProjects.txt");
                    for (int i = 0; i < projectArray.length; i++) {
                        appendArray(projectArray[i].toString().stripLeading(), projectFile);

                        for (i = 0; i < customerArray.length; i++) {
                            appendArray(customerArray[i].toString(), projectFile);

                            for (i = 0; i < contractorArray.length; i++) {
                                appendArray(contractorArray[i].toString(), projectFile);

                                for (i = 0; i < architectArray.length; i++) {
                                    appendArray(architectArray[i].toString()+"\n,\n", projectFile);
                                }
                            }
                        }
                    }
                    System.out.println("Project saved to 'newProjects.txt'.");
                }

                if (option == 2) {
                    // Prompting user to specify the project number.
                    System.out.println("Enter the project number of the project you would like to update: ");
                    Scanner input00 = new Scanner(System.in);
                    String identifier = input00.nextLine();

                    // Prompting user to provide a new deadline.
                    System.out.println("Enter the updated project deadline: ");
                    Scanner input01 = new Scanner(System.in);
                    String replacement = input01.nextLine();

                    String choice = "deadline";
                    update(projectsList, identifier, replacement, choice);
                }

                if (option == 3) {
                    System.out.println("Enter the project number of the project you would like to update: ");
                    Scanner input02 = new Scanner(System.in);
                    String identifier = input02.nextLine();
                    // Prompting user to specify the project number.

                    System.out.println("Enter the updated Fees Paid by customer, to date: ");
                    Scanner input03 = new Scanner(System.in);
                    double totalPaid = input03.nextDouble();
                    String replacement = "" + totalPaid;
                    // Prompting user to provide a new deadline.

                    String choice  = "paid";
                    update(projectsList, identifier, replacement, choice);
                    // Prints the updated 'projectArray'.
                }

                if (option == 4) {
                    System.out.println("Update contractor details: ");
                    contractorArray[contractorArray.length - 1] = getContractor();
                    // Prompts user to update contractor details.

                    for (int i = 0; i < contractorArray.length; i++) {
                        System.out.println(contractorArray[i]);
                    }
                    // Prints the updated 'contractorArray'.
                }

                if (option == 5) {
                    // Prompting user to specify the project number.
                    System.out.println("Enter the project number of the project that you would like to mark as finalised: ");
                    Scanner input00 = new Scanner(System.in);
                    String identifier = input00.nextLine();

                    String choice = "final";
                    String replacement = "YES";
                    update(projectsList, identifier, replacement, choice);

                }

                if (option == 6) {
                    // Displays incomplete projects.
                    viewIncomplete(projectsList);
                }

                if (option == 7) {
                    // Displays Overdue projects.
                    viewOverdue(projectsList);
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Incorrect input.");
            // Displays error message.
        } catch (IOException e) {
            System.out.println("An IO error occurred.");
        }

    }

    /**
     * Creates a new Project object, made up of strings stored from their respective user inputs.
     * @return A Project object(Project details) made from the users input.
     */
    // Method that creates a Project object.
    public static Project getProject() {
        // Prompts user for specific input
        System.out.println("\nEnter the project name :");
        Scanner input1 = new Scanner(System.in);
        String name = input1.nextLine();

        System.out.println("\nEnter the project number: ");
        Scanner input2 = new Scanner(System.in);
        String projectNumber = input2.nextLine();

        System.out.println("\nEnter the building type: ");
        Scanner input3 = new Scanner(System.in);
        String buildType = input3.nextLine();

        System.out.println("\nEnter the projects Physical address: ");
        Scanner input4 = new Scanner(System.in);
        String projectAddress = input4.nextLine();

        System.out.println("\nEnter the project ERF number: ");
        Scanner input5 = new Scanner(System.in);
        String erfNumber = input5.nextLine();

        System.out.println("\nEnter the total fee charged for project: ");
        Scanner input6 = new Scanner(System.in);
        double totalFee = input6.nextDouble();

        System.out.println("\nEnter the total amount paid to date, by client: ");
        Scanner input7 = new Scanner(System.in);
        double totalPaid = input7.nextDouble();

        System.out.println("\nEnter the project due date in the format: YYYY MM DD : ");
        Scanner input8 = new Scanner(System.in);
        String deadline = input8.nextLine();
        // A while loop to ensure that the correct format is provided.
        while (!deadline.startsWith("20")) {
            System.out.println("Incorrect Format!\n");
            System.out.println("\nEnter the project due date in the format: YYYY MM DD : ");
            input8 = new Scanner(System.in);
            deadline = input8.nextLine();
        }

        String finalised = "NO";
        String completion = "N/A";
        Project projectObject = new Project(name, projectNumber, buildType, projectAddress, erfNumber, totalFee, totalPaid, deadline, finalised, completion);
        // Creating a project object.

        // Changes the 'name' value from "null" to an empty string if no input was provided.
        if (name.isEmpty())
            name = "";
        projectObject = new Project(name, projectNumber, buildType, projectAddress, erfNumber, totalFee, totalPaid, deadline, finalised, completion);

        return projectObject;

    }

    /**
     * Creates a new Entity object, made up of strings stored from their respective user inputs.
     * @return An Entity object(Customer Details) made from the users input.
     */
    // Method that creates a customer object.
    public static Entity getCustomer() {
        // Prompts user for specific input.
        System.out.println("\n\n\t\t-Customer Details-\nEnter Customers First Name: ");
        Scanner input9 = new Scanner(System.in);
        String customerName = input9.nextLine();

        System.out.println("\nEnter Customers Surname: ");
        Scanner input10 = new Scanner(System.in);
        String customerSurname = input10.nextLine();

        System.out.println("\nEnter Telephone Number: ");
        Scanner input11 = new Scanner(System.in);
        String customeTelephone = input11.nextLine();

        System.out.println("\nEnter email address: ");
        Scanner input12 = new Scanner(System.in);
        String customerEmail = input12.nextLine();

        System.out.println("\nEnter physical Address: ");
        Scanner input13 = new Scanner(System.in);
        String customerAddress = input13.nextLine();


        // Creating a Entity object (Customer).
        Entity customerObject = new Entity(customerName, customerSurname, "Customer", customeTelephone, customerEmail, customerAddress);

        return customerObject;
    }

    /**
     * Creates a new Entity object, made up of strings stored from their respective user inputs.
     * @return An Entity object(Contractor details) made from the users input.
     */
    // Method that creates a contractor object.
    public static Entity getContractor() {
        // Prompts user for specific input.
        System.out.println("\n\n\t\t-Contractor Details-\nEnter Contractors Name: ");
        Scanner input14 = new Scanner(System.in);
        String contractorName = input14.nextLine();

        System.out.println("\nEnter Contractors Surname: ");
        Scanner input15 = new Scanner(System.in);
        String contractorSurname = input14.nextLine();

        System.out.println("\nEnter Telephone Number: ");
        Scanner input16 = new Scanner(System.in);
        String contractorTelephone = input15.nextLine();

        System.out.println("\nEnter email address: ");
        Scanner input17 = new Scanner(System.in);
        String contractorEmail = input16.nextLine();

        System.out.println("\nEnter physical Address: ");
        Scanner input18 = new Scanner(System.in);
        String contractorAddress = input18.nextLine();

        // Creating a Entity contractor object.
        Entity contractor = new Entity(contractorName, contractorSurname, "Contractor", contractorTelephone, contractorEmail, contractorAddress);

        return contractor;
    }

    /**
     * Creates a new Entity object, made up of strings stored from their respective user inputs.
     * @return An Entity object(Architect details) made from the users input.
     */
    // Method that creates an architect object.
    public static Entity getArchitect() {
        // Prompts user for specific input.
        System.out.println("\n\n\t\t-Architect Details-\nEnter Architects Name: ");
        Scanner input19 = new Scanner(System.in);
        String architectName = input19.nextLine();

        System.out.println("\nEnter Architect Surname:\t\t ");
        Scanner input20 = new Scanner(System.in);
        String architectSurname = input20.nextLine();

        System.out.println("\nEnter Telephone Number:\t\t ");
        Scanner input21 = new Scanner(System.in);
        String architectTelephone = input21.nextLine();

        System.out.println("\nEnter email address:\t\t ");
        Scanner input22 = new Scanner(System.in);
        String architectEmail = input22.nextLine();

        System.out.println("\nEnter physical Address:\t\t ");
        Scanner input23 = new Scanner(System.in);
        String architectAddress = input23.nextLine();

        // Creating a Entity architect object.
        Entity architect = new Entity(architectName, architectSurname, "Architect", architectTelephone, architectEmail, architectAddress);

        return architect;
    }

    /**
     * Increases the amount of elements in the Object Array (taken in the method argument), by creating a new Project object array with one more element
     * and making the initial Object Array equal to the new one, then returning it.
     * @param array - The array that is to be "expanded".
     * @return An array with an increased amount of elements.
     */
    public static Project[] expandProject(Project[] array) {
        Project[] newArray = new Project[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, array.length);

        array = newArray;
        return array;
    }

    /**
     * Increases the amount of elements in the Object Array (taken in the method argument), by creating a new Entity object array with one more element
     * and making the initial Object Array equal to the new one, then returning it.
     * @param array - The array that is to be "expanded".
     * @return An array with an increased amount of elements.
     */
    public static Entity[] expandEntity(Entity[] array) {
        Entity[] newArray = new Entity[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, array.length);

        array = newArray;
        return array;
    }

    /**
     * A function that uses the FileWriter class to APPEND the given string to the given file.
     * @param string - A string representation of the project.
     * @param file - The file to which the string will be appended to.
     * @throws IOException - When an input/output error occurs.
     */
    public static void appendArray(String string, File file) throws IOException{
        FileWriter filewriter = new FileWriter(file, true);
        filewriter.write(string+"\n".strip());
        filewriter.close();
    }

    /**
     * A function that uses the FileWriter class to WRITE the given string to the "newProjects.txt".
     * @param projectsList - An ArrayList representation of the project.
     * @throws IOException - When an input/output error occurs.
     */
    public static void writeList(ArrayList<String> projectsList) throws IOException {
        FileWriter writer = new FileWriter("newProjects.txt");
        for(String ProjectString: projectsList) {
            writer.write(ProjectString.stripTrailing()+"\n,\n");
        }
        writer.close();
    }

    /**
     * Updates/edits the specified detail of the selected project.
     * @param projectsList - An ArrayList representation of all the projects.
     * @param identifier - A string containing the project number of the desired project.
     * @param replacement - A string containing the information that will be used to edit the specified project detail.
     * @param choice - A string that directs the which specific detail the function has to edit/update.
     * @throws IOException - When an input/output error occurs.
     */
    public static void update(ArrayList<String> projectsList , String identifier, String replacement, String choice) throws IOException{

        for (int i = 0; i < projectsList.size(); i++) {
            String replacedData = null;
            if (projectsList.get(i).contains("Project Number: " + identifier)) {
                // Creating a string of the element(project) at the specified index, and a File object for "newProjects.txt".
                String originalData = projectsList.get(i);
                File projectFile = new File("newProjects.txt");

                if (choice.equals("paid")) {
                    /* Gets line of the data to be changed, sets the replaced data back to the ArrayList and overwrites it to the
                     * project file using 'writeList()'. */
                    replacedData = originalData.replaceFirst(originalData.split(":")[7], " " + replacement + "\nProject Deadline Date");
                    projectsList.set(i, replacedData);
                    System.out.println("--Change made successfully.--");
                    writeList(projectsList);
                }

                if (choice.equals("deadline")) {
                    /* Gets line of the data to be changed, sets the replaced data back to the ArrayList and overwrites it to the
                     * project file using 'writeList()'. */
                    replacedData = originalData.replaceFirst(originalData.split(":")[8], " " + replacement + "\nProject Finalised");
                    projectsList.set(i, replacedData);
                    System.out.println("--Change made successfully.--");
                    writeList(projectsList);
                }

                if (choice.equals("final")) {
                    if (projectsList.get(i).contains("Project Finalised?: NO")) {
                        // Gets the lines of the data to be changed and replaces the specific values while storing it to 'replacedData'.
                        replacedData = originalData.replaceFirst(originalData.split(":")[9], " " + replacement + "\nCompletion date");
                        Date date = new Date();
                        replacedData = replacedData.replaceFirst(replacedData.split(":")[10], " " + date + "\n\nName");

                        /* Appends 'replacedData'/finalized project to 'Completed projects.txt'
                         *, sets it back to the ArrayList, overwrites it to 'newProjects.txt' and generates an invoice.*/
                        File completedProject = new File("Completed project.txt");
                        appendArray(replacedData.strip()+"\n,\n", completedProject);
                        projectsList.set(i, replacedData);
                        System.out.println("--Change made successfully.--");
                        writeList(projectsList);
                        genInvoice(replacedData);
                    }
                    else
                        System.out.println("Project already finalised!");
                }
            }
            else
                System.out.println("No project (with that project number) found.");
        }
    }

    /**
     * Generates and prints out an invoice for the customer of the finalized project.
     * @param replacedData - A string representation of the finalized project.
     */
    public static void genInvoice(String replacedData) {
        /* Stores the lines containing the specific project details.
         * Creates a Pattern object to match the regex of a floating point number.
         * Stores each line as a Matcher object.
         */
        String input1 = replacedData.split(":")[7];
        String input2 = replacedData.split(":")[6];
        Pattern pattern = Pattern.compile("([0-9]+[.][0-9]+)");
        Matcher match1 = pattern.matcher(input1);
        Matcher match2 = pattern.matcher(input2);

        // Initializing the variables to contain the found pattern.
        String paidString = "";
        String chargedString = "";
        // A while loop that finds the matching pattern within the Matcher objects and stores them as strings.
        while(match1.find() && match2.find())
        {
            paidString = match1.group();
            chargedString = match2.group();
        }
        /* Converting the strings containing the amounts to float.
         * Calculates the amount still due by the customer to poised.
         */
        float paidFloat = Float.parseFloat(paidString);
        float chargedFloat = Float.parseFloat(chargedString);
        float amountDue = chargedFloat - paidFloat;

        // If the customer still owes Poised:
        if (paidFloat < chargedFloat) {
            // Gets the customers telephone details.
            String phoneLine = replacedData.split(":")[15];
            String phone = phoneLine.substring(phoneLine.indexOf(" "), phoneLine.indexOf("\n"));
            // Gets the customers email details.
            String emailLine = replacedData.split(":")[16];
            String email = emailLine.substring(emailLine.indexOf(" "), emailLine.indexOf("\n"));
            // Gets the customers address details.
            String addressLine = replacedData.split(":")[17];
            String address = addressLine.substring(addressLine.indexOf(" "), addressLine.indexOf("\n"));
            // Gets the customers name.
            String nameLine = replacedData.split(":")[13];
            String name  = nameLine.substring(nameLine.indexOf(" "), nameLine.indexOf("\n"));
            // Displays the invoice details
            System.out.println("\nCustomer Invoice For:\t" + name + "\n" +
                    "Amount Due:\t\t R" + amountDue + "\n"+
                    "Contactact details:" +
                    "\n  Telephone:\t\t" + phone+
                    "\n  Email:\t\t" + email+
                    "\n  Address:\t\t" + address);
        }
        else // If the customer does not owe Poised:
            System.out.println("The client has paid the entire fee.");
    }
    /**
     * Displays all the Incomplete(not finalized) projects
     * @param projectsList - An ArrayList representation of all the projects.
     */
    public static void viewIncomplete(ArrayList<String> projectsList) {
        for (String s : projectsList) {
            if (s.contains("Project Finalised?: NO")) {
                System.out.print(s + "\n*\n*\n*");
            }
        }
    }
    /**
     * Displays all the projects that are overdue.
     * @param projectsList -
     */
    public static void viewOverdue(ArrayList<String> projectsList) {
        for (String s : projectsList) {
			/* Stores the lines of data to be used to a variable and extracts only the due date then stores the
			 due date as an integer.*/
            String dateLine = s.split(":")[8];
            String dateString = dateLine.replaceAll("[^0-9]", "");
            int dateInt = Integer.valueOf(dateString);
			/* Creates a DateFormat object using the specified format and creates Date object
			"today" (storing the current date). */
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            Date today = new Date();
            try {
                /* Stores the formatted date string as a Date object and uses the if statement
                 * to compare the due date with the current date.  */
                Date dueDate = df.parse(String.valueOf(dateInt));
                if (dueDate.compareTo(today) < 0) {
                    System.out.println(s + "\n*\n*\n*");
                } else
                    System.out.println("No overdue projects found.");
            } catch (ParseException e) {
                System.out.println("Unexpected error while parsing.");
            }

        }
    }
}
