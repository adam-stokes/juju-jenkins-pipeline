/* Grab a few logs
 *
 * @param controller juju controller to use
 * @param model juju model to use
 */
def call(String controller,
         String model) {
    def collect_debug_sh = "tox -e py36 -- python3 infra/collect-debug.py"
    sh "cd jobs && wget https://raw.githubusercontent.com/juju-solutions/cdk-field-agent/master/collect.py"
    sh "cd jobs && ${collect_debug_sh} endtime"
    sh "cd jobs && ${collect_debug_sh} save-meta"
    sh "cd jobs && ${collect_debug_sh} push meta.yaml"
    sh "cd jobs && ${collect_debug_sh} push stats.db"
    sh "cd jobs && tox -e py36 -- python3 collect.py -m ${controller}:${model}"
    sh "cd jobs && ${collect_debug_sh} push results*.tar.gz"

    archiveArtifacts artifacts: 'jobs/results**', fingerprint: true
}
