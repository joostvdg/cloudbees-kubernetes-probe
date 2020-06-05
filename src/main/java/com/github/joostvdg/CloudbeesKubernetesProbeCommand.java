package com.github.joostvdg;

import com.github.joostvdg.cmd.ListCBSts;
import com.github.joostvdg.cmd.ListPodsDemo;
import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "cloudbees-kubernetes-probe",
        description = "...",
        mixinStandardHelpOptions = true,
        subcommands =  {
                ListPodsDemo.class,
                ListCBSts.class
        }
)
public class CloudbeesKubernetesProbeCommand implements Runnable {

    @Option(names = {"-v", "--verbose"}, description = "...")
    boolean verbose;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(CloudbeesKubernetesProbeCommand.class, args);
    }


    public void run() {
        // business logic here
        if (verbose) {
            System.out.println("Hi!");
        }
    }
}
