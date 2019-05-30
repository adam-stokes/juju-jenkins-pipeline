/* Tear down dangling lxd images
 *
 */
def call() {
    def lxc_yaml = readYaml text:sh(script: "lxc list --format yaml", returnStdout: true)
    lxc_yaml.each { image ->
        sh "lxc delete --force ${image.name} || true"
    }
}
