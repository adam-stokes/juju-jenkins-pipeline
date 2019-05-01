/* Grab a few logs
 *
 * @param controller juju controller to use
 * @param model juju model to use
 */
def call(String controller,
         String model) {
    sh "cd jobs && wget https://raw.githubusercontent.com/juju-solutions/cdk-field-agent/master/collect.py"
    sh "cd jobs && ${utils.cipy} collect.py -m ${controller}:${model} || true"
    sh "cd jobs && ${utils.cipy} infra/collect-debug.py push --key-id 'results_file' results*.tar.gz"

    archiveArtifacts artifacts: 'jobs/results**', fingerprint: true
}
