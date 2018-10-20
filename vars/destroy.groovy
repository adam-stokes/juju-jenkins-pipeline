/* Destroy Juju Environment
 *
 * @param controller juju controller name
 */
def call(String controller) {
    sh "juju kill-controller -y ${controller} || true"
}
