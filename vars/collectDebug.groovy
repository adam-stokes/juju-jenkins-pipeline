/* Grab a few logs
 *
 * @param controller juju controller to use
 * @param model juju model to use
 */
def call(String controller,
         String model) {
    sh "wget https://raw.githubusercontent.com/juju-solutions/cdk-field-agent/master/collect.py"
    sh "pipenv run python3 collect.py -m ${controller}:${model}"
    archiveArtifacts artifacts: 'results**', fingerprint: true
}
