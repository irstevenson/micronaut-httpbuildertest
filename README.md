# Introduction

This repo demonstrates an issue with attempting to use [HttpBuilder
NG](https://http-builder-ng.github.io/) with [Micronaut](https://micronaut.io). For some reason, one
of the standard Groovy classes is not found:

    java.lang.ClassNotFoundException: groovy.util.slurpersupport.GPathResult

(Yes, it may seem odd to use Http Builder NG seeing Micronaut has it's own Client, however due to a
bug I was unable to use that currently.)

The repo consists of two things:

1. A Micronaut cli-app to trigger the issue; and
2. A standalone groovy script to confirm that the issue is not with Http Builder NG.

## How to fix it

Micronaut does not include groovy-all, so you need to add a couple of additional dependencies.
Simply add the following to your `build.groovy`:

    compile 'org.codehaus.groovy:groovy-xml:2.5.1'
    compile 'org.codehaus.groovy:groovy-json:2.5.1'
 
## Usage

To run the micronaut app, simply:

    ./gradlew run

You'll see it start up and then bug out with:

```
$ ./gradlew run

> Task :run FAILED
Exception in thread "main" java.lang.BootstrapMethodError: java.lang.NoClassDefFoundError: groovy/util/slurpersupport/GPathResult
        at groovyx.net.http.HttpConfigs$BaseHttpConfig.configure(HttpConfigs.java:557)
        at groovyx.net.http.HttpConfigs.<clinit>(HttpConfigs.java:647)
        at groovyx.net.http.HttpObjectConfigImpl.<init>(HttpObjectConfigImpl.java:37)
        at groovyx.net.http.HttpBuilder.configure(HttpBuilder.java:151)
        at groovyx.net.http.HttpBuilder.configure(HttpBuilder.java:127)
        at groovyx.net.http.HttpBuilder$configure.call(Unknown Source)
        at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCall(CallSiteArray.java:47)
        at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:116)
        at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:128)
        at httpbuildertest.HttpbuildertestCommand.run(HttpbuildertestCommand.groovy:25)
        at picocli.CommandLine.execute(CommandLine.java:904)
        at picocli.CommandLine.access$700(CommandLine.java:104)
        at picocli.CommandLine$RunLast.handle(CommandLine.java:1081)
        at picocli.CommandLine$RunLast.handle(CommandLine.java:1049)
        at picocli.CommandLine$AbstractParseResultHandler.handleParseResult(CommandLine.java:957)
        at picocli.CommandLine.parseWithHandlers(CommandLine.java:1240)
        at picocli.CommandLine.run(CommandLine.java:1793)
        at picocli.CommandLine.run(CommandLine.java:1716)
        at io.micronaut.configuration.picocli.PicocliRunner.run(PicocliRunner.java:129)
        at io.micronaut.configuration.picocli.PicocliRunner.run(PicocliRunner.java:107)
        at io.micronaut.configuration.picocli.PicocliRunner$run.call(Unknown Source)
        at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCall(CallSiteArray.java:47)
        at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:116)
        at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:136)
        at httpbuildertest.HttpbuildertestCommand.main(HttpbuildertestCommand.groovy:21)
Caused by: java.lang.NoClassDefFoundError: groovy/util/slurpersupport/GPathResult
        ... 25 more
Caused by: java.lang.ClassNotFoundException: groovy.util.slurpersupport.GPathResult
        at java.net.URLClassLoader.findClass(URLClassLoader.java:381)
        at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
        at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:349)
        at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
        ... 25 more

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':run'.
> Process 'command '/home/ian/.sdkman/candidates/java/8.0.181-oracle/bin/java'' finished with non-zero exit value 1

* Try:
Run with --stacktrace option to get the stack trace. Run with --info or --debug option to get more log output. Run with --scan to get full insights.

* Get more help at https://help.gradle.org

BUILD FAILED in 1s
3 actionable tasks: 1 executed, 2 up-to-date
```

Then with a matching groovy version, you can do this:

    groovy scripttest/HttpBuilderTest.groovy

Output will look more or less like this (I've included a slightly different call to show groovy
version):

```
$ groovy -v; groovy scripttest/HttpBuilderTest.groovy 
Groovy Version: 2.5.1 JVM: 1.8.0_181 Vendor: Oracle Corporation OS: Linux
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
Success!!
```
