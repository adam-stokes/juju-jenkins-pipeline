/* Installs required tools for testing
 *
 */
def call() {
    sh "sudo add-apt-repository -qyf ppa:deadsnakes/ppa"
    sh "sudo apt-update"
    sh "sudo apt install -qyf libffi-dev python3.6 python3.6-dev libffi-dev libssl-dev libxml2-dev libxslt1-dev libjpeg8-dev zlib1g-dev"
    sh "sudo -H -E python3 -m pip install -U pipenv"
    sh "cd jobs && /usr/local/bin/pipenv install --skip-lock"

    // Charmstore auth
    withCredentials([file(credentialsId: 'charm_creds', variable: 'CHARMCREDS'),
                     file(credentialsId: 'juju_creds', variable: 'JUJUCREDS'),
                     file(credentialsId: 'snapcraft_creds', variable: 'SNAPCRAFTCREDS')]) {

        sh "cd jobs/infra && /usr/local/bin/pipenv run ansible-playbook playbook-jenkins.yml --extra-vars 'charm_creds=${CHARMCREDS} juju_creds=${JUJUCREDS} snapcraft_creds=${SNAPCRAFTCREDS}'"
    }
}