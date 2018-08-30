/* Installs required tools for testing
 *
 */
def call() {
    sh "sudo -H snap install juju-wait --classic || true"
    sh "sudo -H snap install kubectl --classic || true"
    sh "sudo -H snap install bundletester --edge --classic || true"
    sh "sudo -H pip3 install pipenv"
    sh "cd jobs && /usr/local/bin/pipenv install"
}
