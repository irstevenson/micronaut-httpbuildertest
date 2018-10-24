package httpbuildertest

import io.micronaut.configuration.picocli.PicocliRunner
import io.micronaut.context.ApplicationContext

import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import picocli.CommandLine.Parameters

import groovyx.net.http.HttpBuilder

@Command(name = 'httpbuildertest', description = '...',
        mixinStandardHelpOptions = true)
class HttpbuildertestCommand implements Runnable {

    @Option(names = ['-v', '--verbose'], description = '...')
    boolean verbose

    static void main(String[] args) throws Exception {
        PicocliRunner.run(HttpbuildertestCommand.class, args)
    }

    void run() {
        HttpBuilder.configure {
            request.uri = 'https://api.github.com'
        }.get {
            request.uri.path = '/repos/micronaut-projects/micronaut-core/releases/latest'

            response.success {
                println 'Success!!'
            }
        }
    }
}
