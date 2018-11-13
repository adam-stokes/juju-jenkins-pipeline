/* Tear down dangling lxd images
 *
 */
def call() {
    def lxc_yaml = readYaml text:sh(script: "sudo lxc list --format yaml", returnStdout: true)
    lxc_yaml.each { image ->
        sh "sudo lxc delete --force ${image.name}"
    }
}
