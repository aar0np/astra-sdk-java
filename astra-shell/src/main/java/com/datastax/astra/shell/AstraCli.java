package com.datastax.astra.shell;

import com.datastax.astra.shell.cmd.HelpCustomCommand;
import com.datastax.astra.shell.cmd.ConfigCommand;
import com.datastax.astra.shell.cmd.db.ShowDatabasesCommand;
import com.datastax.astra.shell.cmd.org.ShowOrganizationsCommand;
import com.datastax.astra.shell.cmd.shell.ShellCommand;
import com.datastax.astra.shell.jansi.Out;
import com.datastax.astra.shell.jansi.TextColor;
import com.datastax.astra.shell.utils.ShellPrinter;
import com.github.rvesse.airline.annotations.Cli;
import com.github.rvesse.airline.annotations.Group;
import com.github.rvesse.airline.parser.errors.ParseArgumentsUnexpectedException;

/**
 * Main class for the program. Will route commands to proper class 
 *
 * @author Cedrick LUNVEN (@clunven)
 */
@Cli(
  name = "astra", 
  description    = "CLI for DataStax Astra™ including an interactive mode",
  defaultCommand = ShellCommand.class, // no command => interactive
  commands       = { 
    ShellCommand.class,
    HelpCustomCommand.class,
    ConfigCommand.class
  },
  
  groups = {
        @Group(
            name = "show",
            description = "Listing details of an entity or entity list",
            commands = { ShowOrganizationsCommand.class, ShowDatabasesCommand.class }
        )
  })
public class AstraCli {
    
    /**
     * Main Program.
     *
     * @param args
     *           start options for the shell
     * @throws Exception
     *           error during parsing or interpreting command
     */
    public static void main(String[] args) {
        
        try {
            
            // Show Banner
            ShellPrinter.banner();
            
            // Command Line Interface
            new com.github.rvesse.airline.Cli<Runnable>(AstraCli.class)
                .parse(args)  // Find the processor for the command 
                .run();       // Run the command
            
        } catch(ParseArgumentsUnexpectedException ex) {
            Out.println("Invalid command: " + ex.getMessage(), TextColor.RED);
        } catch(Exception e) {
            Out.println("Execution error:" + e.getMessage(), TextColor.RED);
        }
    }
    
    /**
     * Run the Program with varArgs.
     * 
     * @param args
     *      arguments
     */
    public static void exec(String ...args) {
        main(args);
    }
    
}