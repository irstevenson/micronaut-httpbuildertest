@Grab( 'io.github.http-builder-ng:http-builder-ng-core:1.0.3' )

import groovyx.net.http.HttpBuilder

HttpBuilder.configure {
    request.uri = 'https://api.github.com'
}.get {
    request.uri.path = '/repos/micronaut-projects/micronaut-core/releases/latest'

    response.success {
        println 'Success!!'
    }
}
