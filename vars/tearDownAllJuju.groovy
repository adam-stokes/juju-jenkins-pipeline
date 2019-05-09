/* Tear down any dangling juju controllers
 *
 */
def call() {
    def controllers_yaml = readYaml text:sh(script: "juju controllers --format yaml", returnStdout: true)
    controllers_yaml.controllers.each { key, data ->
        sh "juju destroy-controllers --destroy-all-models -y ${key} || true"
    }
}
