/* Sets a failure status
 *
 */
def call() {
    def collect_debug_sh = "/var/lib/jenkins/venvs/ci/bin/python3 infra/collect-debug.py"
    sh "cd jobs && ${collect_debug_sh} test-result --fail"
}
