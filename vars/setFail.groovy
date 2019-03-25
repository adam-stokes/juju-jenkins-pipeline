/* Sets a failure status
 *
 */
def call() {
    def collect_debug_sh = "tox -e py36 --workdir ${env.WORKSPACE} -- python3 infra/collect-debug.py"
    sh "cd jobs && ${collect_debug_sh} test-result --fail"
}
