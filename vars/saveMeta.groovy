/* Saves metadata
 *
 */
def call() {
    def collect_debug_sh = "tox -e py36 -- python3 infra/collect-debug.py"
    sh "cd jobs && ${collect_debug_sh} save-meta"
}
