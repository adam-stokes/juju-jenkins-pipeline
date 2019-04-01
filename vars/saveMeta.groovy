/* Saves metadata
 *
 */
def call() {
    def py3 = "/var/lib/jenkins/venvs/ci/bin/python3"
    def collect_debug_sh = "${py3} infra/collect-debug.py"
    sh "cd jobs && ${collect_debug_sh} set-meta"
    sh "cd jobs && ${collect_debug_sh} save-meta"
}
