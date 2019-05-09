/* Tear down environment
 *
 */
def call(String controller) {
    sh "juju destroy-controller --destroy-all-models -y ${controller} || true"
}
