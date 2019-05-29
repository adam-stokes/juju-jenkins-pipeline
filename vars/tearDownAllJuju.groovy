/* Tear down any dangling juju controllers
 *
 */
def call() {
    // Unregister jaas
    sh "juju unregister -y jaas || true"
    def controllers_yaml = readYaml text:sh(script: "juju controllers --format yaml", returnStdout: true)
    controllers_yaml.controllers.each { key, data ->
        if (key != 'jaas') {
            sh "juju destroy-controller --destroy-all-models --destroy-storage -y ${key} || true"
        }
    }
}
