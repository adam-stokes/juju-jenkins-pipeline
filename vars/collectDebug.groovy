/* Grab a few logs
 *
 * @param controller juju controller to use
 * @param model juju model to use
 */
def call(String controller,
         String model) {
    def py3 = "/var/lib/jenkins/venvs/ci/bin/python3.6"
    def collect_debug_sh = "${py3} infra/collect-debug.py"
    sh "cd jobs && wget https://raw.githubusercontent.com/juju-solutions/cdk-field-agent/master/collect.py"
    sh "cd jobs && ${py3} collect.py -m ${controller}:${model}"
    sh "cd jobs && ${py3} push --key-id 'field-agent' results*.tar.gz"

    archiveArtifacts artifacts: 'jobs/results**', fingerprint: true
}
