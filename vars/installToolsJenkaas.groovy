/* Installs required tools for testing
 *
 * We have to use python3.5 for the setup as xenial is the base OS on jenkins slaves
 */
def call() {
    sh "sudo add-apt-repository -y ppa:deadsnakes/ppa"
    sh "sudo add-apt-repository -y --remove ppa:ansible/ansible || true"
    sh "sudo apt update"
    sh "sudo apt install -qyf tox python3-apt"

    // Charmstore auth
    withCredentials([file(credentialsId: 'charm_creds', variable: 'CHARMCREDS'),
                     file(credentialsId: 'juju_creds', variable: 'JUJUCREDS'),
                     file(credentialsId: 'jaas_login', variable: 'JAASCREDS'),
                     file(credentialsId: 'snapcraft_creds', variable: 'SNAPCRAFTCREDS')]) {

        sh "export CHARMCREDS=${CHARMCREDS}"
        sh "export JUJUCREDS=${JUJUCREDS}"
        sh "export JAASCREDS=${JAASCREDS}"
        sh "export SNAPCRAFTCREDS=${SNAPCRAFTCREDS}"
        sh "cd jobs && tox -e py35 -- ansible-playbook infra/playbook-jenkins.yml -e 'ansible_python_interpreter=/usr/bin/python3.5'"
    }
}
