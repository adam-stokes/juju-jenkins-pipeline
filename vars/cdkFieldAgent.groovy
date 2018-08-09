/* Grab cdk field agent data
 *
 * @param controller juju controller to use
 * @param model juju model to use
 */
def call(String controller,
         String model) {
    dir('field-agent') {
        git url: "https://github.com/battlemidget/cdk-field-agent", branch: 'add-argv'
        sh "python3 ./collect.py -m ${controller}:${model}"
    }
    archiveArtifacts artifacts: 'field-agent/result*tar.gz', fingerprint: true
}
