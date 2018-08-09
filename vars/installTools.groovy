/* Installs required tools for testing
 *
 */
def call() {
    sh "sudo snap install juju-wait --classic || true"
    sh "sudo snap install kubectl --classic || true"
    sh "sudo snap install bundletester --edge --classic || true"
    sh "sudo pip3 install pipenv"
}
