package d2k.disk.composition;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.StringUtils;

public class MainApp {

    private static final org.apache.logging.log4j.Logger log =
            org.apache.logging.log4j.LogManager.getLogger(MainApp.class);

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder("d")
                .longOpt("dir").hasArg().required()
                .argName("directory").valueSeparator('=').desc("dir/with/movies").build());

        options.addOption(Option.builder("n")
                .longOpt("try-num").hasArg()
                .argName("number").valueSeparator('=').desc("number of iteration to try").build());
       
        options.addOption(Option.builder("c")
                .longOpt("capacity").hasArg()
                .argName(StringUtils.join(DiskCapacity.values(), " | "))
                .valueSeparator('=').desc("type of disc capasity").build());

        Worker worker = new Worker();

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cl = parser.parse(options, args, true);
            
            if (cl.hasOption("d")) {
                worker.setDirectory(new File(cl.getOptionValue("d")));
            }
            if (cl.hasOption("n")) {
                worker.setTryNum(Integer.parseInt(cl.getOptionValue("n")));
            }
            if (cl.hasOption("c")) {
                worker.setCapacity(DiskCapacity.valueOf(cl.getOptionValue("c")));
            }
        } catch (Exception ex) {
            System.err.println("error: " + ex.toString());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(132, "java " + MainApp.class.getName(), "", options, "", true);
            System.out.println();
            System.exit(1);
        }

        DiskComposition disk = worker.start();
        
        String answer = System.console().readLine("\ndo you want to move all files to a directory? (y/n) : ");
        if(answer!=null && answer.trim().equalsIgnoreCase("y")){
            worker.moveCompositionToRoot(disk);
        }
        
        
    }

}
