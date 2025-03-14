package views;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controllers.IdController;
import controllers.MessageController;
import youareell.YouAreEll;

// Simple Shell is a Console view for youareell.YouAreEll.
public class SimpleShell {


    public static void prettyPrint(String output) {
        // yep, make an effort to format things nicely, eh?
//        System.out.println(output);
        output = output.replace("\"", "");
        output = output.replace("{", "");
        String[] lines = output.split("},");
        for (String line : lines) {
            System.out.println("    " + line);
        }
        System.out.println();
    }

    public static void main(String[] args) throws java.io.IOException {

        YouAreEll webber = new YouAreEll(new MessageController(), new IdController());
        
        String commandLine;
        BufferedReader console = new BufferedReader
                (new InputStreamReader(System.in));

        ProcessBuilder pb = new ProcessBuilder();
        List<String> history = new ArrayList<String>();
        int index = 0;
        //we break out with <ctrl c>
        while (true) {
            //read what the user enters
            System.out.println("cmd? ");
            commandLine = console.readLine();

            List<String> list = new ArrayList<String>();


            Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(commandLine);
            while (m.find()) {//solution from https://stackoverflow.com/questions/7804335/split-string-on-spaces-in-java-except-if-between-quotes-i-e-treat-hello-wor
                list.add(m.group(1).replace("\"", "")); //i get the concept but the regex is above me
            }
            //if the user entered a return, just loop again
            if (commandLine.equals("")) {
                continue;
            }

            if (commandLine.equals("exit")) {
                System.out.println("bye!");
                break;
            }


            System.out.println(list); //***check to see if list was added correctly***
            history.addAll(list);
            String results;
            try {
                //display history of shell with index
                if (list.get(list.size() - 1).equals("history")) {
                    for (String s : history)
                        System.out.println((index++) + " " + s);
                    continue;
                }

                // Specific Commands.

                // ids
                if (list.contains("ids")) {
                    if(list.size() == 3){
                        results = webber.register(list.get(1),list.get(2));
                        SimpleShell.prettyPrint(results);
                        continue;
                    }
                    results = webber.get_ids();
                    SimpleShell.prettyPrint(results);
                    continue;
                }

                // messages
                if (list.contains("messages")) {
                    if(list.size() > 1){
                        results = webber.get_usr_message(list.get(1));
                        SimpleShell.prettyPrint(results);
                        continue;
                    }
                    results = webber.get_messages();
                    SimpleShell.prettyPrint(results);
                    continue;
                }
                // you need to add a bunch more.

                if(list.contains("send")){
                    if(list.size() == 3){
                        results = webber.send_all(list.get(1), list.get(2));
                        SimpleShell.prettyPrint(results);
                        continue;
                    }
                    if(list.size() == 5 && list.get(3).equals("to")){
                        results = webber.send_to(list.get(1), list.get(2), list.get(4));
                        SimpleShell.prettyPrint(results);
                        continue;
                    }
                }

                //!! command returns the last command in history
                if (list.get(list.size() - 1).equals("!!")) {
                    pb.command(history.get(history.size() - 2));

                }//!<integer value i> command
                else if (list.get(list.size() - 1).charAt(0) == '!') {
                    int b = Character.getNumericValue(list.get(list.size() - 1).charAt(1));
                    if (b <= history.size())//check if integer entered isn't bigger than history size
                        pb.command(history.get(b));
                } else {
                    pb.command(list);
                }

                if(list.contains("set")){
                    webber.set(list.get(1));
                    continue;
                }

                // // wait, wait, what curiousness is this?
                 Process process = pb.start();

                 //obtain the input stream
                 InputStream is = process.getInputStream();
                 InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader br = new BufferedReader(isr);

                 //read output of the process
                 String line;
                 while ((line = br.readLine()) != null)
                     System.out.println(line);
                 br.close();


            } catch (Exception e) { //this feels wrong
                System.out.println("Input Error, Please try again!");
                throw new RuntimeException(e);
            }

            //catch ioexception, output appropriate message, resume waiting for input
//            catch (IOException e) {
//                System.out.println("Input Error, Please try again!");
//            }
            // So what, do you suppose, is the meaning of this comment?
            /** The steps are:
             * 1. parse the input to obtain the command and any parameters
             * 2. create a ProcessBuilder object
             * 3. start the process
             * 4. obtain the output stream
             * 5. output the contents returned by the command
             */

        }


    }

}