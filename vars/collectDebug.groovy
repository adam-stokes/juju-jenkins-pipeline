/* Grab a few logs
 *
 * @param controller juju controller to use
 * @param model juju model to use
 */
def call(String controller,
         String model) {
    sh "cd jobs && wget https://raw.githubusercontent.com/juju-solutions/cdk-field-agent/master/collect.py"
    sh "cd jobs && tox -e py36 -- python3 collect.py -m ${controller}:${model}"
    sh "cd jobs && tox -e py36 -- python3 infra/collect-debug.py push results*.tar.gz"
    archiveArtifacts artifacts: 'jobs/results**', fingerprint: true
}
