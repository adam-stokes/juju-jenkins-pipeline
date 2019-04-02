/* Installs required tools for testing
 *
 * We have to use python3.5 for the setup as xenial is the base OS on jenkins slaves
 */
def call() {
    sh "sudo add-apt-repository -y ppa:deadsnakes/ppa"
    sh "sudo apt update"
    sh "sudo apt install -qyf tox python3-apt virtualenv"

    // Charmstore auth
    withCredentials([file(credentialsId: 'charm_creds', variable: 'CHARMCREDS'),
                     file(credentialsId: 'juju_creds', variable: 'JUJUCREDS'),
                     file(credentialsId: 'jaas_login', variable: 'JAASCREDS'),
                     file(credentialsId: 'sso_token', variable: 'SSOCREDS'),
                     file(credentialsId: 'aws_creds', variable: 'AWSCREDS'),
                     file(credentialsId: 'snapcraft_cpc_creds', variable: 'SNAPCRAFTCPCCREDS'),
                     file(credentialsId: 'cdkbot_ssh_rsa', variable: 'CDKBOTSSHCREDS'),
                     file(credentialsId: 'snapcraft_creds', variable: 'SNAPCRAFTCREDS'),
                     file(credentialsId: 'scapestack_creds', variable: 'SCAPESTACKCREDS'),
                     file(credentialsId: 'scapestack_cloud', variable: 'SCAPESTACKCLOUD')]) {

        // Setup python envs
        sh "rm -rf /var/lib/jenkins/venvs || true"
        sh "virtualenv --python=python3.5 /var/lib/jenkins/venvs/ansible"
        sh "virtualenv --python=python3.6 /var/lib/jenkins/venvs/ci"
        sh "/var/lib/jenkins/venvs/ansible/bin/pip3 install -r jobs/requirements.txt"
        sh "/var/lib/jenkins/venvs/ci/bin/pip3 install -r jobs/requirements.txt"


        sh "export CHARMCREDS=${CHARMCREDS}"
        sh "export JUJUCREDS=${JUJUCREDS}"
        sh "export JAASCREDS=${JAASCREDS}"
        sh "export SNAPCRAFTCREDS=${SNAPCRAFTCREDS}"
        sh "export AWSCREDS=${AWSCREDS}"
        sh "export SSOCREDS=${SSOCREDS}"
        sh "export SNAPCRAFTCPCCREDS=${SNAPCRAFTCPCCREDS}"
        sh "export CDKBOTSSHCREDS=${CDKBOTSSHCREDS}"
        sh "export SCAPESTACKCREDS=${SCAPESTACKCREDS}"
        sh "export SCAPESTACKCLOUD=${SCAPESTACKCLOUD}"
        sh "cd jobs && /var/lib/jenkins/venvs/ansible/bin/ansible-playbook infra/playbook-jenkins.yml -e 'ansible_python_interpreter=/usr/bin/python3.5'"
    }
}
