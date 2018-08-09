/* Tear down environment
 *
 */
def call(String controller,
         String model) {
    sh "rm -rf $HOME/.kube || true"
    sh "juju destroy-model -y ${controller}:${model}"
}
